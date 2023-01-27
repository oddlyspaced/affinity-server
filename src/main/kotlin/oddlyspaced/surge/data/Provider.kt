package oddlyspaced.surge.data

import kotlinx.serialization.Serializable

/**
 * data class to hold the properties of a Provider
 */
@Serializable
data class Provider(
    val id: Long, // unique id for provider
    val name: String, // name of provider
    val phone: PhoneNumber, // phone number of provider
    val location: Location,
    val services: ArrayList<String>, // tags of all the services the provider offers
)