package rx.javafx.kt

import javafx.beans.property.Property
import rx.Observable


/**
 * Binds the `Property` to an RxJava `Observable`,
 * meaning it will be bounded to show the latest emissions of that `Observable`
 */
fun <T> Property<T>.bind(observable: Observable<T>) = bind(observable.toBinding())