package oddlyspaced.surge.provider.data.parameter

import kotlinx.serialization.Serializable
import oddlyspaced.surge.provider.data.Location

@Serializable
class SearchParameter(
    val limitCount: Int = 10,
    val limitDistance: Int = 10,
    val pickupLat: Double,
    val pickupLon: Double,
    val dropLat: Double,
    val dropLon: Double,
)