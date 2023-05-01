package cz.iddqd.kotlin.tests.units

import cz.iddqd.kotlin.tests.UnitTest
import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

class CoRoutine6b : UnitTest {
	override fun go() {
		println("CoRoutine6b before launch in '${Thread.currentThread().name}' thread")
		GlobalScope.launch(Dispatchers.IO) {
			val timeTaken = measureTimeMillis {
				val networkCallAnswer1 = async { doNetworkCall1() }
				val networkCallAnswer2 = async { doNetworkCall2() }
				println("CoRoutine6b::doNetworkCall1() running in '${Thread.currentThread().name}' thread returned '${networkCallAnswer1.await()}'")
				println("CoRoutine6b::doNetworkCall2() running in '${Thread.currentThread().name}' thread returned '${networkCallAnswer2.await()}'")
			}
			println("CoRoutine6b both network calls taken $timeTaken ms")
		}
		println("CoRoutine6b finished")
	}

	private suspend fun doNetworkCall1(): String {
		delay(300L)
		return "CoRoutine6b::doNetworkCall1() answer"
	}

	private suspend fun doNetworkCall2(): String {
		delay(300L)
		return "CoRoutine6b::doNetworkCall2() answer"
	}
}