package oddlyspaced.surge.provider.data

import kotlinx.serialization.Serializable

@Serializable
data class ResponseError(
    val message: String?,
)