package rx.javafx.kt

import javafx.beans.value.ObservableValue
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


fun <T : Event> Node.toNodeEvents(eventType: EventType<T>) = JavaFxObservable.fromNodeEvents(this, eventType)

fun ContextMenu.actionEvents() = JavaFxObservable.fromActionEvents(this)
fun MenuItem.actionEvents() = JavaFxObservable.fromActionEvents(this)
fun Node.actionEvents() = JavaFxObservable.fromActionEvents(this)

fun <T: Event> Scene.eventsToObservable(eventType: EventType<T>) = JavaFxObservable.fromSceneEvents(this,eventType)
fun <T: WindowEvent> Window.eventsToObservable(eventType: EventType<T>) = JavaFxObservable.fromWindowEvents(this,eventType)
