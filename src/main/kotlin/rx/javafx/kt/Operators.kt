package rx.javafx.kt

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


