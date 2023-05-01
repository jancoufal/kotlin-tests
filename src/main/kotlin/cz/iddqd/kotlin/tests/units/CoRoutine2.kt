package cz.iddqd.kotlin.tests.units

import cz.iddqd.kotlin.tests.UnitTest
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// see https://www.youtube.com/watch?v=yc_WfBp-PdE
class CoRoutine2 : UnitTest {
	override fun go() {
		GlobalScope.launch {
			doNetworkCall()
		}

		println("CoRoutine2::go() finished")
	}

	private suspend fun doNetworkCall() {
		delay(500L)
		println("CoRoutine2::doNetworkCall() finished in thread '${Thread.currentThread().name}'")
	}
}