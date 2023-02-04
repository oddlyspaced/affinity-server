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
            try {
                val data = call.receive<ProviderParameter>()
                providers.add(data.toProviderInstance())
                call.respond(HttpStatusCode.OK, "Provider added successfully")
            }
            catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, "Error!")
                e.printStackTrace()
            }
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
        post("search") {
            // add more params like name, phone, service
            println("ok")
            try {
                println("omm : " + call.parameters.names())
                val params = call.parameters
                val searchParam = SearchParameter(
                    params["limitCount"]!!.toInt(),
                    params["limitDistance"]!!.toInt(),
                    params["pickupLat"]!!.toDouble(),
                    params["pickupLon"]!!.toDouble(),
                    params["dropLat"]!!.toDouble(),
                    params["dropLon"]!!.toDouble()
                )
                println(params)
                call.respond(HttpStatusCode.OK, search(searchParam))
            }
            catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}