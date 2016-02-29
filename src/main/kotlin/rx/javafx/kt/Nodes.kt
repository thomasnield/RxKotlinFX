package rx.javafx.kt

import javafx.event.EventType
import javafx.scene.control.TableView
import javafx.scene.input.MouseEvent
import rx.observables.JavaFxObservable

//TableView Extensions
fun <S> TableView<S>.mouseEvents(mouseEventType: EventType<MouseEvent>) =
        JavaFxObservable.fromNodeEvents(this,mouseEventType)

fun <S> TableView<S>.mouseClicks() =
        JavaFxObservable.fromNodeEvents(this,MouseEvent.MOUSE_CLICKED)
