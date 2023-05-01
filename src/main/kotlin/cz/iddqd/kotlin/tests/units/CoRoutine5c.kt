package cz.iddqd.kotlin.tests.units

import cz.iddqd.kotlin.tests.UnitTest
import kotlinx.coroutines.*

// "job with timeout" version of https://www.youtube.com/watch?v=55W60o9uzVc
class CoRoutine5c : UnitTest {
	override fun go() {
		println("CoRoutine5c::go() started")

		GlobalScope.launch(Dispatchers.Default) {
			println("CoRoutine5c giving job 1.2 secs to be completed")
			withTimeout(1200L) {
				for (i in 40..50) {
					if (isActive) { // comment this condition to make job so busy that it won't be cancelled
						println("CoRoutine5c job is still working ($i)...")
						println("fib($i) = ${fib(i)}")
					}
				}
			}
		}

		println("CoRoutine5c::go() finished")
	}

	private fun fib(n: Int) : Long {
		return when (n) {
			0 -> 0
			1 -> 1
			else -> fib(n - 1) + fib(n - 2)
		}
	}
}