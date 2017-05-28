package com.github.thomasnield.rxkotlinfx

import io.reactivex.*
import javafx.beans.binding.Binding
import javafx.beans.value.ObservableValue
import javafx.collections.ObservableList
import javafx.collections.ObservableMap
import javafx.collections.ObservableSet
import javafx.event.Event
import javafx.event.EventType
import javafx.scene.Node
import javafx.scene.Scene
import javafx.scene.control.ContextMenu
import javafx.scene.control.Dialog
import javafx.scene.control.MenuItem
import javafx.stage.Window
import javafx.stage.WindowEvent
import io.reactivex.rxjavafx.observables.JavaFxObservable
import io.reactivex.rxjavafx.observers.JavaFxObserver
import io.reactivex.rxjavafx.observers.JavaFxSubscriber
import io.reactivex.rxjavafx.sources.CompositeObservable
import io.reactivex.rxjavafx.sources.SetChange

/**
 * Turns an Observable into a JavaFX Binding. Calling the Binding's dispose() method will handle the disposal.
 */
fun <T> Observable<T>.toBinding(actionOp: (ObservableBindingSideEffects<T>.() -> Unit)? = null): Binding<T> {
    val transformer = actionOp?.let {
        val sideEffects = ObservableBindingSideEffects<T>()
        it.invoke(sideEffects)
        sideEffects.transformer
    }
    return JavaFxObserver.toBinding((transformer?.let { this.compose(it) }?:this))
}


/**
 * Turns an Flowable into a JavaFX Binding. Calling the Binding's dispose() method will handle the unsubscription.
 */
fun <T> Flowable<T>.toBinding(actionOp: (FlowableBindingSideEffects<T>.() -> Unit)? = null): Binding<T> {
    val transformer = actionOp?.let {
        val sideEffects = FlowableBindingSideEffects<T>()
        it.invoke(sideEffects)
        sideEffects.transformer
    }
    return JavaFxSubscriber.toBinding((transformer?.let { this.compose(it) }?:this))
}


/**
 * Turns an Observable into a lazy JavaFX Binding, by lazy meaning it will delay subscription until `getValue()` is requested. Calling the Binding's dispose() method will handle the unsubscription.
 */
fun <T> Observable<T>.toLazyBinding() = JavaFxObserver.toBinding(this)


/**
 * Turns a Flowable into a lazy JavaFX Binding, by lazy meaning it will delay subscription until `getValue()` is requested. Calling the Binding's dispose() method will handle the unsubscription.
 */
fun <T> Flowable<T>.toLazyBinding() = JavaFxSubscriber.toBinding(this)


/**
 * Turns a Single into a JavaFX Binding. Calling the Binding's dispose() method will handle the disposal.
 */
fun <T> Single<T>.toBinding(actionOp: (ObservableBindingSideEffects<T>.() -> Unit)? = null): Binding<T> {
    val transformer = actionOp?.let {
        val sideEffects = ObservableBindingSideEffects<T>()
        it.invoke(sideEffects)
        sideEffects.transformer
    }
    return JavaFxObserver.toBinding((transformer?.let { this.toObservable().compose(it) }?:this.toObservable()))
}

/**
 * Turns a Single into a lazy JavaFX Binding, by lazy meaning it will delay subscription until `getValue()` is requested. Calling the Binding's dispose() method will handle the unsubscription.
 */
fun <T> Single<T>.toLazyBinding() = JavaFxObserver.toBinding(this.toObservable())


/**
 * Turns a Maybe into a JavaFX Binding. Calling the Binding's dispose() method will handle the disposal.
 */
fun <T> Maybe<T>.toBinding(actionOp: (ObservableBindingSideEffects<T>.() -> Unit)? = null): Binding<T> {
    val transformer = actionOp?.let {
        val sideEffects = ObservableBindingSideEffects<T>()
        it.invoke(sideEffects)
        sideEffects.transformer
    }
    return JavaFxObserver.toBinding((transformer?.let { this.toObservable().compose(it) }?:this.toObservable()))
}

/**
 * Turns a Maybe into a lazy JavaFX Binding, by lazy meaning it will delay subscription until `getValue()` is requested. Calling the Binding's dispose() method will handle the unsubscription.
 */
fun <T> Maybe<T>.toLazyBinding() = JavaFxObserver.toBinding(this.toObservable())


/**
 * Turns an Observable into a lazy JavaFX Binding, by lazy meaning it will delay subscription until `getValue()` is requested. Calling the Binding's dispose() method will handle the unsubscription.
 */
fun <T> Observable<T>.toLazyBinding(errorHandler: (Throwable) -> Unit) = JavaFxObserver.toLazyBinding(this,errorHandler)

/**
 * Turns a Flowable into a lazy JavaFX Binding, by lazy meaning it will delay subscription until `getValue()` is requested. Calling the Binding's dispose() method will handle the unsubscription.
 */
fun <T> Flowable<T>.toLazyBinding(errorHandler: (Throwable) -> Unit) = JavaFxSubscriber.toLazyBinding(this,errorHandler)


/**
 * Create an rx Observable from a javafx ObservableValue
 * @param <T>          the type of the observed value
 * @return an Observable emitting values as the wrapped ObservableValue changes
 */
fun <T> ObservableValue<T>.toObservable() = JavaFxObservable.valuesOf(this)


/**
 * Create an rx Observable from a javafx ObservableValue, emitting nullable values as Java 8 `Optional` types
 * @param <T>          the type of the observed value
 * @return an Observable emitting `Optional<T>` values as the wrapped ObservableValue changes
 */
fun <T> ObservableValue<T>.toNullableObservable() = JavaFxObservable.nullableValuesOf(this)

/**
 * Create an rx Observable from a javafx Observable, emitting it when an invalidation occurs
 * @return an rx Observable emitting the JavaFX Observable every time it is invalidated
 */
fun javafx.beans.Observable.invalidations() = JavaFxObservable.invalidationsOf(this)

/**
 * Create an rx Observable from a javafx ObservableValue, and emits changes with old and new value pairs
 * @param <T>          the type of the observed value
 * @return an Observable emitting values as the wrapped ObservableValue changes
 */
fun <T> ObservableValue<T>.toObservableChanges() = JavaFxObservable.changesOf(this)

/**
 * Create an rx Observable from a javafx ObservableValue, and emits changes with old and new non-null value pairs
 * @param <T>          the type of the observed value
 * @return an Observable emitting non-null values as the wrapped ObservableValue changes
 */
fun <T> ObservableValue<T>.toObservableChangesNonNull() = JavaFxObservable.nonNullChangesOf(this)


/**
 * Creates an observable corresponding to javafx ContextMenu action events.
 * @return An Observable of UI ActionEvents
 */
fun ContextMenu.actionEvents() = JavaFxObservable.actionEventsOf(this)

/**
 * Creates an observable corresponding to javafx MenuItem action events.
 *
 * @param menuItem      The target of the ActionEvents
 * @return An Observable of UI ActionEvents
 */
fun MenuItem.actionEvents() = JavaFxObservable.actionEventsOf(this)

/**
 * Creates an observable corresponding to javafx Node action events.
 * @return An Observable of UI ActionEvents
 */
fun Node.actionEvents() = JavaFxObservable.actionEventsOf(this)

/**
 * Creates an observable corresponding to javafx Node events.
 * @param eventType The type of the observed UI events
 * @return An Observable of UI events, appropriately typed
 */
fun <T : Event> Node.events(eventType: EventType<T>) = JavaFxObservable.eventsOf(this, eventType)
/**
 * Create an rx Observable from a javafx ObservableValue
 * @param <T>          the type of the observed value
 * @return an Observable emitting values as the wrapped ObservableValue changes
 */
fun <T: Event> Scene.events(eventType: EventType<T>) = JavaFxObservable.eventsOf(this,eventType)
/**
 * Create an rx Observable from a javafx ObservableValue, and emits changes with old and new value pairs
 * @param <T>          the type of the observed value
 * @return an Observable emitting values as the wrapped ObservableValue changes
 */
fun <T: WindowEvent> Window.events(eventType: EventType<T>) = JavaFxObservable.eventsOf(this,eventType)

/**
 * Creates an observable that emits an ObservableList every time it is modified
 * @return An Observable emitting the ObservableList each time it changes
 */
fun <T> ObservableList<T>.onChangedObservable() = JavaFxObservable.emitOnChanged(this)

/**
 * Creates an observable that emits all removal items from an ObservableList
 * @return An Observable emitting items removed from the ObservableList
 */
fun <T> ObservableList<T>.removals() = JavaFxObservable.removalsOf(this)
/**
 * Creates an observable that emits all additions to an ObservableList
 * @return An Observable emitting items added to the ObservableList
 */
fun <T> ObservableList<T>.additions() = JavaFxObservable.additionsOf(this)
/**
 * Creates an observable that emits all updated items from an ObservableList.
 * If you declare an ObservableList that listens to one or more properties of each element,
 * you can emit the changed items every time these properties are modified
 * <pre>ObservableList<Person> sourceList = FXCollections.observableArrayList(user -> new javafx.beans.Observable[]{user.age} );</pre>
 * @return An Observable emitting items updated in the ObservableList
 */
fun <T> ObservableList<T>.updates() = JavaFxObservable.updatesOf(this)
/**
 * Emits all added, removed, and updated items from an ObservableList
 * @return An Observable emitting changed items with an ADDED, REMOVED, or UPDATED flags
 */
fun <T> ObservableList<T>.changes() = JavaFxObservable.changesOf(this)

/**
 * Emits distinctly  added and removed items from an ObservableList.
 * If dupe items with identical hashcode/equals evaluations are added to an ObservableList, only the first one will fire an ADDED item.
 * When the last dupe is removed, only then will it fire a REMOVED item.
 * @return An Observable emitting changed items with an ADDED, REMOVED, or UPDATED flags
 */
fun <T> ObservableList<T>.distinctChanges() = JavaFxObservable.distinctChangesOf(this)
/**
 * Emits distinctly added and removed items item from an ObservableList.
 * If dupe mapped R items with identical hashcode/equals evaluations are added to an ObservableList, only the first one will fire an ADDED T item.
 * When the last dupe is removed, only then will it fire a REMOVED T item.
 * @return An Observable emitting changed mapped items with an ADDED, REMOVED, or UPDATED flags
 */
fun <T,R> ObservableList<T>.distinctChanges(mapper: ((T) -> R)) = JavaFxObservable.distinctChangesOf(this,mapper)

/**
 * Emits distinctly added and removed mappings to each R item from an ObservableList.
 * If dupe mapped R items with identical hashcode/equals evaluations are added to an ObservableList, only the first one will fire an ADDED R item.
 * When the last dupe is removed, only then will it fire a REMOVED R item.
 * @return An Observable emitting changed mapped items with an ADDED, REMOVED, or UPDATED flags
 */
fun <T,R> ObservableList<T>.distinctMappingChanges(mapper: ((T) -> R)) = JavaFxObservable.distinctMappingsOf(this,mapper)

/**
 * Creates an observable that emits an ObservableMap every time it is modified
 * @return An Observable emitting the ObservableMap each time it changes
 */
fun <K,T> ObservableMap<K, T>.onChangedObservable() = JavaFxObservable.emitOnChanged(this)
/**
 * Creates an observable that emits all removal items from an ObservableMap
 * @return An Observable emitting items removed from the ObservableMap
 */
fun <K,T> ObservableMap<K, T>.removals() = JavaFxObservable.removalsOf(this)
/**
 * Creates an observable that emits all additions to an ObservableMap
 * @return An Observable emitting items added to the ObservableMap
 */
fun <K,T> ObservableMap<K, T>.additions() = JavaFxObservable.additionsOf(this)
/**
 * Emits all added, removed, and updated items from an ObservableMap
 * @return An Observable emitting changed items with an ADDED, REMOVED, or UPDATED flags
 */
fun <K,T> ObservableMap<K, T>.changes() = JavaFxObservable.changesOf(this)

/**
 * Creates an observable that emits an ObservableSet every time it is modified
 * @return An Observable emitting the ObservableSet each time it changes
 */
fun <T> ObservableSet<T>.onChangedObservable() = JavaFxObservable.emitOnChanged(this)

/**
 * Creates an observable that emits all removal items from an ObservableSet
 * @return An Observable emitting items removed from the ObservableSet
 */
fun <T> ObservableSet<T>.removals() = JavaFxObservable.removalsOf(this)
/**
 * Creates an observable that emits all additions to an ObservableSet
 * @return An Observable emitting items added to the ObservableSet
 */
fun <T> ObservableSet<T>.additions() = JavaFxObservable.additionsOf(this)
/**
 * Emits all added, removed, and updated items from an ObservableSet
 * @return An Observable emitting changed items with an ADDED, REMOVED, or UPDATED flags
 */
fun <T> ObservableSet<SetChange<T>>.changes() = JavaFxObservable.changesOf(this)


/**
 * Emits the response `T` for a given `Dialog<T>`. If no response is provided the Observable will be empty.
 */
fun <T> Dialog<T>.toObservable() = JavaFxObservable.fromDialog(this)

/**
 * Adds the `Observable` to the `CompositeObservable`, and subscribes it to all existing and future Subscribers
 */
operator fun <T> CompositeObservable<T>.plusAssign(observable: Observable<T>) {
    add(observable)
}

