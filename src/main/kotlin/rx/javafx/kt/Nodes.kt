package rx.javafx.kt

import javafx.scene.control.ListView
import javafx.scene.control.TableView
import rx.Observable


val <T> TableView<T>.rowIndexSelections: Observable<Int>
    get() = itemSelections.map { selectionModel.selectedIndex }


val <T> TableView<T>.columnIndexSelections: Observable<Int>
    get() = selectionModel.selectedCells.toObservable().flatMap { Observable.from(it).map { it.column } }


val <T> TableView<T>.itemSelections: Observable<T>
    get() = selectionModel.selectedItemProperty().toObservable()


val <T> ListView<T>.itemSelections: Observable<T>
    get() = selectionModel.selectedItems.toObservable().flatMap { Observable.from(it) }


