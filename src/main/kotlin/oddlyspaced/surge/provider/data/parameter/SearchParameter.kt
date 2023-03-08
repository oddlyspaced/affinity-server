package oddlyspaced.surge.provider.data.parameter

import kotlinx.serialization.Serializable
import oddlyspaced.surge.provider.data.Location
import oddlyspaced.surge.provider.data.Service

@Serializable
class SearchParameter(
    val limitCount: Int = 10,
    val limitDistance: Int = 10,
    val serviceFilters: ArrayList<String>,
    val pickupLocation: Location,
    val dropLocation: Location,
)