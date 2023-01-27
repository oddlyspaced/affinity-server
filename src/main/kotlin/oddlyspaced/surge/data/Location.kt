package oddlyspaced.surge.data

import kotlinx.serialization.Serializable

/**
 * data class to hold a location representation
 */
@Serializable
data class Location(
    val lat: Float,
    val lon: Float,
)