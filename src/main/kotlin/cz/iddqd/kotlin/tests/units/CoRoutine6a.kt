package cz.iddqd.kotlin.tests.units

import cz.iddqd.kotlin.tests.UnitTest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.system.measureTimeMillis

// see https://www.youtube.com/watch?v=t-3TOke8tq8
class CoRoutine6a : UnitTest {
	override fun go() {
		println("CoRoutine6a before launch in '${Thread.currentThread().name}' thread")
		GlobalScope.launch(Dispatchers.IO) {
			val timeTaken = measureTimeMillis {
				val networkCallAnswer1 = doNetworkCall1()
				val networkCallAnswer2 = doNetworkCall2()
				println("CoRoutine6a::doNetworkCall1() running in '${Thread.currentThread().name}' thread returned '$networkCallAnswer1'")
				println("CoRoutine6a::doNetworkCall2() running in '${Thread.currentThread().name}' thread returned '$networkCallAnswer2'")
			}
			println("CoRoutine6a both network calls taken $timeTaken ms")
		}
		println("CoRoutine6a finished")
	}

	private suspend fun doNetworkCall1(): String {
		delay(300L)
		return "CoRoutine6a::doNetworkCall1() answer"
	}

	private suspend fun doNetworkCall2(): String {
		delay(300L)
		return "CoRoutine6a::doNetworkCall2() answer"
	}
}