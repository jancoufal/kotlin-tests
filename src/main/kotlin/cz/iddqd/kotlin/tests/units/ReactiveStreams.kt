package cz.iddqd.kotlin.tests.units

import cz.iddqd.kotlin.tests.UnitTest
import java.time.Duration
import java.util.concurrent.Flow
import java.util.concurrent.Flow.Subscriber
import java.util.concurrent.SubmissionPublisher

class ReactiveStreams : UnitTest {
	override fun go() {
		val publisher = SubmissionPublisher<Int>()

		with(publisher) {
			subscribe(Reader("Inky", -1))
			subscribe(Reader("Blinky", 0))
			subscribe(Reader("Pinky", 1))
			subscribe(Reader("Clyde", 2))
			subscribe(Reader("PacMan", Long.MAX_VALUE))
		}

		repeat(5) {
			println("Publishing $it")
			publisher.submit(it)
		}

		publisher.close()

		val sleepDuration = Duration.ofSeconds(1L)
		println("Waiting $sleepDuration to complete.")
		Thread.sleep(sleepDuration.toMillis())

		println("Publishing -1 after close")
		publisher.submit(-1)
	}

	class Reader(private val name: String, private val request: Long) : Subscriber<Int> {

		override fun onSubscribe(subscription: Flow.Subscription?) {
			println("$name.onSubscribe(subscription=$subscription)")
			println("$name.request($request)")
			subscription?.request(request)
		}

		override fun onNext(item: Int?) {
			println("$name.onNext(item=$item)")
		}

		override fun onError(throwable: Throwable?) {
			println("$name.onError(throwable=$throwable)")
		}

		override fun onComplete() {
			println("$name.onComplete()")
		}
	}
}