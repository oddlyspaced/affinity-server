package oddlyspaced.surge.provider.routing

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import oddlyspaced.surge.provider.data.Provider
import oddlyspaced.surge.provider.data.ProviderAuth
import oddlyspaced.surge.provider.data.ProviderStatus
import oddlyspaced.surge.provider.data.ResponseError
import oddlyspaced.surge.provider.data.parameter.AreaUpdateParameter
import oddlyspaced.surge.provider.data.parameter.LocationUpdateParameter
import oddlyspaced.surge.provider.data.parameter.StatusUpdateParameter
import oddlyspaced.surge.providerAuths
import oddlyspaced.surge.providers


fun Route.providerRouting() {
    route("/provider") {
        /**
         * dummy authentication endpoint
         */
        post("authenticate") {
            try {
                val id = call.parameters["id"]?.toInt() ?: -1
                val pass = call.parameters["password"] ?: ""
                providerAuths.filter {
                    it.id == id
                }.let { filteredProviderAuth ->
                    if (filteredProviderAuth.size == 1) {
                        if (filteredProviderAuth[0].password == pass) {
                            call.respond(HttpStatusCode.OK, ResponseError("Authentication Successful", false))
                        }
                        else {
                            call.respond(HttpStatusCode.Forbidden, ResponseError("Authentication Unsuccessful"))
                        }
                    }
                    else {
                        call.respond(HttpStatusCode.Forbidden, ResponseError("Authentication Unsuccessful"))
                    }
                }
            }
            catch (e: Exception) {
                call.respond(HttpStatusCode.Forbidden, ResponseError("Authentication Unsuccessful"))
            }
        }
        /**
         * adds a provider to the list
         */
        post("add") {
            try {
                // todo: add check for id in order to
                val provider = call.receive<Provider>()
                provider.generateId()
                providers.add(provider)
                providerAuths.add(ProviderAuth(provider.id, "1234"))
                // todo check if this response can be improved
                call.respond(HttpStatusCode.OK, ResponseError("Provider Added successfully", error = false))
            }
            catch (e: Exception) {
                println(e.toString())
                e.printStackTrace()
                call.respond(HttpStatusCode.BadRequest, ResponseError(e.stackTraceToString()))
            }
        }
        post("remove") {
            try {
                val providerId = call.parameters["id"]?.toInt() ?: -1
                providers.filter {
                    it.id == providerId
                }.let {
                    if (it.isEmpty()) {
                        call.respond(HttpStatusCode.BadRequest, ResponseError("Provider ID not found"))
                    }
                    else {
                        providers.remove(it[0])
                        call.respond(HttpStatusCode.OK, ResponseError("Removed successfully", false))
                    }
                }
            }
            catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, ResponseError(e.stackTraceToString()))
            }
        }
        /**
         * gets all providers according to param
         * @param status should return all params including inactive ones
         */
        get("all") {
            try {
                val status = ProviderStatus.valueOf(call.parameters["status"] ?: ProviderStatus.UNDEFINED.toString())
                if (status == ProviderStatus.UNDEFINED) {
                    call.respond(HttpStatusCode.OK, providers)
                }
                else {
                    call.respond(HttpStatusCode.OK, providers.filter { provider ->
                        provider.status == status
                    })
                }
            }
            catch (e: Exception) {
                println(e.toString())
                e.printStackTrace()
                call.respond(HttpStatusCode.BadRequest, ResponseError(e.stackTraceToString()))
            }
        }
        /**
         * fetches a specific provider
         * @param id id of the provider
         */
        get("specific") {
            try {
                val id = call.parameters["id"]?.toInt() ?: -1
                call.respond(HttpStatusCode.OK, providers.find { provider ->
                    provider.id == id
                } ?: run {
                    throw Exception("Provider Not Found")
                })
            }
            catch (e: Exception) {
                println(e.toString())
                e.printStackTrace()
                call.respond(HttpStatusCode.BadRequest, ResponseError(e.stackTraceToString()))
            }
        }
        /**
         * searches for providers
         * todo: params
         */
        get("search") {
            try {
                // todo fix search logic
                call.respond(HttpStatusCode.OK, providers)
            }
            catch (e: Exception) {
                println(e.toString())
                e.printStackTrace()
                call.respond(HttpStatusCode.BadRequest, ResponseError(e.stackTraceToString()))
            }
        }
        route("/update") {
            post("location") {
                try {
                    val update = call.receive<LocationUpdateParameter>()
                    for (provider in providers) {
                        if (provider.id == update.id) {
                            provider.location = update.newLocation
                            call.respond(HttpStatusCode.OK, ResponseError("Location Updated Successfully"))
                        }
                    }
                    call.respond(HttpStatusCode.BadRequest, ResponseError("Provider not found!"))
                }
                catch (e: Exception) {
                    println(e.toString())
                    e.printStackTrace()
                    call.respond(HttpStatusCode.BadRequest, ResponseError(e.stackTraceToString()))
                }
            }
            post("status") {
                try {
                    val update = call.receive<StatusUpdateParameter>()
                    for (provider in providers) {
                        if (provider.id == update.id) {
                            provider.status = update.newStatus
                            call.respond(HttpStatusCode.OK, ResponseError("Status Updated Successfully"))
                        }
                    }
                    call.respond(HttpStatusCode.BadRequest, ResponseError("Provider not found!"))
                }
                catch (e: Exception) {
                    println(e.toString())
                    e.printStackTrace()
                    call.respond(HttpStatusCode.BadRequest, ResponseError(e.stackTraceToString()))
                }
            }
            post("area") {
                try {
                    val update = call.receive<AreaUpdateParameter>()
                    for (provider in providers) {
                        if (provider.id == update.id) {
                            provider.areaServed = update.newArea
                            call.respond(HttpStatusCode.OK, ResponseError("Area Served Updated Successfully"))
                        }
                    }
                    call.respond(HttpStatusCode.BadRequest, ResponseError("Provider not found!"))
                }
                catch (e: Exception) {
                    println(e.toString())
                    e.printStackTrace()
                    call.respond(HttpStatusCode.BadRequest, ResponseError(e.stackTraceToString()))
                }
            }
        }
        /**
         * gets all services
         */
        get("services") {
            try {
                call.respond(HttpStatusCode.OK, ResponseError("Provider Added"))
            }
            catch (e: Exception) {
                println(e.toString())
                e.printStackTrace()
                call.respond(HttpStatusCode.BadRequest, ResponseError(e.stackTraceToString()))
            }
        }
    }
}