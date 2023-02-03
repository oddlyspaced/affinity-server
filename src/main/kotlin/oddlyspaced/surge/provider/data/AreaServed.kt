package oddlyspaced.surge.provider.data

import kotlinx.serialization.Serializable

/**
 * stores info regarding the area served by a provider
 */
@Serializable
data class AreaServed(
    val source: Location,
    val radius: Double,
)

// checks if a provided location lies in the radius
fun AreaServed.isPointInRadius(point: Location): Boolean {
    return this.source.distanceTo(point) <= radius
}