package oddlyspaced.surge.provider.data.parameter

import kotlinx.serialization.Serializable
import oddlyspaced.surge.provider.data.Location

@Serializable
data class LocationUpdateParameter(
    val id: Int,
    val newLocation: Location
)