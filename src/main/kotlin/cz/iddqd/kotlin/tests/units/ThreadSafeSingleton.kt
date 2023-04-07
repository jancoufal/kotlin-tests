package cz.iddqd.kotlin.tests.units

import cz.iddqd.kotlin.tests.UnitTest
import java.util.concurrent.CountDownLatch
import java.util.concurrent.atomic.AtomicInteger
import java.util.stream.IntStream
import kotlin.streams.toList

object SingletonObject {

	private var counter: AtomicInteger

	init {
		println("!! Initializing ${this.javaClass.simpleName}")
		counter = AtomicInteger(0)
	}

	fun getCounter() = counter
}

class ThreadSafeSingleton : UnitTest {
	override fun go() {

		val threadCount = 100
		val startSignal = CountDownLatch(1)

		println("Creating $threadCount threads...")
		val increaseList = IntStream.range(0, threadCount).toList()
			.map { CounterIncrease(startSignal) }

		println("Starting ${increaseList.count()} threads...")
		increaseList.forEach { it.start() }

		println("Signaling ${increaseList.count()} threads...")
		startSignal.countDown()

		println("Waiting for ${increaseList.count()} threads...")
		increaseList.forEach { it.join() }

		println("Counter value: ${SingletonObject.getCounter().get()}")
	}

	class CounterIncrease(private val startSignal: CountDownLatch) : Thread() {
		override fun run() {
			startSignal.await()
			SingletonObject.getCounter().getAndIncrement()
		}
	}
}