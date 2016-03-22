package rx.javafx.kt

import javafx.application.Platform
import javafx.embed.swing.JFXPanel
import org.junit.Assert
import org.junit.Test
import rx.Observable
import rx.schedulers.Schedulers
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

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

        latch.await()
    }
    @Test
    fun doOnCompletedFxTest() {
        val latch = CountDownLatch(1)

        Observable.just("Alpha").observeOn(Schedulers.io()).doOnCompletedFx() {
            Assert.assertTrue(Platform.isFxApplicationThread())
            latch.countDown()
        }.subscribe()

        latch.await()
    }

    @Test
    fun doOnSubscribeFx() {
        val latch = CountDownLatch(1)

        Observable.just("Alpha").observeOn(Schedulers.io()).doOnSubscribeFx {
            Assert.assertTrue(Platform.isFxApplicationThread())
            latch.countDown()
        }.subscribe()

        latch.await()
    }

    @Test
    fun doOnTerminateFx() {
        val latch = CountDownLatch(1)

        Observable.just("Alpha").observeOn(Schedulers.io()).doOnTerminateFx {
            Assert.assertTrue(Platform.isFxApplicationThread())
            latch.countDown()
        }.subscribe()

        latch.await()
    }

    @Test
    fun doOnUnsubscribeTest() {
        val latch = CountDownLatch(1)

        val subscription = Observable.interval(1, TimeUnit.SECONDS).doOnUnsubscribeFx {
            Assert.assertTrue(Platform.isFxApplicationThread())
            latch.countDown()
        }.subscribe()

        Thread.sleep(3000)
        subscription.unsubscribe()

        latch.await()
    }
    @Test
    fun doOnErrorTest() {
        val latch = CountDownLatch(1)

        Observable.just(5).map { it / 0 }.observeOn(Schedulers.io()).doOnErrorFx {
            Assert.assertTrue(Platform.isFxApplicationThread())
            latch.countDown()
        }.onErrorResumeNext { Observable.empty() }.subscribe()

        latch.await()
    }
}