package oddlyspaced.surge

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import oddlyspaced.surge.plugins.configureRouting
import oddlyspaced.surge.plugins.configureSerialization

fun main() {
    embeddedServer(Netty, port = 4444, module = Application::module).start(wait = true)
}

fun Application.module() {
    configureSerialization()
    configureRouting()
}