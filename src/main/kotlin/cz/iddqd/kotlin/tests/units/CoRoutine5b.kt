package cz.iddqd.kotlin.tests.units

import cz.iddqd.kotlin.tests.UnitTest
import kotlinx.coroutines.*

// "too busy to cancel" version of https://www.youtube.com/watch?v=55W60o9uzVc
class CoRoutine5b : UnitTest {
	override fun go() {
		println("CoRoutine5b::go() started")

		val job = GlobalScope.launch(Dispatchers.Default) {
			for(i in 40..50) {
				if (isActive) { // comment this condition to make job so busy that it won't be cancelled
					println("CoRoutine5b job is still working ($i)...")
					println("fib($i) = ${fib(i)}")
				}
			}
		}

		runBlocking {
			println("CoRoutine5b giving job 1.2 secs to be completed")
			delay(1200L)
			job.cancel()
			println("CoRoutine5b job is cancelled")
		}

		println("CoRoutine5b::go() finished")
	}

	private fun fib(n: Int) : Long {
		return when (n) {
			0 -> 0
			1 -> 1
			else -> fib(n - 1) + fib(n - 2)
		}
	}
}