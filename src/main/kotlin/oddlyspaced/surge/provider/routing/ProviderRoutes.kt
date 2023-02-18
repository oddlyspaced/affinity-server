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
            call.respond(providers.filter {
                it.isActive
            })
        }
        /**
         * fetches list of providers without filtering
         * todo: rename this endpoint
         */
        get("every") {
            call.respond(providers)
        }
        get("specific") {
            val params = call.parameters
            providers.forEach {
                if (it.id == params["id"]!!.toInt()) {
                    call.respond(HttpStatusCode.OK, it)
                }
            }
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
        post("areaupdate") {
            // id, sourceLat, sourceLon, radius
            val params = call.parameters
            providers.forEach { provider ->
                if (provider.id == params["id"]!!.toInt()) {
                    provider.areaServed = AreaServed(Location(params["sourceLat"]!!.toDouble(), params["sourceLon"]!!.toDouble()), params["radius"]!!.toDouble())
                }
            }
            call.respond(HttpStatusCode.OK, "Area Updated Successfully")
        }
        post("statusupdate") {
            // id isActive
            val params = call.parameters
            providers.forEach { provider ->
                if (provider.id == params["id"]!!.toInt()) {
                    provider.isActive = params["isActive"]!!.toBoolean()
                }
            }
            call.respond(HttpStatusCode.OK, "Status Updated Successfully")
        }
        post("locationupdate") {
            val params = call.parameters
            // id lat lon
            providers.forEach { provider ->
                if (provider.id == params["id"]!!.toInt()) {
                    provider.location = Location(params["lat"]!!.toDouble(), params["lon"]!!.toDouble())
                }
            }
            call.respond(HttpStatusCode.OK, "Location Updated Successfully")
        }
    }
}