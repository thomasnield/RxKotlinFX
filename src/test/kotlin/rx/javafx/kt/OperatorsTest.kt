package rx.javafx.kt

import javafx.application.Platform
import javafx.embed.swing.JFXPanel
import org.junit.Assert
import org.junit.Test
import rx.Observable
import rx.schedulers.Schedulers
import java.util.*
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger

class OperatorsTest {

    init {
        JFXPanel()
    }
    @Test
    fun doOnNextFxTest() {
        val latch = CountDownLatch(1)

        Observable.just("Alpha").observeOn(Schedulers.io()).doOnNextFx {
            Assert.assertTrue(Platform.isFxApplicationThread())
            latch.countDown()
        }.subscribe()

        latch.await(10, TimeUnit.SECONDS)
    }
    @Test
    fun doOnCompletedFxTest() {
        val latch = CountDownLatch(1)

        Observable.just("Alpha").observeOn(Schedulers.io()).doOnCompletedFx() {
            Assert.assertTrue(Platform.isFxApplicationThread())
            latch.countDown()
        }.subscribe()

        latch.await(10, TimeUnit.SECONDS)
    }

    @Test
    fun doOnSubscribeFx() {
        val latch = CountDownLatch(1)

        Observable.just("Alpha").observeOn(Schedulers.io()).doOnSubscribeFx {
            Assert.assertTrue(Platform.isFxApplicationThread())
            latch.countDown()
        }.subscribe()

        latch.await(10, TimeUnit.SECONDS)
    }

    @Test
    fun doOnTerminateFx() {
        val latch = CountDownLatch(1)

        Observable.just("Alpha").observeOn(Schedulers.io()).doOnTerminateFx {
            Assert.assertTrue(Platform.isFxApplicationThread())
            latch.countDown()
        }.subscribe()

        latch.await(10, TimeUnit.SECONDS)
    }

    @Test
    fun doOnUnsubscribeFxTest() {
        val latch = CountDownLatch(1)

        val subscription = Observable.interval(1, TimeUnit.SECONDS).doOnUnsubscribeFx {
            Assert.assertTrue(Platform.isFxApplicationThread())
            latch.countDown()
        }.subscribe()

        Thread.sleep(3000)
        subscription.unsubscribe()

        latch.await(10, TimeUnit.SECONDS)
    }
    @Test
    fun doOnErrorFxTest() {
        val latch = CountDownLatch(1)

        Observable.just(5).map { it / 0 }.observeOn(Schedulers.io()).doOnErrorFx {
            Assert.assertTrue(Platform.isFxApplicationThread())
            latch.countDown()
        }.onErrorResumeNext { Observable.empty() }.subscribe()

        latch.await()
    }
    @Test
    fun doOnNextCountTest() {
        val items: MutableList<Int> = ArrayList()

        Observable.just("Alpha","Beta","Gamma")
            .doOnNextCount { items.add(it) }
            .subscribe()

        Assert.assertTrue(items.containsAll(listOf(1,2,3)))
    }
    @Test
    fun doOnCompletedCountTest() {

        var value: Int? = null
        Observable.just("Alpha","Beta","Gamma")
                .doOnCompletedCount { value = it }
                .subscribe()

        Assert.assertTrue(value != null && value == 3)
    }
    @Test
    fun doOnErrorCountTest() {

        var value: Int? = null
        Observable.just(1,2,0,3)
                .map { 10 / it }
                .doOnErrorCount { value = it }
                .subscribe()

        Assert.assertTrue(value != null && value == 2)
    }
    @Test
    fun doOnNextCountFxTest() {
        val latch = CountDownLatch(3)
        var sum: Int = 0
        Observable.just(1,2,3)
            .doOnNextCountFx {
                Assert.assertTrue(Platform.isFxApplicationThread())
                sum += it
                latch.countDown()
            }.subscribe()

        latch.await(10,TimeUnit.SECONDS)
        assert(sum == 6)
    }
    @Test
    fun doOnCompletedCountFxTest() {
        val latch = CountDownLatch(1)
        var value: Int? = null
        Observable.just("Alpha","Beta","Gamma")
                .doOnCompletedCountFx {
                    Assert.assertTrue(Platform.isFxApplicationThread())
                    value = it
                    latch.countDown()
                }.subscribe()

        latch.await(10,TimeUnit.SECONDS)
        Assert.assertTrue(value == 3)
    }
    @Test
    fun doOnErrorCountFxTest() {
        val latch = CountDownLatch(1)
        var value: Int? = null
        Observable.just(1,3,0,5)
                .map { 10 / it }
                .doOnErrorCountFx {
                    Assert.assertTrue(Platform.isFxApplicationThread())
                    value = it
                    latch.countDown()
                }.subscribe()

        latch.await(10,TimeUnit.SECONDS)
        Assert.assertTrue(value == 2)
    }

    @Test
    fun bindingSideEffectsTest() {
        val counter = AtomicInteger(0)
        Observable.just(1,2,3)
                .toBinding {
                    onNext { counter.incrementAndGet() }
                    onCompleted { counter.incrementAndGet() }
                }

        Assert.assertTrue(counter.get() == 4)
    }

    @Test
    fun bindingSideEffectsErrorTest() {
        val counter = AtomicInteger(0)
        Observable.error<Unit>(Exception("Test"))
                .toBinding {
                    onError { counter.incrementAndGet() }
                }

        Assert.assertTrue(counter.get() == 1)
    }
}