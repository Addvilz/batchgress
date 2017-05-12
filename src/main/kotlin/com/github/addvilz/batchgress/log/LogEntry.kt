package com.github.addvilz.batchgress.log

import java.util.*

class LogEntry(private val level: Logger.Level, private val message: String) {
    private var date: Date = Date(System.currentTimeMillis())

    override fun toString(): String {
        return "%s [%s] %s".format(date, level.name, message)
    }
}
