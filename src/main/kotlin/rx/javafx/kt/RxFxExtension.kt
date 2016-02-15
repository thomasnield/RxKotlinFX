package rx.javafx.kt

import javafx.beans.value.ObservableValue
import rx.Observable
import rx.observables.JavaFxObservable
import rx.subscribers.JavaFxSubscriber


fun <T> Observable<T>.toBinding() = JavaFxSubscriber.toBinding(this)
fun <T> Observable<T>.toBinding(errorHandler: (Throwable) -> Unit) = JavaFxSubscriber.toBinding(this,errorHandler)
fun <T> ObservableValue<T>.toObservable() = JavaFxObservable.fromObservableValue(this)
fun <T> ObservableValue<T>.toObservableChanges() = JavaFxObservable.fromObservableValueChanges(this)
