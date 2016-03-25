# RxKotlinFX
Kotlin extensions to the [RxJavaFX](https://github.com/ReactiveX/RxJavaFX) library.

## Binaries

Binaries and dependency information for Maven, Ivy, Gradle and others can be found at [http://search.maven.org](http://search.maven.org/#search%7Cga%7C1%7Cio.reactivex.rxjavafx).

Example for Maven:

```xml
<dependency>
    <groupId>com.github.thomasnield</groupId>
    <artifactId>rxkotlinfx</artifactId>
    <version>x.y.z</version>
</dependency>
```

Gradle: 

```groovy 
repositories {
    mavenCentral()
}
dependencies {
    compile 'com.github.thomasnield:rxkotlinfx:x.y.z'
}
```
##Contributing
Feel free to contribute and help streamline a pragmatic UI stack with Kotlin, RxJava, and JavaFX. Speaking of stacks, this project may be used in conjunction with [TornadoFX](https://github.com/edvin/tornadofx) and [RxKotlin](https://github.com/ReactiveX/RxKotlin). Please make sure no extension naming conventions conflict with these two other libraries :)

##Features

RxKotlinFX contains Kotlin extensions to [RxJavaFX](https://github.com/ReactiveX/RxJavaFX) as well as additional `Observable` operators specific to JavaFX. It also is in exporatory stages to add helpful `Node` extension functions that return Observables. This exploration is inspired by the JavaFX/Kotlin interop project [TornadoFX](https://github.com/edvin/tornadofx). Where TornadoFX handles layouts, node extensions, DI, and other JavaFX/Kotlin interoperations, this library will seek to integrate RxJava with JavaFX in the same spirit using Kotlin. The vision is to add useful extensions that put `Observable` streams as properties and functions on JavaFX components, especially where `ObservableValue` properties are not readily available. 

###RxJavaFX Extensions
The core API implements [RxJavaFX](https://github.com/ReactiveX/RxJavaFX) static factories as extension functions. See the full list of extension functions [here](https://github.com/thomasnield/RxKotlinFX/blob/master/src/main/kotlin/rx/javafx/kt/Observables.kt). 

#####Observable of Button ActionEvents
```kotlin
val myButton = Button("Press Me")
val subscription = myButton.actionEvents().subscribe { println("Pressed!") } 
```
#####Creating a Reactive Binding
```kotlin
val myButton = Button("Press Me")

val countBinding = myButtonActionEvents().map { 1 }
    .scan(0, { x,y -> x + y })
    .map { it.toString() }
    .toBinding()
    
val myLabel = Label()
myLabel.textProperty().bind(countBinding)
```

#####Observable of ObservableList Adds
```kotlin
val items = FXCollections.observableArrayList("Alpha", "Beta", "Gamma")

val changes = items.changes()

changes.filter { it.flag == Flag.ADDED }
        .map { it.value }
        .subscribe { println("ADDED $it") }

items.add("Delta")
items.add("Epsilon")
```
######OUTPUT
```
ADDED Delta
ADDED Epsilon
```

###Operators
RxKotlinFX has a growing list of operators placed as extension functions onto `Observable` that aid interoperability with JavaFX.

|Operator|Description|
|----|-----|
|observeOnFx()|Schedules the emissions to be observed on the JavaFX thread
|subscribeOnFx()|Schedules the source `Observable` to emit items on the JavaFX thread
|doOnNextFx()|Executes the specified action on the FX thread for each emission
|doOnErrorFx()|Executes the specified action on the FX thread when an error is emitted
|doOnCompletedFx()|Executes the specified action on the FX thread when the `Observable` calls `onCompleted()`
|doOnSubscribeFx()|Executes the specified action on the FX thread when the `Observable` is first subscribed
|doOnTerminateFx()|Executes the specified action on the FX thread when the `Observable` calls `onCompleted()` or `onError()`
|doOnUnsubscribeFx()|Executes the specified action on the FX thread when the `Observable` is unsubscribed
|doOnNextCount()|Executes the specified action with the cumulative count of emissions for that emission
|doOnErrorCount()|Executes the specified action with the cumulative count of emissions when an error is emitted
|doOnCompletedCount()|Executes the specified action with the total emission count when `onCompleted()` is called
|doOnNextCountFx()|Same as `doOnNextCount()` except action is executed on FX thread
|doOnErrorCountFx()|Same as `doOnErrorCount()` except action is executed on FX thread
|doOnCompletedCountFx()|Same as `doOnCompletedCount()` except action is executed on FX thread

The `doOnXXXCount()` operators are especially helpful for providing a status update of how many items have been "processed" by an `Observable`. 

```kotlin
val source = Observable.range(1,1000)
val processedCountLabel = Label()

source.map { it * 10 }
     .doOnNextFx { processedCountLabel.text = "Processed $it items" }
     .subsribe { doSomethingWith(it) }
```

###Control Extensions
The rest of the project will likely add convenient extension functions to emit events as `Observable` values, [much like the TornadoFX project has done](https://github.com/edvin/tornadofx/blob/master/src/main/java/tornadofx/Nodes.kt). For example, helpful `Observable` extension functions and properties can be added to `TableView` and `ListView`, such as selection events.

```kotlin
val tableView: TableView<MyItem> = ...
val selections: Observable<MyItem> = tableView.itemSelections
val rowIndexSelections: Observable<Int> = tableView.rowIndexSelections
```

Check releases as well the [Nodes code file](https://github.com/thomasnield/RxKotlinFX/blob/master/src/main/kotlin/rx/javafx/kt/Nodes.kt) to see a list of available extensions. Feel free to contribute if you see any missing.

