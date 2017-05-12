package com.github.addvilz.batchgress

import com.github.addvilz.batchgress.draw.Drawer
import com.github.addvilz.batchgress.draw.ExtendedDrawer
import com.github.addvilz.batchgress.draw.SingleLineDrawer
import com.github.addvilz.batchgress.log.Logger
import java.io.File
import java.io.PrintStream
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap
import kotlin.concurrent.fixedRateTimer

class Batchgress(
        out: PrintStream,
        numLogLines: Int = 10,
        logFile: File? = null
) {
    companion object {
        val ANSI_CLS = "\u001b[2J"
        val ANSI_HOME = "\u001b[H"
    }

    private val counters: ConcurrentMap<String, Counter> = ConcurrentHashMap<String, Counter>()
    private val drawer: Drawer
    private val logger: Logger

    constructor() : this(System.out)

    init {
        when (supportsExtendedDraw()) {
            true ->
                drawer = ExtendedDrawer(out, numLogLines)
            false ->
                drawer = SingleLineDrawer(out)
        }
        logger = Logger(drawer, logFile)
    }

    private fun supportsExtendedDraw(): Boolean {
        val term: String? = System.getenv("TERM")
        when (term) {
            null ->
                return false
            "xterm", "xterm-256color" ->
                return true

        }
        return false
    }

    fun getCounter(key: String): Counter {
        return counters.getOrPut(key, { Counter() })
    }

    fun draw() {
        drawer.draw(counters)
    }

    fun drawWithInterval(millis: Long): Timer {
        return fixedRateTimer(period = millis) {
            draw()
        }
    }

    fun logInfo(message: String) {
        logger.log(Logger.Level.INFO, message)
    }

    fun logWarning(message: String) {
        logger.log(Logger.Level.WARNING, message)
    }

    fun logError(message: String) {
        logger.log(Logger.Level.ERROR, message)
    }
}

