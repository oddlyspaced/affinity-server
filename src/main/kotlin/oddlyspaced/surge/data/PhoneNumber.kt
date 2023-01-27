package oddlyspaced.surge.data

import kotlinx.serialization.Serializable

/**
 * data class to store phone number representation
 */
@Serializable
data class PhoneNumber(
    val countryCode: String,// country code
    val phoneNumber: String,// phone number
)