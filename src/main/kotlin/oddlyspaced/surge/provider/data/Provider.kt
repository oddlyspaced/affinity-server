package oddlyspaced.surge.provider.data

import kotlinx.serialization.Serializable
import oddlyspaced.surge.providers

/**
 * data class to hold the properties of a Provider
 */
@Serializable
data class Provider(
    var id: Int = -1, // unique id for provider [-1 for default not initialized]
    val name: String, // name of provider
    val phone: PhoneNumber, // phone number of provider
    var location: Location,
    val services: ArrayList<String>, // tags of all the services the provider offers
    var areaServed: AreaServed, // area served by provider
    var isActive: Boolean,
) {
    fun generateId() {
        this.id = providers.size + 1
    }
}