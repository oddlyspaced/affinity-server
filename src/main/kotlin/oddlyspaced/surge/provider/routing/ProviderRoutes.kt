package oddlyspaced.surge.provider.routing

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import oddlyspaced.surge.generateData
import oddlyspaced.surge.provider.data.*
import oddlyspaced.surge.provider.data.parameter.ProviderParameter
import oddlyspaced.surge.provider.data.parameter.SearchParameter
import oddlyspaced.surge.provider.data.parameter.toProviderInstance
import oddlyspaced.surge.providers
import oddlyspaced.surge.search
import oddlyspaced.surge.services

fun Route.providerRouting() {
    route("/provider") {
        /**
         * fill dummy data
         */
        get("dummy") {
            generateData()
            call.respond(HttpStatusCode.OK, "Dummy data added. New length ${providers.size}")
        }
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
         * fetches list of all services
         */
        get("services") {
            call.respond(services)
        }
        /**
         * searches for providers
         */
        get("search") {
            // add more params like name, phone, service
            val params = call.receive<SearchParameter>()
            call.respond(HttpStatusCode.OK, search(params))
        }
    }
}