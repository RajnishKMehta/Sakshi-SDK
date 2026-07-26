plugins {
    alias(libs.plugins.android.library)
    `maven-publish`
}

android {
    namespace = "rajnishkmehta.sakshi.sdk"
    compileSdk = 37

    defaultConfig {
        minSdk = 29

        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            isMinifyEnabled = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    buildFeatures {
        aidl = true
    }

    publishing {
        singleVariant("release") {
            withSourcesJar()
            withJavadocJar()
        }
    }
}

kotlin {
    jvmToolchain(21)
    compilerOptions {
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_21)
        freeCompilerArgs.add("-Xexplicit-api=strict")
    }
}

dependencies {
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.androidx.annotation)
    implementation(libs.androidx.core.ktx)
}

publishing {
    publications {
        register<MavenPublication>("release") {
            groupId = "rajnishkmehta.sakshi"
            artifactId = "sakshi-sdk"
            version = libs.versions.sakshi.sdk.get()

            afterEvaluate {
                from(components["release"])
            }

            pom {
                name.set("Sakshi SDK")
                description.set("Lightweight Android IPC SDK for communication with Sakshi Vault application.")
                url.set("https://github.com/RajnishKMehta/sakshi-sdk")

                licenses {
                    license {
                        name.set("Apache License, Version 2.0")
                        url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }

                developers {
                    developer {
                        id.set("rajnishkmehta")
                        name.set("Rajnish")
                        email.set("RajnishKMehta@proton.me")
                    }
                }

                scm {
                    connection.set("scm:git:github.com/RajnishKMehta/sakshi-sdk.git")
                    developerConnection.set("scm:git:ssh://github.com/RajnishKMehta/sakshi-sdk.git")
                    url.set("https://github.com/RajnishKMehta/sakshi-sdk")
                }
            }
        }
    }
}
