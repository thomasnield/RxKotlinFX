# RxKotlinFX
Kotlin extensions to the [RxJavaFX](https://github.com/ReactiveX/RxJavaFX) library.

Right now it only contains eight extension functions calling on [RxJavaFX](https://github.com/ReactiveX/RxJavaFX) factories. Kotlin, being an effective language, makes this library a little awkward to release because it has so few lines of code. Arguably anybody can implement these extension functions on an as-needed basis.

If anyone has worthwhile ideas to better integrate RxJava, JavaFX, and Kotlin beyond just extension functions, feel free to contribute and maybe a release will be worthwhile. 

```
fun <T> Observable<T>.toBinding() = JavaFxSubscriber.toBinding(this)
fun <T> Observable<T>.toBinding(errorHandler: (Throwable) -> Unit) = JavaFxSubscriber.toBinding(this,errorHandler)
fun <T> ObservableValue<T>.toObservable() = JavaFxObservable.fromObservableValue(this)
fun <T> ObservableValue<T>.toObservableChanges() = JavaFxObservable.fromObservableValueChanges(this)
fun <T> Observable<T>.toFxScheduler() = observeOn(JavaFxScheduler.getInstance())
fun <T : Event> Node.toNodeEvents(eventType: EventType<T>) = JavaFxObservable.fromNodeEvents(this, eventType)
fun <T> Observable<T>.observeOnFx() = this.observeOn(JavaFxScheduler.getInstance())
```
