package cz.iddqd.kotlin.tests.units

import cz.iddqd.kotlin.tests.UnitTest
import kotlinx.coroutines.*

class CoRoutine4 : UnitTest {
	override fun go() {
		println("CoRoutine4 before runBlocking in '${Thread.currentThread().name}' thread")

		runBlocking {
			println("CoRoutine4 runBlocking started in '${Thread.currentThread().name}' thread")

			launch(Dispatchers.IO) {
				delay(1000L)
				println("CoRoutine4 launch #1 in runBlocking started in '${Thread.currentThread().name}' thread")
			}

			launch(Dispatchers.IO) {
				delay(1000L)
				println("CoRoutine4 launch #2 in runBlocking started in '${Thread.currentThread().name}' thread")
			}

			delay(300L)
			println("CoRoutine4 runBlocking finished in '${Thread.currentThread().name}' thread")
		}

		println("CoRoutine4 after runBlocking in '${Thread.currentThread().name}' thread")
		println("CoRoutine4::go() finished")
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