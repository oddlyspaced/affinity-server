package oddlyspaced.surge

import io.github.serpro69.kfaker.Faker
import oddlyspaced.surge.provider.data.Location
import oddlyspaced.surge.provider.data.PhoneNumber
import oddlyspaced.surge.provider.data.Provider
import oddlyspaced.surge.provider.data.Service

val providers = arrayListOf<Provider>()

val sourcePoint = Location(26.882883,80.925594)
private val serviceTagNames = arrayListOf<String>()

val services = arrayListOf<Service>()

// todo: improve and make this piece of logic more efficient
fun generateData() {
    val fake = Faker()
    for (i in 0..25) {
        providers.add(
            Provider(
                (providers.size + 1),
                fake.name.name(),
                PhoneNumber(
                    fake.phoneNumber.countryCode.code(),
                    fake.phoneNumber.phoneNumber()
                ),
                Location(
                    sourcePoint.lat + ((0..100).random() / 1000.0),
                    sourcePoint.lon + ((0..100).random() / 1000.0),
                ),
                (1..(1..10).random()).mapTo(arrayListOf()) {
                    fake.adjective.positive()
                }.flat()
            )
        )
        serviceTagNames.addAll(providers[i].services)
    }
    serviceTagNames.flat().let {
        serviceTagNames.clear()
        it.forEach { service ->
            services.add(Service(services.size.plus(1), service))
        }
    }
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