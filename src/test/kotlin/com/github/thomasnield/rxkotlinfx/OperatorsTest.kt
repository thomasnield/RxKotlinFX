package com.github.thomasnield.rxkotlinfx

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import javafx.application.Platform
import javafx.embed.swing.JFXPanel
import org.junit.Assert.assertTrue
import java.util.*
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger

class OperatorsTest {

    init {
        JFXPanel()
    }
    @org.junit.Test
    fun doOnNextFxTest() {
        val latch = CountDownLatch(1)

        Observable.just("Alpha").observeOn(Schedulers.io()).doOnNextFx {
            assertTrue(Platform.isFxApplicationThread())
            latch.countDown()
        }.subscribe()

        latch.await(10, TimeUnit.SECONDS)
    }
    @org.junit.Test
    fun doOnCompletedFxTest() {
        val latch = CountDownLatch(1)

        Observable.just("Alpha").observeOn(Schedulers.io()).doOnCompleteFx() {
            assertTrue(Platform.isFxApplicationThread())
            latch.countDown()
        }.subscribe()

        latch.await(10, TimeUnit.SECONDS)
    }

    @org.junit.Test
    fun doOnSubscribeFx() {
        val latch = CountDownLatch(1)

        Observable.just("Alpha").observeOn(Schedulers.io()).doOnSubscribeFx {
            assertTrue(Platform.isFxApplicationThread())
            latch.countDown()
        }.subscribe()

        latch.await(10, TimeUnit.SECONDS)
    }

    @org.junit.Test
    fun doOnTerminateFx() {
        val latch = CountDownLatch(1)

        Observable.just("Alpha").observeOn(Schedulers.io()).doOnTerminateFx {
            assertTrue(Platform.isFxApplicationThread())
            latch.countDown()
        }.subscribe()

        latch.await(10, TimeUnit.SECONDS)
    }

    @org.junit.Test
    fun doOnUnsubscribeFxTest() {
        val latch = CountDownLatch(1)

        val subscription = Observable.interval(1, TimeUnit.SECONDS).doOnDisposeFx {
            assertTrue(Platform.isFxApplicationThread())
            latch.countDown()
        }.subscribe()

        Thread.sleep(3000)
        subscription.dispose()

        latch.await(10, TimeUnit.SECONDS)
    }
    // TODO: Fix this broken test.
    /*@org.junit.Test
    fun doOnErrorFxTest() {
        val latch = CountDownLatch(1)

        Observable.just(5).map { it / 0 }.observeOn(Schedulers.io()).doOnErrorFx {
            assertTrue(Platform.isFxApplicationThread())
            latch.countDown()
        }.onErrorResumeNext(Observable.empty()).subscribe()

        latch.await()
    }*/
    @org.junit.Test
    fun doOnNextCountTest() {
        val items: MutableList<Int> = ArrayList()

        Observable.just("Alpha","Beta","Gamma")
            .doOnNextCount { items.add(it) }
            .subscribe()

        assertTrue(items.containsAll(listOf(1,2,3)))
    }
    @org.junit.Test
    fun doOnCompletedCountTest() {

        var value: Int? = null
        Observable.just("Alpha","Beta","Gamma")
                .doOnCompleteCount { value = it }
                .subscribe()

        assertTrue(value != null && value == 3)
    }
    @org.junit.Test
    fun doOnErrorCountTest() {

        var value: Int? = null
        Observable.just(1,2,0,3)
                .map { 10 / it }
                .doOnErrorCount { value = it }
                .subscribe()

        assertTrue(value != null && value == 2)
    }
    @org.junit.Test
    fun doOnNextCountFxTest() {
        val latch = CountDownLatch(3)
        var sum: Int = 0
        Observable.just(1,2,3)
            .doOnNextCountFx {
                assertTrue(Platform.isFxApplicationThread())
                sum += it
                latch.countDown()
            }.subscribe()

        latch.await(10, TimeUnit.SECONDS)
        assert(sum == 6)
    }
    @org.junit.Test
    fun doOnCompletedCountFxTest() {
        val latch = CountDownLatch(1)
        var value: Int? = null
        Observable.just("Alpha","Beta","Gamma")
                .doOnCompleteCountFx {
                    assertTrue(Platform.isFxApplicationThread())
                    value = it
                    latch.countDown()
                }.subscribe()

        latch.await(10, TimeUnit.SECONDS)
        assertTrue(value == 3)
    }
    @org.junit.Test
    fun doOnErrorCountFxTest() {
        val latch = CountDownLatch(1)
        var value: Int? = null
        Observable.just(1,3,0,5)
                .map { 10 / it }
                .doOnErrorCountFx {
                    assertTrue(Platform.isFxApplicationThread())
                    value = it
                    latch.countDown()
                }.subscribe()

        latch.await(10, TimeUnit.SECONDS)
        assertTrue(value == 2)
    }

    @org.junit.Test
    fun bindingSideEffectsTest() {
        val counter = AtomicInteger(0)
        Observable.just(1,2,3)
                .toBinding {
                    onNext { counter.incrementAndGet() }
                    onComplete { counter.incrementAndGet() }
                }

        assertTrue(counter.get() == 4)
    }

    @org.junit.Test
    fun bindingSideEffectsErrorTest() {
        val counter = AtomicInteger(0)
        Observable.error<Unit>(Exception("Test"))
                .toBinding {
                    onError { counter.incrementAndGet() }
                }

       assertTrue(counter.get() == 1)
    }
}