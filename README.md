# Sakshi SDK 📱⚡

[![Release](https://img.shields.io/maven-central/v/io.github.rajnishkmehta.sakshi/sakshi-sdk
)](https://central.sonatype.com/artifact/io.github.rajnishkmehta.sakshi/sakshi-sdk)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
[![API](https://img.shields.io/badge/API-29%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=29)

**Sakshi SDK** (`rajnishkmehta.sakshi.sdk`) is a lightweight, headless Android client and IPC library designed for local-first inter-process communication between trusted client applications (such as *Sakshi Camera* or *Sakshi Audio*) and the **Sakshi Vault** application.

---

## 💡 What is Sakshi SDK?

Sakshi SDK provides clean, idiomatic, coroutine-powered Kotlin public APIs for:
1. **Client Developers (Camera, Audio, Viewer)**: Send photos, manage incremental video sync, ping Vault, query recording status, and observe file copy completion acknowledgements (`CopyDoneAck`).
2. **Vault Developers**: Implement remote AIDL service binding and send structured responses, sync progress, copy completion acknowledgements, and error events using `VaultResponder`.

---

## 🏗️ Dual-Side Architecture & Responsibilities

```
+------------------------------------+                 +-----------------------------------+
|       Client Application           |                 |        Sakshi Vault App           |
|  (e.g., Camera / Audio / Viewer)   |                 |       (External Service)          |
|                                    |                 |                                   |
|  +------------------------------+  |   Android IPC   |  +-----------------------------+  |
|  |        SakshiClient          |  | <=============> |  |   Incremental Copy Engine   |  |
|  +------------------------------+  |   (AIDL/Binder) |  |   VaultResponder Helper     |  |
|  (Send Photos & Video Sync)     |                 |  |   Media Records Database    |  |
+------------------------------------+                 +-----------------------------------+
```

### SDK Responsibilities
- Inter-process communication bridge with Vault service.
- Hiding Binder proxy transactions, AIDL interfaces, and death recipient handling.
- Providing `SakshiClient` for client applications and `VaultResponder` for Vault applications.

### Vault Responsibilities (External App)
- File storage, incremental byte copying, and persistence.
- Interval timers, queue scheduling, and non-overlapping copy pass execution.
- Emitting `CopyDoneAck` (including File ID, optional Vault URI, and total copied bytes) upon completing file transfers.

---

## 📦 Installation & Gradle Integration

Add the dependency to your app or library module's `build.gradle.kts`:

```kotlin
repositories {
    mavenCentral()
    maven {
        url = uri("https://maven.pkg.github.com/RajnishKMehta/sakshi-sdk")
    }
}

dependencies {
    implementation("rajnishkmehta.sakshi:sakshi-sdk:1.0.0-beta.1")
}
```

---

## 🚀 Quick Start Examples

### A. Client App Example (Camera / Audio)

```kotlin
import rajnishkmehta.sakshi.sdk.api.SakshiClient
import rajnishkmehta.sakshi.sdk.api.models.*

val client = SakshiClient.create(context)

// 1. Send Photo
val photoResult = client.sendPhoto(PhotoRequest(fileId = "photo_001", uri = photoUri))

// 2. Start Video Sync
coroutineScope.launch {
    client.startVideoSync(VideoSyncRequest(fileId = "rec_999", uri = videoUri)).collect { result ->
        val status = result.getOrNull()
        println("Sync State: ${status?.state}, Copied Bytes: ${status?.lastCopiedOffsetBytes}")
    }
}

// 3. Observe Copy Completion Acknowledgement
coroutineScope.launch {
    client.observeCopyDone("rec_999").collect { result ->
        val ack = result.getOrNull()
        println("Copy Completed! File ID: ${ack?.fileId}, Vault URI: ${ack?.vaultUri}, Copied Bytes: ${ack?.totalCopiedBytes}")
    }
}
```

### B. Vault App Example (Service Side)

```kotlin
import rajnishkmehta.sakshi.sdk.api.vault.VaultResponder
import rajnishkmehta.sakshi.sdk.api.models.CopyDoneAck
import rajnishkmehta.sakshi.sdk.internal.ipc.ISakshiVaultService

class SakshiVaultRemoteService : Service() {
    private val binder = object : ISakshiVaultService.Stub() {
        override fun startVideoSync(videoSyncBundle: Bundle, callback: ISakshiVaultCallback) {
            val fileId = videoSyncBundle.getString("file_id", "")
            
            // Vault copies bytes incrementally...
            val totalCopied = performVaultCopy(fileId)

            // Vault sends CopyDoneAck back to Client app
            VaultResponder.sendCopyDone(
                callback,
                CopyDoneAck(
                    fileId = fileId,
                    vaultUri = getVaultDestinationUri(fileId),
                    totalCopiedBytes = totalCopied
                )
            )
        }
        // ...
    }
    override fun onBind(intent: Intent?): IBinder = binder
}
```

---

## 📄 AsciiDoc Documentation

Detailed AsciiDoc technical specifications are available in the [`/docs`](docs) directory:

- 📑 [System Architecture (`/docs/architecture.adoc`)](docs/architecture.adoc)
- 📑 [Client API Usage (`/docs/api_usage.adoc`)](docs/api_usage.adoc)
- 📑 [Vault Integration Guide (`/docs/vault_integration.adoc`)](docs/vault_integration.adoc)
- 📑 [AIDL IPC Specification (`/docs/ipc_specification.adoc`)](docs/ipc_specification.adoc)
- 📑 [Development Guide (`/docs/development.adoc`)](docs/development.adoc)

---

## 📄 License

This project is licensed under the [Apache License 2.0](LICENSE).
