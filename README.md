# RxKotlinFX
Kotlin extensions to the [RxJavaFX](https://github.com/ReactiveX/RxJavaFX) library.

This project is in exploration stages and inspired by the JavaFX/Kotlin interop project [TornadoFX](https://github.com/edvin/tornadofx). Where TornadoFX handles layouts, node extensions, DI, and other JavaFX/Kotlin interoperations, this library will seek to integrate RxJava with JavaFX in the same spirit using Kotlin. The vision is to add useful extensions that put `Observable` streams as properties and functions on JavaFX components, especially where `ObservableValue` properties are not readily available. 

The core API implements [RxJavaFX](https://github.com/ReactiveX/RxJavaFX) static factories as extension functions. 

```kotlin
fun <T> Observable<T>.toBinding() = JavaFxSubscriber.toBinding(this)
fun <T> Observable<T>.toBinding(errorHandler: (Throwable) -> Unit) = JavaFxSubscriber.toBinding(this,errorHandler)
fun <T> ObservableValue<T>.toObservable() = JavaFxObservable.fromObservableValue(this)
fun <T> ObservableValue<T>.toObservableChanges() = JavaFxObservable.fromObservableValueChanges(this)
fun <T> Observable<T>.toFxScheduler() = observeOn(JavaFxScheduler.getInstance())
fun <T : Event> Node.toNodeEvents(eventType: EventType<T>) = JavaFxObservable.fromNodeEvents(this, eventType)
fun <T> Observable<T>.observeOnFx() = this.observeOn(JavaFxScheduler.getInstance())
```

The rest of the project will likely add convenient extension functions to emit events as `Observable` values. 

```kotlin 
fun <N: Node> N.mouseEvents(mouseEventType: EventType<MouseEvent>) =
        JavaFxObservable.fromNodeEvents(this,mouseEventType)

fun <N: Node> N.mouseClicks() =
        JavaFxObservable.fromNodeEvents(this,MouseEvent.MOUSE_CLICKED)

```
