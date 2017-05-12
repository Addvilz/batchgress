package com.github.addvilz.batchgress

import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicLong

class Counter {
    private val createdAt = System.nanoTime()
    private val counter: AtomicLong = AtomicLong()
    var total: Long? = null;

    fun getPercentFinished(): Double? {
        if (null == total) {
            return null
        }
        return ((counter.get().toDouble() / total!!.toDouble()) * 100.0)
    }

    fun getRemainingNanos(): Double? {
        if (null == total) {
            return null
        }
        val processed = get().toDouble()
        val elapsed = (System.nanoTime() - createdAt)
        return (elapsed * total!!.toDouble() / processed) - elapsed
    }

    fun getAvgRatio(): Double {
        return get().toDouble() / TimeUnit.NANOSECONDS.toSeconds((System.nanoTime() - createdAt))
    }

    fun incrementByOne(): Counter {
        counter.incrementAndGet()
        return this
    }

    fun incrementBy(delta: Long): Counter {
        counter.addAndGet(delta)
        return this
    }

    fun get(): Long {
        return counter.get()
    }

    override fun toString(): String {
        if (null != total) {
            val milliseconds = TimeUnit.NANOSECONDS.toMillis(getRemainingNanos()!!.toLong())

            val seconds = (milliseconds / 1000).toInt() % 60
            val minutes = milliseconds / (1000 * 60) % 60
            val hours = (milliseconds / (1000 * 60 * 60) % 24)

            try {
                return "%d of %d (%f/s), %f%% done (%dh %dm %ds remaining)".format(
                        get(),
                        total,
                        getAvgRatio(),
                        getPercentFinished(),
                        hours,
                        minutes,
                        seconds
                )
            } catch (e: Exception) {
                e.printStackTrace()
                return ""
            }
        }

        return "%d (%f/s)".format(
                get(),
                getAvgRatio()
        )
    }
}
