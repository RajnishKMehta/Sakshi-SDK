package rajnishkmehta.sakshi.sdk.api.models

import android.net.Uri

/**
 * Acknowledgement response returned by Vault when a photo request is received/processed.
 *
 * @property fileId Unique identifier matching the original [PhotoRequest].
 * @property isIngested True if Vault successfully received and registered the photo.
 * @property vaultUri Optional URI assigned by Vault where the photo record is stored.
 * @property timestampEpochMs Epoch timestamp in milliseconds of acknowledgement processing.
 */
public data class PhotoResponse(
    public val fileId: String,
    public val isIngested: Boolean,
    public val vaultUri: Uri? = null,
    public val timestampEpochMs: Long = System.currentTimeMillis()
)
