# RxKotlinFX
Kotlin extensions to the [RxJavaFX](https://github.com/ReactiveX/RxJavaFX) library.

RxKotlinFX contains Kotlin extensions to [RxJavaFX](https://github.com/ReactiveX/RxJavaFX). It also is in exporatory stages to add helpful `Node` extension functions that return Observables. This exploration is inspired by the JavaFX/Kotlin interop project [TornadoFX](https://github.com/edvin/tornadofx). Where TornadoFX handles layouts, node extensions, DI, and other JavaFX/Kotlin interoperations, this library will seek to integrate RxJava with JavaFX in the same spirit using Kotlin. The vision is to add useful extensions that put `Observable` streams as properties and functions on JavaFX components, especially where `ObservableValue` properties are not readily available. 

##RxJavaFX Extensions
The core API implements [RxJavaFX](https://github.com/ReactiveX/RxJavaFX) static factories as extension functions. See the full list of extension functions [here](https://github.com/thomasnield/RxKotlinFX/blob/master/src/main/kotlin/rx/javafx/kt/Observables.kt). 

#####Observable of Button ActionEvents
```kotlin
val myButton = Button("Press Me")
val buttonEvents = myButton.actionEvents()
val subscription = buttonEvents.subscribe { println("Pressed!") } 
```
#####Observable of ObservableList Adds
```kotlin
val items = FXCollections.observableArrayList("Alpha", "Beta", "Gamma")

val changes = items.changes() //returns ObservableList<ListChange<T>>

changes.filter { it.flag == Flag.ADDED }
        .map { it.value }
        .subscribe { println("ADDED $it") }

items.add("Delta")
items.add("Epsilon")

/*will print:
ADDED Delta
ADDED Epsilon
*/

```

##Further Extensions
The rest of the project will likely add convenient extension functions to emit events as `Observable` values, much like the [TornadoFX](https://github.com/edvin/tornadofx) project has done. For example, helpful `Observable` extension functions can be added to `TableView` and `ListView`.

```kotlin

/**
 * Returns an Observable emitting integer values for selected row indexes.
 */
val <T> TableView<T>.rowIndexSelections: Observable<Int>
    get() = itemSelections.map { selectionModel.selectedIndex }

/**
 * Returns an Observable emitting integer values for selected column indexes.
 */
val <T> TableView<T>.columnIndexSelections: Observable<Int>
    get() = selectionModel.selectedCells.toObservable().flatMap { Observable.from(it).map { it.column } }

/**
 * Returns an Observable emitting selected items for the given TableView
 */
val <T> TableView<T>.itemSelections: Observable<T>
    get() = selectionModel.selectedItemProperty().toObservable()

/**
 * Returns an Observable emitting selected items for the given ListView
 */
val <T> ListView<T>.itemSelections: Observable<T>
    get() = selectionModel.selectedItems.toObservable().flatMap { Observable.from(it) }
```

