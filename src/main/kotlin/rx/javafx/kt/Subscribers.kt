package rx.javafx.kt

import javafx.beans.binding.Binding
import javafx.beans.property.Property
import rx.Observable
import rx.subscriptions.CompositeBinding


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

/**
 * Add this `Binding` to the provided `CompositeBinding`, and returns itself
 * @return `Binding`
 */
fun <T> Binding<T>.addto(compositeBinding: CompositeBinding): Binding<T> {
    compositeBinding.add(this)
    return this
}

operator fun <T> CompositeBinding.plusAssign(binding: Binding<T>) = add(binding)

operator fun CompositeBinding.plusAssign(compositeBinding: CompositeBinding) = add(compositeBinding)

operator fun <T> CompositeBinding.minusAssign(binding: Binding<T>) = remove(binding)

operator fun CompositeBinding.minusAssign(compositeBinding: CompositeBinding) = remove(compositeBinding)
