package cz.iddqd.kotlin.tests.units

import cz.iddqd.kotlin.tests.UnitTest
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// see https://www.youtube.com/watch?v=kvfpuzSwVZ8
class CoRoutine1 : UnitTest {
	override fun go() {

		// probably won't be printed out as the main thread is finished before the coroutine is executed
		GlobalScope.launch {
			delay(500L)
			println("CoRoutine1 coroutine in GlobalScope from thread '${Thread.currentThread().name}'")
		}

		println("CoRoutine1::go() finished")
	}
}