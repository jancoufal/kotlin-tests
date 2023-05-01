package cz.iddqd.kotlin.tests.units

import cz.iddqd.kotlin.tests.UnitTest
import kotlinx.coroutines.*

// see https://www.youtube.com/watch?v=71NrkkRNXG4
class CoRoutine3 : UnitTest {
	override fun go() {

//		GlobalScope.launch(newSingleThreadContext("CoRoutine3-Thread"))

		GlobalScope.launch(Dispatchers.IO) {
			println("CoRoutine3 running network calls in '${Thread.currentThread().name}' thread")
			val networkCallAnswer1 = doNetworkCall1()
			val networkCallAnswer2 = doNetworkCall2()
			withContext(Dispatchers.Default) {
				println("CoRoutine3::doNetworkCall1() running in '${Thread.currentThread().name}' thread returned '$networkCallAnswer1'")
				println("CoRoutine3::doNetworkCall2() running in '${Thread.currentThread().name}' thread returned '$networkCallAnswer2'")
			}
		}
		println("CoRoutine3::go() finished")
	}

	private suspend fun doNetworkCall1(): String {
		delay(300L)
		return "CoRoutine3::doNetworkCall1() answer"
	}

	private suspend fun doNetworkCall2(): String {
		delay(300L)
		return "CoRoutine3::doNetworkCall2() answer"
	}

}