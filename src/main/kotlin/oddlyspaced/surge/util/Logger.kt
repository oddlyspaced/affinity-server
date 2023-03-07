package oddlyspaced.surge.util

import java.io.File
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*

object Logger {
    private val logFile: File = File("log.txt")

    fun print(text: String) {
        val op = "${getCurrentTimeStamp()}: $text"
        kotlin.io.print(op)
        logFile.appendText(op)
    }

    fun println(text: String) {
        print(text + "\n")
    }

    private fun getCurrentTimeStamp(): String? {
        return DateTimeFormatter
            .ofPattern("yyyy-MM-dd HH:mm:ss")
            .withZone(ZoneOffset.UTC)
            .format(Instant.now())
    }
}