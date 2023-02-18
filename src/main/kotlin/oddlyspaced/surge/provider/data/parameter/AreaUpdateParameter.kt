package oddlyspaced.surge.provider.data.parameter

import kotlinx.serialization.Serializable
import oddlyspaced.surge.provider.data.AreaServed
import oddlyspaced.surge.provider.data.Location
import oddlyspaced.surge.provider.data.ProviderStatus

@Serializable
data class AreaUpdateParameter(
    val id: Int,
    val newArea: AreaServed,
)