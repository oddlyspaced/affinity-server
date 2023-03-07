package oddlyspaced.surge.util

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import oddlyspaced.surge.provider.data.Provider
import oddlyspaced.surge.providers
import java.io.File

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
                    Logger.println("${providers.size} written to storage.")
                }
            }
        }
        catch (e: Exception) {
            e.printStackTrace()
            Logger.println("Exception error while trying to write providers to storage.")
            Logger.println(e.stackTraceToString())
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
            Logger.println("Exception error while trying to write providers to storage.")
            Logger.println(e.stackTraceToString())
            return arrayListOf()
        }
    }
}