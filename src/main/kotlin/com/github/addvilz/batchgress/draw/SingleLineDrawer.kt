package com.github.addvilz.batchgress.draw

import com.github.addvilz.batchgress.Counter
import com.github.addvilz.batchgress.log.LogEntry
import java.io.PrintStream
import java.util.concurrent.ConcurrentMap

class SingleLineDrawer(private val out: PrintStream) : Drawer {
    override fun drawLogEntry(entry: LogEntry) {
        out.println("\n> " + entry.toString())
    }

    override fun draw(
            counters: ConcurrentMap<String, Counter>
    ) {
        out.print("\r" + counters.toString())
    }

}
