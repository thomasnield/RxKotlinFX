package rx.javafx.kt

import internal.rx.javafx.kt.operators.CountObserver
import internal.rx.javafx.kt.operators.OperatorEmissionCounter
import javafx.application.Platform
import rx.Observable
import rx.schedulers.JavaFxScheduler

/**
 * Observes the emissions on the JavaFX Thread.
 * This is the same as calling Observable#observeOn(JavaFxScheduler.getInstance())
 */
fun <T> Observable<T>.observeOnFx() = observeOn(JavaFxScheduler.getInstance())
/**
 * Instructs the source Observable to emit items on the JavaFX Thread.
 * This is the same as calling Observable#subscribeOn(JavaFxScheduler.getInstance())
 */
fun <T> Observable<T>.subscribeOnFx() = subscribeOn(JavaFxScheduler.getInstance())

/**
 * Performs the provided onNext action on the FX thread
 */
inline fun <T> Observable<T>.doOnNextFx(crossinline onNext: (T) -> Unit) = doOnNext {
    Platform.runLater { onNext.invoke(it) }
}
/**
 * Performs the provided onError action on the FX thread
 */
inline fun <T> Observable<T>.doOnErrorFx(crossinline onError: (Throwable) -> Unit) = doOnError {
    Platform.runLater { onError.invoke(it) }
}
/**
 * Performs the provided onCompleted action on the FX thread
 */
inline fun <T> Observable<T>.doOnCompletedFx(crossinline onCompleted: () -> Unit) = doOnCompleted {
    Platform.runLater { onCompleted.invoke() }
}
/**
 * Performs the provided onSubscribe action on the FX thread
 */
inline fun <T> Observable<T>.doOnSubscribeFx(crossinline onSubscribe: () -> Unit) = doOnSubscribe {
    Platform.runLater { onSubscribe.invoke() }
}
/**
 * Performs the provided onTerminate action on the FX thread
 */
inline fun <T> Observable<T>.doOnTerminateFx(crossinline onTerminate: () -> Unit) = doOnTerminate {
    Platform.runLater { onTerminate.invoke() }
}
/**
 * Performs the provided onUnsubscribe action on the FX thread
 */
inline fun <T> Observable<T>.doOnUnsubscribeFx(crossinline onUnsubscribe: () -> Unit) = doOnUnsubscribe {
    Platform.runLater { onUnsubscribe.invoke() }
}
/**
 * Executes side effect with the accumulating count of emissions for each onNext() call
 */
fun <T> Observable<T>.doOnNextCount(onNext: (Int) -> Unit) = lift<T>(
        OperatorEmissionCounter(CountObserver(doOnNextCountAction = onNext))
)
/**
 * Executes side effect with the total count of emissions for the onCompleted() call
 */
fun <T> Observable<T>.doOnCompletedCount(onCompleted: (Int) -> Unit) = lift<T>(
        OperatorEmissionCounter(CountObserver(doOnCompletedCountAction = onCompleted))
)
/**
 * Executes side effect with the total count of emissions for an onError() call
 */
fun <T> Observable<T>.doOnErrorCount(onError: (Int) -> Unit) = lift<T>(
        OperatorEmissionCounter(CountObserver(doOnErrorCountAction = onError))
)
/**
 * Executes side effect on FX thread with the accumulating count of emissions for each onNext() call
 */
fun <T> Observable<T>.doOnNextCountFx(onNext: (Int) -> Unit) = doOnNextCount { Platform.runLater { onNext.invoke(it) } }
/**
 * Executes side effect on FX thread with the total count of emissions for the onCompleted() call
 */
fun <T> Observable<T>.doOnCompletedCountFx(onCompleted: (Int) -> Unit) = doOnCompletedCount { Platform.runLater { onCompleted.invoke(it) } }
/**
 * Executes side effect on FX thread with the total count of emissions for the onError() call
 */
fun <T> Observable<T>.doOnErrorCountFx(onError: (Int) -> Unit) = doOnErrorCount { Platform.runLater { onError.invoke(it) } }