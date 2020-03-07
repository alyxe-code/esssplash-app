package com.p2lem8dev.esssplash.common.livenavigation

import androidx.lifecycle.LifecycleOwner

interface LiveNavigation<T> {
    fun observe(owner: LifecycleOwner, listener: ((T) -> Unit) -> Unit)

    fun observe(owner: LifecycleOwner, executor: T) = observe(owner) { it(executor) }

    fun cancel(listener: ((T) -> Unit) -> Unit)

    fun cancel(executor: T) = cancel { it(executor) }
}
