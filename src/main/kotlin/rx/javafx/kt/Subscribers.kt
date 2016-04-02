package rx.javafx.kt

import javafx.beans.property.Property
import rx.Observable

fun <T> Property<T>.bind(observable: Observable<T>) = bind(observable.toBinding())