package cz.iddqd.kotlin.tests.units

import cz.iddqd.kotlin.tests.UnitTest
import kotlinx.coroutines.*

// see https://www.youtube.com/watch?v=k9yisEEPC8g
// see https://www.youtube.com/watch?v=55W60o9uzVc
class CoRoutine5a : UnitTest {
	override fun go() {
		println("CoRoutine5a::go() started")

		val job = GlobalScope.launch(Dispatchers.Default) {
			repeat(5) {
				println("CoRoutine5a job is still working ($it)...")
				delay(500L)
			}
		}

		runBlocking {
			println("CoRoutine5a giving job 1.2 secs to be completed")
			delay(1200L)
			job.cancel()

//			println("CoRoutine5 waiting for job to complete")
//			job.join()
		}

		println("CoRoutine5a::go() finished")
	}
}