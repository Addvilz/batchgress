package com.github.addvilz.batchgress.log

import com.github.addvilz.batchgress.draw.Drawer
import java.io.File
import java.io.PrintWriter

class Logger(private val drawer: Drawer, logFile: File?) {
    enum class Level {
        INFO, WARNING, ERROR
    }

    private val writer: PrintWriter? = logFile?.printWriter()

    fun log(level: Level, message: String) {
        val logEntry = LogEntry(level, message)
        writer?.println(logEntry.toString())
        writer?.flush()
        drawer.drawLogEntry(logEntry)
    }

}
