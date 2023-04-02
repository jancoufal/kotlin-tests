package cz.iddqd.kotlin.tests

import cz.iddqd.kotlin.tests.units.ReactiveStreams

fun main() {
    println("Starting...")

    val tests = listOf<UnitTest>(
        ReactiveStreams()
    )

    tests.forEach {
        println("Running ${it.javaClass}...")
        try {
            it.go()
            println("...ok")
        }
        catch (e: Exception) {
            println("...failed due to ${e.javaClass}: ${e.message}")
        }
    }

    // Try adding program arguments via Run/Debug configuration.
    // Learn more about running applications: https://www.jetbrains.com/help/idea/running-applications.html.
    println("done.")
}