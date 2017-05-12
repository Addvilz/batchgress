package com.github.addvilz.batchgress.draw

import com.github.addvilz.batchgress.Counter
import com.github.addvilz.batchgress.log.LogEntry
import java.util.concurrent.ConcurrentMap

interface Drawer {
    fun draw(counters: ConcurrentMap<String, Counter>)
    fun drawLogEntry(entry: LogEntry)
}
