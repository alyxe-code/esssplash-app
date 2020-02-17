package com.p2lem8dev.esssplash.common.livenavigation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

class LiveNavigationImplementation<T> :
    LiveNavigation<T> {
    private val liveData =
        MutableLiveData<SingleLiveEvent<(T) -> Unit>>()
    private val events = mutableListOf<(T) -> Unit>()

    override fun observe(owner: LifecycleOwner, listener: ((T) -> Unit) -> Unit) {
        liveData.observe(owner, Observer { it?.get(listener) })
    }

    fun post(event: (T) -> Unit) {
        events.add(event)
        if (events.size == 1)
            liveData.postValue(
                SingleLiveEvent(
                    this::onEventProceed
                )
            )
    }

    fun call(method: T.() -> Unit) = post(method)

    private fun onEventProceed(worker: ((T) -> Unit) -> Unit) {
        while (events.isNotEmpty())
            worker(events.removeAt(0))
    }

    class SingleLiveEvent<V>(private val listener: ((V) -> Unit) -> Unit) {
        private var proceed = false
        operator fun get(handler: (V) -> Unit) {
            if (!proceed) {
                proceed = true
                listener.invoke(handler)
            }
        }
    }
}
