package rx.javafx.kt

import javafx.scene.control.ListView
import javafx.scene.control.TableView
import rx.Observable

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

