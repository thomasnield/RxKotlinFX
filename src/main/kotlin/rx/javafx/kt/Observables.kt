package rx.javafx.kt

import javafx.beans.value.ObservableValue
import javafx.collections.ObservableList
import javafx.event.Event
import javafx.event.EventType
import javafx.scene.Node
import javafx.scene.Scene
import javafx.scene.control.ContextMenu
import javafx.scene.control.MenuItem
import javafx.stage.Window
import javafx.stage.WindowEvent
import rx.Observable
import rx.observables.JavaFxObservable
import rx.schedulers.JavaFxScheduler
import rx.subscribers.JavaFxSubscriber

fun <T> Observable<T>.observeOnFx() = observeOn(JavaFxScheduler.getInstance())
fun <T> Observable<T>.subscribeOnFx() = subscribeOn(JavaFxScheduler.getInstance())

fun <T> Observable<T>.toBinding() = JavaFxSubscriber.toBinding(this)
fun <T> Observable<T>.toBinding(errorHandler: (Throwable) -> Unit) = JavaFxSubscriber.toBinding(this,errorHandler)
fun <T> ObservableValue<T>.toObservable() = JavaFxObservable.fromObservableValue(this)
fun <T> ObservableValue<T>.toObservableChanges() = JavaFxObservable.fromObservableValueChanges(this)

fun ContextMenu.actionEvents() = JavaFxObservable.fromActionEvents(this)
fun MenuItem.actionEvents() = JavaFxObservable.fromActionEvents(this)
fun Node.actionEvents() = JavaFxObservable.fromActionEvents(this)

fun <T : Event> Node.eventsOf(eventType: EventType<T>) = JavaFxObservable.fromNodeEvents(this, eventType)
fun <T: Event> Scene.eventsOf(eventType: EventType<T>) = JavaFxObservable.fromSceneEvents(this,eventType)
fun <T: WindowEvent> Window.eventsOf(eventType: EventType<T>) = JavaFxObservable.fromWindowEvents(this,eventType)

fun <T> ObservableList<T>.toObservable() = JavaFxObservable.fromObservableList(this)

fun <T> ObservableList<T>.removals() = JavaFxObservable.fromObservableListRemovals(this)
fun <T> ObservableList<T>.additions() = JavaFxObservable.fromObservableListAdds(this)
fun <T> ObservableList<T>.updates() = JavaFxObservable.fromObservableListUpdates(this)
fun <T> ObservableList<T>.changes() =JavaFxObservable.fromObservableListChanges(this)
fun <T> ObservableList<T>.distinctChanges() = JavaFxObservable.fromObservableListDistinctChanges(this)
fun <T,R> ObservableList<T>.distinctchanges(mapper: ((T) -> R)) = JavaFxObservable.fromObservableListDistinctChanges(this,mapper)

