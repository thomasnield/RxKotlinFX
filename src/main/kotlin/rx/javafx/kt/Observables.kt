package rx.javafx.kt

import javafx.beans.value.ObservableValue
import javafx.collections.ObservableList
import javafx.collections.ObservableMap
import javafx.collections.ObservableSet
import javafx.event.Event
import javafx.event.EventType
import javafx.scene.Node
import javafx.scene.Scene
import javafx.scene.control.ContextMenu
import javafx.scene.control.MenuItem
import javafx.stage.Window
import javafx.stage.WindowEvent
import rx.Observable
import rx.javafx.sources.SetChange
import rx.observables.JavaFxObservable
import rx.subscribers.JavaFxSubscriber

/**
 * Turns an Observable into a JavaFX Binding. Calling the Binding's dispose() method will handle the unsubscription.
 */
fun <T> Observable<T>.toBinding() = JavaFxSubscriber.toBinding(this)
/**
 * Turns an Observable into a JavaFX Binding. Calling the Binding's dispose() method will handle the unsubscription.
 */
fun <T> Observable<T>.toBinding(errorHandler: (Throwable) -> Unit) = JavaFxSubscriber.toBinding(this,errorHandler)
/**
 * Create an rx Observable from a javafx ObservableValue
 * @param <T>          the type of the observed value
 * @return an Observable emitting values as the wrapped ObservableValue changes
 */
fun <T> ObservableValue<T>.toObservable() = JavaFxObservable.fromObservableValue(this)
/**
 * Create an rx Observable from a javafx ObservableValue, and emits changes with old and new value pairs
 * @param <T>          the type of the observed value
 * @return an Observable emitting values as the wrapped ObservableValue changes
 */
fun <T> ObservableValue<T>.toObservableChanges() = JavaFxObservable.fromObservableValueChanges(this)

/**
 * Creates an observable corresponding to javafx ContextMenu action events.
 * @return An Observable of UI ActionEvents
 */
fun ContextMenu.actionEvents() = JavaFxObservable.fromActionEvents(this)

/**
 * Creates an observable corresponding to javafx MenuItem action events.
 *
 * @param menuItem      The target of the ActionEvents
 * @return An Observable of UI ActionEvents
 */
fun MenuItem.actionEvents() = JavaFxObservable.fromActionEvents(this)

/**
 * Creates an observable corresponding to javafx Node action events.
 * @return An Observable of UI ActionEvents
 */
fun Node.actionEvents() = JavaFxObservable.fromActionEvents(this)

/**
 * Creates an observable corresponding to javafx Node events.
 * @param eventType The type of the observed UI events
 * @return An Observable of UI events, appropriately typed
 */
fun <T : Event> Node.events(eventType: EventType<T>) = JavaFxObservable.fromNodeEvents(this, eventType)
/**
 * Create an rx Observable from a javafx ObservableValue
 * @param <T>          the type of the observed value
 * @return an Observable emitting values as the wrapped ObservableValue changes
 */
fun <T: Event> Scene.events(eventType: EventType<T>) = JavaFxObservable.fromSceneEvents(this,eventType)
/**
 * Create an rx Observable from a javafx ObservableValue, and emits changes with old and new value pairs
 * @param <T>          the type of the observed value
 * @return an Observable emitting values as the wrapped ObservableValue changes
 */
fun <T: WindowEvent> Window.events(eventType: EventType<T>) = JavaFxObservable.fromWindowEvents(this,eventType)

/**
 * Creates an observable that emits an ObservableList every time it is modified
 * @return An Observable emitting the ObservableList each time it changes
 */
fun <T> ObservableList<T>.onChangedObservable() = JavaFxObservable.fromObservableList(this)

/**
 * Creates an observable that emits all removal items from an ObservableList
 * @return An Observable emitting items removed from the ObservableList
 */
fun <T> ObservableList<T>.removals() = JavaFxObservable.fromObservableListRemovals(this)
/**
 * Creates an observable that emits all additions to an ObservableList
 * @return An Observable emitting items added to the ObservableList
 */
fun <T> ObservableList<T>.additions() = JavaFxObservable.fromObservableListAdds(this)
/**
 * Creates an observable that emits all updated items from an ObservableList.
 * If you declare an ObservableList that listens to one or more properties of each element,
 * you can emit the changed items every time these properties are modified
 * <pre>ObservableList<Person> sourceList = FXCollections.observableArrayList(user -> new javafx.beans.Observable[]{user.age} );</pre>
 * @return An Observable emitting items updated in the ObservableList
 */
fun <T> ObservableList<T>.updates() = JavaFxObservable.fromObservableListUpdates(this)
/**
 * Emits all added, removed, and updated items from an ObservableList
 * @return An Observable emitting changed items with an ADDED, REMOVED, or UPDATED flags
 */
fun <T> ObservableList<T>.changes() =JavaFxObservable.fromObservableListChanges(this)

/**
 * Emits distinctly  added and removed items from an ObservableList.
 * If dupe items with identical hashcode/equals evaluations are added to an ObservableList, only the first one will fire an ADDED item.
 * When the last dupe is removed, only then will it fire a REMOVED item.
 * @return An Observable emitting changed items with an ADDED, REMOVED, or UPDATED flags
 */
fun <T> ObservableList<T>.distinctChanges() = JavaFxObservable.fromObservableListDistinctChanges(this)
/**
 * Emits distinctly added and removed items item from an ObservableList.
 * If dupe mapped R items with identical hashcode/equals evaluations are added to an ObservableList, only the first one will fire an ADDED T item.
 * When the last dupe is removed, only then will it fire a REMOVED T item.
 * @return An Observable emitting changed mapped items with an ADDED, REMOVED, or UPDATED flags
 */
fun <T,R> ObservableList<T>.distinctChanges(mapper: ((T) -> R)) = JavaFxObservable.fromObservableListDistinctChanges(this,mapper)

/**
 * Emits distinctly added and removed mappings to each R item from an ObservableList.
 * If dupe mapped R items with identical hashcode/equals evaluations are added to an ObservableList, only the first one will fire an ADDED R item.
 * When the last dupe is removed, only then will it fire a REMOVED R item.
 * @return An Observable emitting changed mapped items with an ADDED, REMOVED, or UPDATED flags
 */
fun <T,R> ObservableList<T>.distinctMappingChanges(mapper: ((T) -> R)) = JavaFxObservable.fromObservableListDistinctMappings(this,mapper)

/**
 * Creates an observable that emits an ObservableMap every time it is modified
 * @return An Observable emitting the ObservableMap each time it changes
 */
fun <K,T> ObservableMap<K, T>.onChangedObservable() = JavaFxObservable.fromObservableMap(this)
/**
 * Creates an observable that emits all removal items from an ObservableMap
 * @return An Observable emitting items removed from the ObservableMap
 */
fun <K,T> ObservableMap<K,T>.removals() = JavaFxObservable.fromObservableMapRemovals(this)
/**
 * Creates an observable that emits all additions to an ObservableMap
 * @return An Observable emitting items added to the ObservableMap
 */
fun <K,T> ObservableMap<K,T>.additions() = JavaFxObservable.fromObservableMapAdds(this)
/**
 * Emits all added, removed, and updated items from an ObservableMap
 * @return An Observable emitting changed items with an ADDED, REMOVED, or UPDATED flags
 */
fun <K,T> ObservableMap<K,T>.changes() = JavaFxObservable.fromObservableMapChanges(this)

/**
 * Creates an observable that emits an ObservableSet every time it is modified
 * @return An Observable emitting the ObservableSet each time it changes
 */
fun <T> ObservableSet<T>.onChangedObservable() = JavaFxObservable.fromObservableSet(this)

/**
 * Creates an observable that emits all removal items from an ObservableSet
 * @return An Observable emitting items removed from the ObservableSet
 */
fun <T> ObservableSet<T>.removals() = JavaFxObservable.fromObservableSetRemovals(this)
/**
 * Creates an observable that emits all additions to an ObservableSet
 * @return An Observable emitting items added to the ObservableSet
 */
fun <T> ObservableSet<T>.additions() = JavaFxObservable.fromObservableSetAdds(this)
/**
 * Emits all added, removed, and updated items from an ObservableSet
 * @return An Observable emitting changed items with an ADDED, REMOVED, or UPDATED flags
 */
fun <T> ObservableSet<SetChange<T>>.changes() =JavaFxObservable.fromObservableSetChanges(this)

