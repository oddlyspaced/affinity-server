package oddlyspaced.surge.routing

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import oddlyspaced.surge.data.Location
import oddlyspaced.surge.data.Provider
import oddlyspaced.surge.data.distanceTo
import oddlyspaced.surge.data.parameter.ProviderParameter
import oddlyspaced.surge.data.parameter.SearchParameter
import oddlyspaced.surge.data.providers
import oddlyspaced.surge.data.parameter.toProviderInstance

fun Route.providerRouting() {
    route("/provider") {
        /**
         * register a provider
         */
        post("add") {
            val data = call.receive<ProviderParameter>()
            providers.add(data.toProviderInstance())
            call.respond(HttpStatusCode.OK, "Provider added successfully")
        }
        /**
         * fetches list of all providers
         */
        get("all") {
            call.respond(providers)
        }
        /**
         * searches for providers
         */
        get("search") {
            // add more params like name, phone, service
            val params = call.receive<SearchParameter>()
            val providedLocation = Location(params.searchFromLat, params.searchFromLon)
            val searchResult = arrayListOf<Provider>()
            providers.forEach { provider ->
                if (provider.location.distanceTo(providedLocation) <= params.limitDistance) {
                    searchResult.add(provider)
                }
                if (searchResult.size == params.limitCount) {
                    call.respond(HttpStatusCode.OK, searchResult)
                }
            }
            call.respond(HttpStatusCode.OK, searchResult)
        }
    }
}