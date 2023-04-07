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

// see https://medium.com/swlh/singleton-design-pattern-with-kotlin-2e6c8d42fc11
class SingletonWithLazyAndObject private constructor() {

	private var counter: AtomicInteger
	init {
		println("!! Initializing ${this.javaClass.simpleName}")
		counter = AtomicInteger(0)
	}

	fun getCounter() = counter

	private object Holder {
		val INSTANCE = SingletonWithLazyAndObject()
	}
	companion object {
		val instance: SingletonWithLazyAndObject by lazy { Holder.INSTANCE }
	}
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

		println("Counter value (SingletonObject): ${SingletonObject.getCounter().get()}")
		println("Counter value (SingletonWithLazyAndObject): ${SingletonWithLazyAndObject.instance.getCounter().get()}")
	}

	class CounterIncrease(private val startSignal: CountDownLatch) : Thread() {
		override fun run() {
			startSignal.await()
			SingletonObject.getCounter().getAndIncrement()
			SingletonWithLazyAndObject.instance.getCounter().getAndIncrement()
		}
	}
}