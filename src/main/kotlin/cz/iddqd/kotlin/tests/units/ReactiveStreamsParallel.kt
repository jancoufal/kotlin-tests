package cz.iddqd.kotlin.tests.units

import cz.iddqd.kotlin.tests.UnitTest
import java.util.*
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Flow
import java.util.concurrent.SubmissionPublisher

class ReactiveStreamsParallel : UnitTest {
	override fun go() {
		val ingsoc = SubmissionPublisher<String>()

		val repeatCount = 5
		val propagandaCountDown = CountDownLatch(1)

		val ministries = listOf(
			Ministry(ingsoc, propagandaCountDown, repeatCount, "Peace", "War Is Peace"),
			Ministry(ingsoc, propagandaCountDown, repeatCount, "Plenty", "Chocolate is cheaper again"),
			Ministry(ingsoc, propagandaCountDown, repeatCount, "Truth", "Freedom Is Slavery"),
			Ministry(ingsoc, propagandaCountDown, repeatCount, "Love", "Ignorance Is Strength"),
		).onEach { println("Ministry of ${it.ministryName} awaits signals.") }

		val citizens = listOf(
			Citizen("Winston Smith", repeatCount * ministries.size),
			Citizen("Julia", repeatCount * ministries.size),
			Citizen("O'Brien", repeatCount * ministries.size),
		).onEach { println("Citizen ${it.name} listens.") }
			.onEach { ingsoc.subscribe(it) }


		println("1984 starts")

		ministries.forEach { it.start() }
		propagandaCountDown.countDown()

		ministries.forEach { it.join() }

		println("Waiting for citizens to be advised")
		citizens.forEach { it.indoctrinated() }

		println("1984 ends")
	}

	class Ministry(
		private val publisher: SubmissionPublisher<String>,
		private val propagandaCountDown: CountDownLatch,
		private val repeats: Int,
		val ministryName: String,
		private val message: String
	) : Thread(ministryName) {
		override fun run() {
			propagandaCountDown.await()
			println("Ministry of $ministryName accepts a signal.")
			Collections
				.nCopies(repeats, "Ministry of $ministryName saying '$message'")
				.forEach { publisher.submit(it) }
		}
	}

	class Citizen(val name: String, expectedMessages: Int) : Flow.Subscriber<String> {

		private val expectedMessagesCountDown = CountDownLatch(expectedMessages)
		override fun onSubscribe(subscription: Flow.Subscription?) = subscription?.request(Long.MAX_VALUE)!!
		override fun onNext(item: String?) {
			println("$name agreed with $item")
			expectedMessagesCountDown.countDown()
		}
		override fun onError(throwable: Throwable?) = println("$name confused with ${throwable?.javaClass?.simpleName}: ${throwable?.message}")
		override fun onComplete() = println("$name case closed")
		fun indoctrinated() = expectedMessagesCountDown.await()
	}

}