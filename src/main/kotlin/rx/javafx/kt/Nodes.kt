package rx.javafx.kt

import javafx.scene.control.TableView
import rx.Observable


val <T> TableView<T>.rowSelections: Observable<Int>
    get() = itemSelections.map { selectionModel.selectedIndex }

/*
val <T> TableView<T>.columnSelections: Observable<Int>
    get() = selectionModel.selectedcell
*/

val <T> TableView<T>.itemSelections: Observable<T>
    get() = this.selectionModel.selectedItemProperty().toObservable()

