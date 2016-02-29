package rx.javafx.kt

import javafx.beans.value.ObservableValue
import javafx.event.Event
import javafx.event.EventType
import javafx.scene.Node
import rx.Observable
import rx.observables.JavaFxObservable
import rx.schedulers.JavaFxScheduler
import rx.subscribers.JavaFxSubscriber


fun <T> Observable<T>.toBinding() = JavaFxSubscriber.toBinding(this)
fun <T> Observable<T>.toBinding(errorHandler: (Throwable) -> Unit) = JavaFxSubscriber.toBinding(this,errorHandler)
fun <T> ObservableValue<T>.toObservable() = JavaFxObservable.fromObservableValue(this)
fun <T> ObservableValue<T>.toObservableChanges() = JavaFxObservable.fromObservableValueChanges(this)
fun <T> Observable<T>.toFxScheduler() = observeOn(JavaFxScheduler.getInstance())
fun <T : Event> Node.toNodeEvents(eventType: EventType<T>) = JavaFxObservable.fromNodeEvents(this, eventType)


