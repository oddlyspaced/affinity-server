package oddlyspaced.surge.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import oddlyspaced.surge.provider.data.ResponseError
import oddlyspaced.surge.provider.routing.providerRouting

fun Application.configureRouting() {

    routing {
        get("/ping") {
            call.respond(HttpStatusCode.OK, ResponseError("pong", false))
        }

        providerRouting()
    }
}
