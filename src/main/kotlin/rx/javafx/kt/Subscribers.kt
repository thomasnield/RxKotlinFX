package rx.javafx.kt

import javafx.beans.binding.Binding
import javafx.beans.property.Property
import rx.Observable
import rx.Subscription
import rx.javafx.sources.CompositeObservable
import rx.subscriptions.CompositeBinding
import rx.subscriptions.CompositeSubscription


/**
 * Binds the `Property` to an RxJava `Observable`,
 * meaning it will be bounded to show the latest emissions of that `Observable`.
 * The `Binding` is also returned so caller can be dispose it later if needed
 * @return `Binding`
 */
fun <T> Property<T>.bind(observable: Observable<T>, actionOp: (BindingSideEffects<T>.() -> Unit)? = null): Binding<T> {
    val transformer = actionOp?.let {
        val sideEffects = BindingSideEffects<T>()
        it.invoke(sideEffects)
        sideEffects.transformer
    }
    val binding = (transformer?.let { observable.compose(it) }?:observable).toBinding()
    bind(binding)
    return binding
}

/**
 * Add this `Binding` to the provided `CompositeBinding`, and returns itself
 * @return `Binding`
 */
fun <T> Binding<T>.addTo(compositeBinding: CompositeBinding): Binding<T> {
    compositeBinding.add(this)
    return this
}

/**
 * Add this `Observable` to the provided `CompositeObservable`, and return the `Subscription` that links them.
 * You can optionally provide a `CompositeSubscription` to automatically add the `Subscription` to
 */
fun <T> Observable<T>.addTo(compositeObservable: CompositeObservable<T>, compositeSubscription: CompositeSubscription? = null): Subscription {
    val subscription = compositeObservable.add(this)
    compositeSubscription?.apply { this.add(subscription) }
    return subscription
}


operator fun <T> CompositeBinding.plusAssign(binding: Binding<T>) = add(binding)

operator fun CompositeBinding.plusAssign(compositeBinding: CompositeBinding) = add(compositeBinding)

operator fun <T> CompositeBinding.minusAssign(binding: Binding<T>) = remove(binding)

operator fun CompositeBinding.minusAssign(compositeBinding: CompositeBinding) = remove(compositeBinding)

class BindingSideEffects<T> {
    private var onNextAction: ((T) -> Unit)? = null
    private var onCompletedAction: (() -> Unit)? = null
    private var onErrorAction: ((ex: Throwable) -> Unit)? = null

    fun onNext(onNext: (T) -> Unit): Unit {
        onNextAction = onNext
    }

    fun onCompleted(onCompleted: () -> Unit): Unit {
        onCompletedAction = onCompleted
    }

    fun onError(onError: (ex: Throwable) -> Unit): Unit {
        onErrorAction = onError
    }

    internal val transformer: Observable.Transformer<T, T> get() = Observable.Transformer<T, T> { obs ->
        var withActions: Observable<T> = obs
        withActions = onNextAction?.let { withActions.doOnNext(onNextAction) } ?: withActions
        withActions = onCompletedAction?.let { withActions.doOnCompleted(onCompletedAction) } ?: withActions
        withActions = onErrorAction?.let { withActions.doOnError(onErrorAction) } ?: withActions
        withActions
    }
}
