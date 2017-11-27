package com.github.thomasnield.rxkotlinfx

import io.reactivex.*
import io.reactivex.rxjavafx.schedulers.JavaFxScheduler
import io.reactivex.rxjavafx.transformers.FxFlowableTransformers
import io.reactivex.rxjavafx.transformers.FxObservableTransformers
import javafx.application.Platform

/**
 * Observes the emissions on the JavaFX Thread.
 * This is the same as calling Observable#observeOn(JavaFxScheduler.platform())
 */
fun <T> Observable<T>.observeOnFx() = observeOn(JavaFxScheduler.platform())

/**
 * Observes the emissions on the JavaFX Thread.
 * This is the same as calling Flowable#observeOn(JavaFxScheduler.platform())
 */
fun <T> Flowable<T>.observeOnFx() = observeOn(JavaFxScheduler.platform())


/**
 * Observes the emissions on the JavaFX Thread.
 * This is the same as calling Single#observeOn(JavaFxScheduler.platform())
 */
fun <T> Single<T>.observeOnFx() = observeOn(JavaFxScheduler.platform())

/**
 * Observes the emissions on the JavaFX Thread.
 * This is the same as calling Maybe#observeOn(JavaFxScheduler.platform())
 */
fun <T> Maybe<T>.observeOnFx() = observeOn(JavaFxScheduler.platform())

/**
 * Observes the emissions on the JavaFX Thread.
 * This is the same as calling Completable#observeOn(JavaFxScheduler.platform())
 */
fun Completable.observeOnFx() = observeOn(JavaFxScheduler.platform())


/**
 * Instructs the source Observable to emit items on the JavaFX Thread.
 * This is the same as calling Observable#subscribeOn(JavaFxScheduler.platform())
 */
fun <T> Observable<T>.subscribeOnFx() = subscribeOn(JavaFxScheduler.platform())

/**
 * Instructs the source Flowable to emit items on the JavaFX Thread.
 * This is the same as calling Flowable#subscribeOn(JavaFxScheduler.platform())
 */
fun <T> Flowable<T>.subscribeOnFx() = subscribeOn(JavaFxScheduler.platform())

/**
 * Observes the emissions on the JavaFX Thread.
 * This is the same as calling Single#subscribeOnFx(JavaFxScheduler.platform())
 */
fun <T> Single<T>.subscribeOnFx() = subscribeOn(JavaFxScheduler.platform())

/**
 * Observes the emissions on the JavaFX Thread.
 * This is the same as calling Maybe#subscribeOnFx(JavaFxScheduler.platform())
 */
fun <T> Maybe<T>.subscribeOnFx() = subscribeOn(JavaFxScheduler.platform())

/**
 * Observes the emissions on the JavaFX Thread.
 * This is the same as calling Completable#subscribeOnFx(JavaFxScheduler.platform())
 */
fun Completable.subscribeOnFx() = subscribeOn(JavaFxScheduler.platform())

/**
 * Performs the provided onNext action on the FX thread
 */
inline fun <T> Observable<T>.doOnNextFx(crossinline onNext: (T) -> Unit): Observable<T> = doOnNext {
    Platform.runLater { onNext.invoke(it) }
}

/**
 * Performs the provided onNext action on the FX thread
 */
inline fun <T> Flowable<T>.doOnNextFx(crossinline onNext: (T) -> Unit): Flowable<T> = doOnNext {
    Platform.runLater { onNext.invoke(it) }
}

/**
 * Performs the provided onError action on the FX thread
 */
inline fun <T> Observable<T>.doOnErrorFx(crossinline onError: (Throwable) -> Unit): Observable<T> = doOnError {
    Platform.runLater { onError.invoke(it) }
}

/**
 * Performs the provided onError action on the FX thread
 */
inline fun <T> Flowable<T>.doOnErrorFx(crossinline onError: (Throwable) -> Unit): Flowable<T> = doOnError {
    Platform.runLater { onError.invoke(it) }
}

/**
 * Performs the provided onComplete action on the FX thread
 */
inline fun <T> Observable<T>.doOnCompleteFx(crossinline onComplete: () -> Unit): Observable<T> = doOnComplete {
    Platform.runLater { onComplete.invoke() }
}

/**
 * Performs the provided onComplete action on the FX thread
 */
inline fun <T> Flowable<T>.doOnCompleteFx(crossinline onComplete: () -> Unit): Flowable<T> = doOnComplete {
    Platform.runLater { onComplete.invoke() }
}

/**
 * Performs the provided onSubscribe action on the FX thread
 */
inline fun <T> Observable<T>.doOnSubscribeFx(crossinline onSubscribe: () -> Unit): Observable<T> = doOnSubscribe {
    Platform.runLater { onSubscribe.invoke() }
}


/**
 * Performs the provided onSubscribe action on the FX thread
 */
inline fun <T> Flowable<T>.doOnSubscribeFx(crossinline onSubscribe: () -> Unit): Flowable<T> = doOnSubscribe {
    Platform.runLater { onSubscribe.invoke() }
}

/**
 * Performs the provided onTerminate action on the FX thread
 */
inline fun <T> Observable<T>.doOnTerminateFx(crossinline onTerminate: () -> Unit): Observable<T> = doOnTerminate {
    Platform.runLater { onTerminate.invoke() }
}

/**
 * Performs the provided onTerminate action on the FX thread
 */
inline fun <T> Flowable<T>.doOnTerminateFx(crossinline onTerminate: () -> Unit): Flowable<T> = doOnTerminate {
    Platform.runLater { onTerminate.invoke() }
}

/**
 * Performs the provided onDispose action on the FX thread
 */
inline fun <T> Observable<T>.doOnDisposeFx(crossinline onDispose: () -> Unit): Observable<T> = this.doOnDispose {
    Platform.runLater { onDispose.invoke() }
}

/**
 * Performs the provided onDispose action on the FX thread
 */
inline fun <T> Flowable<T>.doOnCancelFx(crossinline onDispose: () -> Unit): Flowable<T> = this.doOnCancel {
    Platform.runLater { onDispose.invoke() }
}

/**
 * Executes side effect with the accumulating count of emissions for each onNext() call
 */
fun <T> Observable<T>.doOnNextCount(onNext: (Int) -> Unit): Observable<in T> =
        compose(FxObservableTransformers.doOnNextCount(onNext))


/**
 * Executes side effect with the accumulating count of emissions for each onNext() call
 */
fun <T> Flowable<T>.doOnNextCount(onNext: (Int) -> Unit): Flowable<in T> =
        compose(FxFlowableTransformers.doOnNextCount(onNext))

/**
 * Executes side effect with the total count of emissions for the onComplete() call
 */
fun <T> Observable<T>.doOnCompleteCount(onComplete: (Int) -> Unit): Observable<in T> =
        compose(FxObservableTransformers.doOnCompleteCount(onComplete))

/**
 * Executes side effect with the total count of emissions for the onComplete() call
 */
fun <T> Flowable<T>.doOnCompleteCount(onComplete: (Int) -> Unit): Flowable<in T> =
        compose(FxFlowableTransformers.doOnCompleteCount(onComplete))

/**
 * Executes side effect with the total count of emissions for an onError() call
 */
fun <T> Observable<T>.doOnErrorCount(onError: (Int) -> Unit): Observable<in T> =
        compose(FxObservableTransformers.doOnErrorCount(onError))

/**
 * Executes side effect with the total count of emissions for an onError() call
 */
fun <T> Flowable<T>.doOnErrorCount(onError: (Int) -> Unit): Flowable<in T> =
        compose(FxFlowableTransformers.doOnErrorCount(onError))

/**
 * Executes side effect on FX thread with the accumulating count of emissions for each onNext() call
 */
fun <T> Observable<T>.doOnNextCountFx(onNext: (Int) -> Unit) = doOnNextCount { Platform.runLater { onNext.invoke(it) } }

/**
 * Executes side effect on FX thread with the accumulating count of emissions for each onNext() call
 */
fun <T> Flowable<T>.doOnNextCountFx(onNext: (Int) -> Unit) = doOnNextCount { Platform.runLater { onNext.invoke(it) } }

/**
 * Executes side effect on FX thread with the total count of emissions for the onComplete() call
 */
fun <T> Observable<T>.doOnCompleteCountFx(onComplete: (Int) -> Unit) = doOnCompleteCount { Platform.runLater { onComplete.invoke(it) } }

/**
 * Executes side effect on FX thread with the total count of emissions for the onComplete() call
 */
fun <T> Flowable<T>.doOnCompleteCountFx(onComplete: (Int) -> Unit) = doOnCompleteCount { Platform.runLater { onComplete.invoke(it) } }

/**
 * Executes side effect on FX thread with the total count of emissions for the onError() call
 */
fun <T> Observable<T>.doOnErrorCountFx(onError: (Int) -> Unit) = doOnErrorCount { Platform.runLater { onError.invoke(it) } }


/**
 * Executes side effect on FX thread with the total count of emissions for the onError() call
 */
fun <T> Flowable<T>.doOnErrorCountFx(onError: (Int) -> Unit) = doOnErrorCount { Platform.runLater { onError.invoke(it) } }