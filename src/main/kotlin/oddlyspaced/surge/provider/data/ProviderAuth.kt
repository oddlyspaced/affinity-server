package oddlyspaced.surge.provider.data

/**
 * data class to hold authentication object for a given provider
 */
data class ProviderAuth(
    val id: Int, // provider id
    val password: String, // auth info [todo: replace with proper and something better in future]
)