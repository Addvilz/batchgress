package com.github.addvilz.batchgress.draw

import com.github.addvilz.batchgress.Batchgress.Companion
import com.github.addvilz.batchgress.Counter
import com.github.addvilz.batchgress.log.LogEntry
import com.google.common.collect.EvictingQueue
import com.google.common.collect.Queues.synchronizedQueue
import java.io.PrintStream
import java.util.*
import java.util.concurrent.ConcurrentMap

class ExtendedDrawer(private val out: PrintStream, numLogLines: Int) : Drawer {
    private val log: Queue<LogEntry> = synchronizedQueue(EvictingQueue.create<LogEntry>(numLogLines))

    override fun drawLogEntry(entry: LogEntry) {
        log.add(entry)
    }

    override fun draw(counters: ConcurrentMap<String, Counter>) {
        out.print(Companion.ANSI_CLS + Companion.ANSI_HOME)
        out.flush()

        val maxKey: Int = counters.keys.stream().map { it.length }.mapToInt { it }.max().orElse(0)

        counters.entries.forEach {
            out.println("> %s: %s".format(
                    it.key.padEnd(maxKey),
                    it.value.toString()
            ))
        }

        if (log.size > 0) {
            out.println("\n##### LOG #####\n")
        }

        log.toTypedArray().forEach {
            out.println(it.toString())
        }

        out.flush()
    }

}
