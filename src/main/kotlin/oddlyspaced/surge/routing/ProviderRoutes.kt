package oddlyspaced.surge.routing

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import oddlyspaced.surge.data.ProviderParameter
import oddlyspaced.surge.data.providers
import oddlyspaced.surge.data.toProviderInstance

fun Route.providerRouting() {
    route("/provider") {
        post("add") {
            val data = call.receive<ProviderParameter>()
            providers.add(data.toProviderInstance())
            call.respond(HttpStatusCode.OK, "Provider added successfully")
        }
    }
}