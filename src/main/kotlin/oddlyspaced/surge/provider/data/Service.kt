package oddlyspaced.surge.provider.data

import kotlinx.serialization.Serializable

@Serializable
data class Service(
    val id: Int,
    val tag: String,
)