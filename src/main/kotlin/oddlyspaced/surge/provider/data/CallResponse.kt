package oddlyspaced.surge.provider.data

import kotlinx.serialization.Serializable

@Serializable
data class CallResponse(
    val success: Boolean,
    val message: String?,
)