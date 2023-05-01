package cz.iddqd.kotlin.tests.units

import cz.iddqd.kotlin.tests.UnitTest
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// see https://www.youtube.com/watch?v=yc_WfBp-PdE
class CoRoutine2 : UnitTest {
	override fun go() {
		GlobalScope.launch {
			val networkCallAnswer1 = doNetworkCall1()
			val networkCallAnswer2 = doNetworkCall2()
			println("CoRoutine2::doNetworkCall1() running in '${Thread.currentThread().name}' thread returned '$networkCallAnswer1'")
			println("CoRoutine2::doNetworkCall2() running in '${Thread.currentThread().name}' thread returned '$networkCallAnswer2'")
		}

		println("CoRoutine2::go() finished")
	}

	private suspend fun doNetworkCall1(): String {
		delay(300L)
		return "CoRoutine2::doNetworkCall1() answer"
	}

	private suspend fun doNetworkCall2(): String {
		delay(300L)
		return "CoRoutine2::doNetworkCall2() answer"
	}
}