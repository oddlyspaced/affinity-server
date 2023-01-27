package oddlyspaced.surge.data.parameter

import kotlinx.serialization.Serializable

@Serializable
class SearchParameter(
    val limitCount: Int = 10,
    val limitDistance: Int = 10,
    val searchFromLat: Double,
    val searchFromLon: Double,
)