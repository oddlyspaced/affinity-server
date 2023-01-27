package oddlyspaced.surge

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import oddlyspaced.surge.data.nodes
import oddlyspaced.surge.plugins.*

fun main() {
//    scan()
    embeddedServer(Netty, port = 4444, module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureSerialization()
    configureRouting()
}

fun scan() {
    CoroutineScope(Dispatchers.IO).launch {
        while (true) {
            println(nodes.size)
            delay(5000)
        }
    }
}