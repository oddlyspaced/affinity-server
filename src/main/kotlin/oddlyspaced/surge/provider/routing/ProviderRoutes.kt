package oddlyspaced.surge.provider.routing

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import oddlyspaced.surge.provider.data.CallResponse


fun Route.providerRouting() {
    route("/provider") {
        /**
         * adds a provider to the list
         */
        post("add") {
            try {
                call.respond(HttpStatusCode.OK, CallResponse(success = true, "Provider Added"))
            }
            catch (e: Exception) {
                println(e.toString())
                e.printStackTrace()
                call.respond(HttpStatusCode.BadRequest, CallResponse(success = false, e.stackTraceToString()))
            }
        }
        /**
         * gets all providers according to param
         * @param all should return all params including inactive ones
         */
        get("all") {
            try {
                call.respond(HttpStatusCode.OK, CallResponse(success = true, "Provider Added"))
            }
            catch (e: Exception) {
                println(e.toString())
                e.printStackTrace()
                call.respond(HttpStatusCode.BadRequest, CallResponse(success = false, e.stackTraceToString()))
            }
        }
        /**
         * fetches a specific provider
         * @param id id of the provider
         */
        get("specific") {
            try {
                call.respond(HttpStatusCode.OK, CallResponse(success = true, "Provider Added"))
            }
            catch (e: Exception) {
                println(e.toString())
                e.printStackTrace()
                call.respond(HttpStatusCode.BadRequest, CallResponse(success = false, e.stackTraceToString()))
            }
        }
        /**
         * searches for providers
         * todo: params
         */
        get("search") {
            try {
                call.respond(HttpStatusCode.OK, CallResponse(success = true, "Provider Added"))
            }
            catch (e: Exception) {
                println(e.toString())
                e.printStackTrace()
                call.respond(HttpStatusCode.BadRequest, CallResponse(success = false, e.stackTraceToString()))
            }
        }
        /**
         * updates info on a provider
         * todo: params
         */
        post("/update") {
            try {
                call.respond(HttpStatusCode.OK, CallResponse(success = true, "Provider Added"))
            }
            catch (e: Exception) {
                println(e.toString())
                e.printStackTrace()
                call.respond(HttpStatusCode.BadRequest, CallResponse(success = false, e.stackTraceToString()))
            }
        }
        /**
         * gets all services
         */
        get("services") {
            try {
                call.respond(HttpStatusCode.OK, CallResponse(success = true, "Provider Added"))
            }
            catch (e: Exception) {
                println(e.toString())
                e.printStackTrace()
                call.respond(HttpStatusCode.BadRequest, CallResponse(success = false, e.stackTraceToString()))
            }
        }
    }
}