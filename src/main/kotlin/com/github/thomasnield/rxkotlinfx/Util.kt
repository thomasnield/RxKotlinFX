package com.github.thomasnield.rxkotlinfx

import io.reactivex.Observable
import java.util.Optional

fun <T> Optional<T>.toObservable() = Observable.just(this)
        .filter { it.isPresent }
        .map { it.get() }