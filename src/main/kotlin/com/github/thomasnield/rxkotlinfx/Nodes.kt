package com.github.thomasnield.rxkotlinfx

import io.reactivex.rxjava3.core.Observable
import javafx.scene.control.*

/**
 * Returns an [Observable] emitting integer values for selected row indexes.
 */
val <T> TableView<T>.rowIndexSelections: Observable<Int>
    get() = itemSelections.map { selectionModel.selectedIndex }

/**
 * Returns an [Observable] emitting integer values for selected column indexes.
 */
val <T> TableView<T>.columnIndexSelections: Observable<Int>
    get() = selectionModel.selectedCells
            .additions().map { it.column }

/**
 * Returns an [Observable] emitting selected items for the given TableView
 */
val <T> TableView<T>.itemSelections: Observable<T>
    get() = selectionModel.selectedItemProperty().toObservable()

/**
 * Returns an [Observable] emitting single selected items for the given [ListView]
 */
val <T> ListView<T>.itemSelections: Observable<T>
    get() = selectionModel.selectedItems.additions()

/**
 * Returns an [Observable] emitting single selected items for the given [ComboBox]
 */
val <T> ComboBox<T>.valueSelections: Observable<T>
    get() = valueProperty().toObservable()

/**
 * Returns an [Observable] emitting text value inputs for the given [TextField]
 */
val TextField.textValues: Observable<String>
        get() = textProperty().toObservable()

/**
 * Returns an [Observable] emitting [Tab] selections for the given [TabPane]
 */
val TabPane.tabSelections: Observable<Tab>
        get() = selectionModel.selectedItemProperty().toObservable()
