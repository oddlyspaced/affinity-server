package oddlyspaced.surge.provider.routing

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import oddlyspaced.surge.provider.data.Provider
import oddlyspaced.surge.provider.data.ProviderAuth
import oddlyspaced.surge.provider.data.ProviderStatus
import oddlyspaced.surge.provider.data.ResponseError
import oddlyspaced.surge.provider.data.parameter.AreaUpdateParameter
import oddlyspaced.surge.provider.data.parameter.LocationUpdateParameter
import oddlyspaced.surge.provider.data.parameter.SearchParameter
import oddlyspaced.surge.provider.data.parameter.StatusUpdateParameter
import oddlyspaced.surge.providerAuths
import oddlyspaced.surge.providers
import oddlyspaced.surge.search
import oddlyspaced.surge.services
import oddlyspaced.surge.util.Logger

// helper function to store call and error trace to logs
fun dumpCallToLog(msg: String, call: ApplicationCall, exception: Exception) {
    CoroutineScope(Dispatchers.IO).launch{
        Logger.println("-----")
        Logger.println(msg)
        Logger.println("${call.parameters}")
        Logger.println(call.receiveText())
        Logger.println(exception.stackTraceToString())
    }
}

/**
 * all provider related action endpoints
 */
fun Route.providerRouting() {
    route("/provider") {
        /**
         * gets all providers according to specified status
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
                dumpCallToLog("Error in all", call, e)
                call.respond(HttpStatusCode.BadRequest, ResponseError(e.stackTraceToString()))
            }
        }
        /**
         * fetches a specific provider
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
                dumpCallToLog("Error in specific", call, e)
                call.respond(HttpStatusCode.BadRequest, ResponseError(e.stackTraceToString()))
            }
        }
        /**
         * gets all services
         */
        get("services") {
            try {
                call.respond(HttpStatusCode.OK, services)
            }
            catch (e: Exception) {
                dumpCallToLog("Error in services", call, e)
                call.respond(HttpStatusCode.BadRequest, ResponseError(e.stackTraceToString()))
            }
        }
        /**
         * dummy authentication endpoint
         * todo: improve this
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
                dumpCallToLog("Error in authenticate", call, e)
                call.respond(HttpStatusCode.Forbidden, ResponseError("Authentication Unsuccessful"))
            }
        }
        /**
         * adds a provider to the list
         */
        post("add") {
            try {
                val provider = call.receive<Provider>()
                if (provider.id != -1) {
                    providers.filter {
                        it.id == provider.id
                    }.let {
                        if (it.isEmpty()) {
                            // ??? should not be possible ???
                        }
                        else {
                            // todo update this logic to be more efficient
                            providers[providers.indexOf(it[0])] = provider
                            call.respond(HttpStatusCode.OK, ResponseError("Provider Updated successfully", error = false))
                        }
                    }
                }
                else {
                    provider.generateId()
                    providers.add(provider)
                    providerAuths.add(ProviderAuth(provider.id, "1234"))
                    call.respond(HttpStatusCode.OK, ResponseError("Provider Added successfully", error = false))
                }
            }
            catch (e: Exception) {
                dumpCallToLog("Error in add", call, e)
                call.respond(HttpStatusCode.BadRequest, ResponseError(e.stackTraceToString()))
            }
        }
        /**
         * removes a provider
         */
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
                dumpCallToLog("Error in remove", call, e)
                call.respond(HttpStatusCode.BadRequest, ResponseError(e.stackTraceToString()))
            }
        }
        /**
         * searches for providers according to specified params
         */
        post("search") {
            try {
                val params = call.receive<SearchParameter>()
                call.respond(HttpStatusCode.OK, search(params))
            }
            catch (e: Exception) {
                dumpCallToLog("Error in search", call, e)
                call.respond(HttpStatusCode.BadRequest, ResponseError(e.stackTraceToString()))
            }
        }
        /**
         * update route
         */
        route("/update") {
            /**
             * update location of a provider
             */
            post("location") {
                try {
                    val update = call.receive<LocationUpdateParameter>()
                    for (provider in providers) {
                        if (provider.id == update.id) {
                            provider.location = update.newLocation
                            call.respond(HttpStatusCode.OK, ResponseError("Location Updated Successfully", false))
                        }
                    }
                    call.respond(HttpStatusCode.BadRequest, ResponseError("Provider not found!"))
                }
                catch (e: Exception) {
                    dumpCallToLog("Error in update", call, e)
                    call.respond(HttpStatusCode.BadRequest, ResponseError(e.stackTraceToString()))
                }
            }
            /**
             * update status of a provider
             */
            post("status") {
                try {
                    val update = call.receive<StatusUpdateParameter>()
                    for (provider in providers) {
                        if (provider.id == update.id) {
                            provider.status = update.newStatus
                            call.respond(HttpStatusCode.OK, ResponseError("Status Updated Successfully", false))
                        }
                    }
                    call.respond(HttpStatusCode.BadRequest, ResponseError("Provider not found!"))
                }
                catch (e: Exception) {
                    dumpCallToLog("Error in status", call, e)
                    call.respond(HttpStatusCode.BadRequest, ResponseError(e.stackTraceToString()))
                }
            }
            /**
             * update area of a provider
             */
            post("area") {
                try {
                    val update = call.receive<AreaUpdateParameter>()
                    for (provider in providers) {
                        if (provider.id == update.id) {
                            provider.areaServed = update.newArea
                            call.respond(HttpStatusCode.OK, ResponseError("Area Served Updated Successfully", false))
                        }
                    }
                    call.respond(HttpStatusCode.BadRequest, ResponseError("Provider not found!"))
                }
                catch (e: Exception) {
                    dumpCallToLog("Error in area", call, e)
                    call.respond(HttpStatusCode.BadRequest, ResponseError(e.stackTraceToString()))
                }
            }
        }
    }
}