# RxKotlinFX
Kotlin extensions to the [RxJavaFX](https://github.com/ReactiveX/RxJavaFX) library.

RxKotlinFX contains Kotlin extensions to [RxJavaFX](https://github.com/ReactiveX/RxJavaFX). It also is in exporatory stages to add helpful `Node` extension functions that return Observables. This exploration is inspired by the JavaFX/Kotlin interop project [TornadoFX](https://github.com/edvin/tornadofx). Where TornadoFX handles layouts, node extensions, DI, and other JavaFX/Kotlin interoperations, this library will seek to integrate RxJava with JavaFX in the same spirit using Kotlin. The vision is to add useful extensions that put `Observable` streams as properties and functions on JavaFX components, especially where `ObservableValue` properties are not readily available. 

The core API implements [RxJavaFX](https://github.com/ReactiveX/RxJavaFX) static factories as extension functions. See the full list of extension functions [here](https://github.com/thomasnield/RxKotlinFX/blob/master/src/main/kotlin/rx/javafx/kt/Observables.kt). 

#####Observable of Button ActionEvents
```kotlin
val myButton = Button("Press Me")
val buttonEvents = myButton.actionEvents()
val subscription = buttonEvents.subscribe { println("Pressed!") } 

val items = FXCollections.obs
```
#####Observable of ObservableList Adds
```kotlin
The rest of the project will likely add convenient extension functions to emit events as `Observable` values. After I finish some developments in [RxJavaFX](https://github.com/ReactiveX/RxJavaFX), I will be extending to this library next. 
