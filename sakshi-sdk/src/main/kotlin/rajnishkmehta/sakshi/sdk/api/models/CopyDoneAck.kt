package rajnishkmehta.sakshi.sdk.api.models

import android.net.Uri

/**
 * Acknowledgement response sent by Vault application to client applications (e.g. Camera)
 * when a file copy or video synchronization pass has completed successfully.
 *
 * @property fileId Unique identifier matching the recording file.
 * @property vaultUri Optional URI assigned by Vault where the copied file is stored.
 * @property totalCopiedBytes Total number of bytes copied into Vault storage.
 * @property timestampEpochMs Epoch timestamp in milliseconds when copy completed.
 */
public data class CopyDoneAck(
    public val fileId: String,
    public val vaultUri: Uri? = null,
    public val totalCopiedBytes: Long = 0L,
    public val timestampEpochMs: Long = System.currentTimeMillis()
)
