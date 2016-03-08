package rx.javafx.kt

import javafx.event.EventType
import javafx.scene.Node
import javafx.scene.input.MouseEvent
import rx.observables.JavaFxObservable

fun <N: Node> N.mouseEvents(mouseEventType: EventType<MouseEvent>) =
        JavaFxObservable.fromNodeEvents(this,mouseEventType)

fun <N: Node> N.mouseClicks() =
        JavaFxObservable.fromNodeEvents(this,MouseEvent.MOUSE_CLICKED)
