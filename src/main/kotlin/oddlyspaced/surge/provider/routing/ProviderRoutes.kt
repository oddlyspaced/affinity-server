package oddlyspaced.surge.provider.routing

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import oddlyspaced.surge.provider.data.ResponseError


fun Route.providerRouting() {
    route("/provider") {
        /**
         * adds a provider to the list
         */
        post("add") {
            try {
                call.respond(HttpStatusCode.OK, ResponseError("Provider Added"))
            }
            catch (e: Exception) {
                println(e.toString())
                e.printStackTrace()
                call.respond(HttpStatusCode.BadRequest, ResponseError(e.stackTraceToString()))
            }
        }
        /**
         * gets all providers according to param
         * @param all should return all params including inactive ones
         */
        get("all") {
            try {
                call.respond(HttpStatusCode.OK, ResponseError("Provider Added"))
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
                call.respond(HttpStatusCode.OK, ResponseError("Provider Added"))
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
                call.respond(HttpStatusCode.OK, ResponseError("Provider Added"))
            }
            catch (e: Exception) {
                println(e.toString())
                e.printStackTrace()
                call.respond(HttpStatusCode.BadRequest, ResponseError(e.stackTraceToString()))
            }
        }
        /**
         * updates info on a provider
         * todo: params
         */
        post("/update") {
            try {
                call.respond(HttpStatusCode.OK, ResponseError("Provider Added"))
            }
            catch (e: Exception) {
                println(e.toString())
                e.printStackTrace()
                call.respond(HttpStatusCode.BadRequest, ResponseError(e.stackTraceToString()))
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