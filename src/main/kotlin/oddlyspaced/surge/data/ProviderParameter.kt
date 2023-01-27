package oddlyspaced.surge.data

data class ProviderParameter(
    val name: String,
    val countryCode: String,
    val phoneNumber: String,
    val lat: Float,
    val lon: Float,
    val services: ArrayList<String>
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
        this.services
    )
}