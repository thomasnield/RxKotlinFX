package com.github.thomasnield.rxkotlinfx

import io.reactivex.Observable
import java.util.*

@Deprecated("Don't use this anymore. Implement yourself.", ReplaceWith("Single.just(this).filter { it.isPresent }.map { it.get() }", "io.reactivex.Observable"))
fun <T> Optional<T>.toObservable() = Observable.just(this)
        .filter { it.isPresent }
        .map { it.get() }