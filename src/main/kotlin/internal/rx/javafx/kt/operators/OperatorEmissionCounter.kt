package internal.rx.javafx.kt.operators


import rx.Observable
import rx.Producer
import rx.Subscriber
import rx.exceptions.Exceptions

class OperatorEmissionCounter<T>(val ctObserver: CountObserver): Observable.Operator<T, T> {

    override fun call(child: Subscriber<in T>): Subscriber<in T> {

        val parent = object: Subscriber<T>() {

            private var count = 0
            private var done = false

            override fun onCompleted() {
                if (done)
                    return

                try {
                    ctObserver.doOnCompletedCountAction.invoke(count)
                } catch (e: Exception) {
                    Exceptions.throwIfFatal(e)
                    onError(e)
                    return
                }
                done = true
                child.onCompleted()
            }

            override fun onError(e: Throwable) {
                if (done)
                    return
                try {
                    ctObserver.doOnErrorCountAction.invoke(count)
                } catch(e: Exception) {
                    Exceptions.throwIfFatal(e)
                    child.onError(e)
                }
            }

            override fun onNext(t: T) {
                if (done)
                    return
                try {
                    ctObserver.doOnNextCountAction(++count)
                } catch(e: Exception) {
                    Exceptions.throwIfFatal(e)
                    onError(e)
                    return
                }
                child.onNext(t)
            }

            override fun setProducer(p: Producer) = child.setProducer(p)
        }

        child.add(parent)

        return parent
    }
}

class CountObserver(
        val doOnNextCountAction: (Int) -> Unit = {i -> Unit},
        val doOnCompletedCountAction: (Int) -> Unit = {i -> Unit},
        val doOnErrorCountAction: (Int) -> Unit = {i -> Unit}
)