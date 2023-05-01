package cz.iddqd.kotlin.tests

import cz.iddqd.kotlin.tests.units.*

fun main() {
	println("Starting...")

	val tests = listOf(
//		ReactiveStreams(),
//		ReactiveStreamsParallel(),
//		ThreadSafeSingleton(),
		CoRoutine1(),
	)

	tests.forEach {
		println("Running ${it.javaClass.simpleName}...")
		try {
			it.go()
			println("...ok")
		} catch (e: Exception) {
			println("...failed due to ${e.javaClass.simpleName}: ${e.message}")
		}
	}

	// to test the coroutines
	println("sleeping 1 sec")
	Thread.sleep(1000L)

	// Try adding program arguments via Run/Debug configuration.
	// Learn more about running applications: https://www.jetbrains.com/help/idea/running-applications.html.
	println("done.")
}