package oddlyspaced.surge.provider.data.parameter

import kotlinx.serialization.Serializable
import oddlyspaced.surge.provider.data.AreaServed
import oddlyspaced.surge.provider.data.Location
import oddlyspaced.surge.provider.data.PhoneNumber
import oddlyspaced.surge.provider.data.Provider
import oddlyspaced.surge.providers

@Serializable
data class ProviderParameter(
    val name: String,
    val countryCode: String,
    val phoneNumber: String,
    val lat: Double,
    val lon: Double,
    val services: ArrayList<String>,
    val areaSource: Location,
    val areaRadius: Double,
)

fun ProviderParameter.toProviderInstance(): Provider {
    return Provider(
        (providers.size + 1).hashCode(),
        this.name,
        PhoneNumber(
            this.countryCode,
            this.phoneNumber
        ),
        Location(
            this.lat,
            this.lon
        ),
        this.services,
        AreaServed(
            areaSource,
            areaRadius
        ),
        true
    )
}