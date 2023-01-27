package oddlyspaced.surge.routing

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import oddlyspaced.surge.data.Node
import oddlyspaced.surge.data.NodeData
import oddlyspaced.surge.data.nodes

fun Route.nodeRouting() {
    route("/node") {
        get {
            if (nodes.isNotEmpty()) {
                call.respond(nodes)
            }
            else {
                call.respondText("No nodes registered!")
            }
        }
        get("{id?}") {
            val id = call.parameters["id"] ?: return@get call.respondText("Missing id", status = HttpStatusCode.BadRequest)
            val node = nodes.find { it.id == id } ?: return@get call.respondText("No node with id: $id", status = HttpStatusCode.NotFound)
            call.respond(node)
        }
        post("register") {
            val nodeData = call.receive<NodeData>()
            nodes.add(Node(nodes.size.toString(), nodeData))
            call.respondText("Node successfully: ")
        }
    }
}