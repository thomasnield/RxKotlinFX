package rx.javafx.kt

import javafx.beans.binding.Binding
import javafx.beans.property.Property
import rx.Observable


/**
 * Binds the `Property` to an RxJava `Observable`,
 * meaning it will be bounded to show the latest emissions of that `Observable`.
 * The `Binding` is also returned so caller can be dispose it later if needed
 * @return `Binding`
 */
fun <T> Property<T>.bind(observable: Observable<T>): Binding<T> {
    val binding = observable.toBinding()
    bind(binding)
    return binding
}