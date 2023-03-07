package oddlyspaced.surge

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import oddlyspaced.surge.plugins.configureRouting
import oddlyspaced.surge.plugins.configureSerialization
import oddlyspaced.surge.util.LocalStorageManager
import oddlyspaced.surge.util.Logger
import java.net.InetAddress
import kotlin.reflect.jvm.internal.impl.descriptors.Visibilities.Local

fun main() {
    println("Starting server at: ${InetAddress.getLocalHost().hostAddress}:${AffinityConfiguration.APPLICATION_PORT}")
    if (AffinityConfiguration.GENERATE_DUMMY_DATA) {
        Logger.println("Generating Dummy Data as requested")
        generateData()
        Logger.println("Generated ${providers.size} dummy providers")
    }
    if (AffinityConfiguration.RESTORE_DATA_ON_BOOT) {
        // restore data
        Logger.println("Restoring data from storage as requested")
        LocalStorageManager.loadProviderInfoFromStorage().let { loadedProviders ->
            Logger.println("Loaded ${loadedProviders.size} providers from storage")
            providers.addAll(loadedProviders)
        }
    }
    if (AffinityConfiguration.SAVE_DATA_TO_STORAGE) {
        Logger.println("Data Storage Polling requested at ${AffinityConfiguration.SAVE_DATA_INTERVAL} intervals")
        pollDataStorage()
    }
    // server runs blocking the main thread
    embeddedServer(Netty, port = AffinityConfiguration.APPLICATION_PORT, module = Application::module).start(wait = true)
}

private fun pollDataStorage() {
    CoroutineScope(Dispatchers.IO).launch {
        while (true) {
            delay(AffinityConfiguration.SAVE_DATA_INTERVAL)
            LocalStorageManager.saveProviderInfoToStorage()
        }
    }
}

fun Application.module() {
    configureSerialization()
    configureRouting()
}