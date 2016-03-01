package rx.javafx.kt

import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.event.EventType
import javafx.scene.Node
import javafx.scene.input.MouseEvent
import javafx.stage.Window
import javafx.stage.WindowEvent
import rx.Observable
import rx.observables.JavaFxObservable
import rx.schedulers.JavaFxScheduler
import rx.subscriptions.JavaFxSubscriptions

fun <N: Node> N.mouseEvents(mouseEventType: EventType<MouseEvent>) =
        JavaFxObservable.fromNodeEvents(this,mouseEventType)

fun <N: Node> N.mouseClicks() =
        JavaFxObservable.fromNodeEvents(this,MouseEvent.MOUSE_CLICKED)

fun <N: Node> N.actionEvents() = JavaFxObservable.fromNodeEvents(this, ActionEvent.ACTION)

fun <T : Window> T.windowEvents(windowEvent: EventType<WindowEvent>): Observable<WindowEvent> {
    val window = this
    return Observable.create(Observable.OnSubscribe<WindowEvent> { subscriber ->
        val handler = EventHandler<WindowEvent> { subscriber.onNext(it) }

        window.addEventHandler(windowEvent, handler)

        subscriber.add(JavaFxSubscriptions.unsubscribeInEventDispatchThread { window.removeEventHandler(windowEvent, handler) })

    }).subscribeOn(JavaFxScheduler.getInstance())
}