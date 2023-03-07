package oddlyspaced.surge.util

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import oddlyspaced.surge.provider.data.Provider
import oddlyspaced.surge.providers
import java.io.File
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

object LocalStorageManager {
    fun saveProviderInfoToStorage() {
        try {
            val data = providers
            File("providers.json").let { file ->
                if (file.exists()) {
                    file.delete()
                }
                if (file.createNewFile()) {
                    file.writeText(Gson().toJson(data))
                    println("${getCurrentTimeStamp()}: ${providers.size} written to storage.")
                }
            }
        }
        catch (e: Exception) {
            e.printStackTrace()
            println("${getCurrentTimeStamp()}: Exception error while trying to write providers to storage.")
        }
    }
    fun loadProviderInfoFromStorage(): ArrayList<Provider> {
        try {
            File("providers.json").let { file ->
                if (file.exists()) {
                    val data = file.readLines().joinToString("")
                    return Gson().fromJson(data, object : TypeToken<ArrayList<Provider>>() {}.type)
                }
                else {
                    return arrayListOf()
                }
            }
        }
        catch (e: Exception) {
            e.printStackTrace()
            println("${getCurrentTimeStamp()}: Exception error while trying to write providers to storage.")
            return arrayListOf()
        }
    }

    private fun getCurrentTimeStamp(): String? {
        return DateTimeFormatter
            .ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS")
            .withZone(ZoneOffset.UTC)
            .format(Instant.now())
    }
}