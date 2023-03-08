package oddlyspaced.surge

import io.github.serpro69.kfaker.Faker
import oddlyspaced.surge.provider.data.*
import oddlyspaced.surge.provider.data.parameter.SearchParameter

val providers = arrayListOf<Provider>()
val providerAuths = arrayListOf<ProviderAuth>()

val sourcePoint = Location(26.882883,80.925594)
val services = arrayListOf<Service>()
val serviceRankMap = hashMapOf<String, Int>()

// todo: improve and make this piece of logic more efficient
fun generateData() {
    val fake = Faker()
    for (i in 0 until 10) {
        val tempProviderPos = Location(
            sourcePoint.lat + ((0..100).random() / 1000.0),
            sourcePoint.lon + ((0..100).random() / 1000.0),
        )
        val newProviderId = (providers.size + 1)
        providers.add(
            Provider(
                newProviderId,
                fake.name.name(),
                PhoneNumber(
                    fake.phoneNumber.countryCode.code(),
                    fake.phoneNumber.phoneNumber()
                ),
                tempProviderPos,
                (1..(1..10).random()).mapTo(arrayListOf()) {
                    fake.job.field()
                }.flat(),
                AreaServed(
                    tempProviderPos,
                    (2..8).random().toDouble()
                ),
                ProviderStatus.ACTIVE,
            )
        )
        providerAuths.add(
            ProviderAuth(newProviderId, "1234") // todo: fix this core logic
        )
    }
    generateServiceTags()
}

private fun generateServiceTags() {
    providers.forEach { provider ->
        provider.services.forEach { service ->
            serviceRankMap[service]?.let { count ->
                serviceRankMap[service] = count + 1
            } ?: run {
                serviceRankMap[service] = 1
            }
        }
    }
    serviceRankMap.toList().sortedBy {
        it.second
    }.reversed().let { rankedServiceList ->
        services.clear()
        rankedServiceList.mapTo(services) {
            Service(services.size.plus(1), it.first, it.second)
        }
    }
}

/**
 * handles search logic
 * copies over the provider list and cuts them short based on the parameters
 * @param params search param body from the api call
 * @return list of providers that qualify the params
 */
fun search(params: SearchParameter): List<Provider> {
    val results = providers.filter { provider ->
        doesProviderMatch(provider, params)
    }
    return if (results.size > params.limitCount) {
        results.sortedBy {
            it.location.distanceTo(params.pickupLocation)
        }.subList(0, params.limitCount)
    }
    else {
        results
    }
}

/**
 * checks if a provided provider instance qualifies with the provided search params
 * logic:
 * for a provider to qualify the pickup location and drop location must exist in their served area
 * their current location must exist withing the limit distance
 * their service and search filters must have some overlap if search filter is not empty
 * @param provider provider to check for
 * @param params search parameters from api call
 */
private fun doesProviderMatch(provider: Provider, params: SearchParameter): Boolean {
    val isPickupInRange = provider.areaServed.isPointInRadius(params.pickupLocation)
    val isDropInRange = provider.areaServed.isPointInRadius(params.dropLocation)
    val isProviderInRange = provider.location.distanceTo(params.pickupLocation) <= params.limitDistance.toDouble()
    val overlappingFilters = if (params.serviceFilters.isEmpty()) true else provider.services.intersect(params.serviceFilters.toSet()).isNotEmpty()

    return (isPickupInRange && isDropInRange && isProviderInRange && overlappingFilters)
}

fun ArrayList<String>.flat(): ArrayList<String> {
    val flattened = arrayListOf<String>()
    this.forEach {
        if (!flattened.contains(it)) {
            flattened.add(it)
        }
    }
    return flattened
}