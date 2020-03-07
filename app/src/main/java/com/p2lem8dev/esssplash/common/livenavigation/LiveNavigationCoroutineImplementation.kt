package com.p2lem8dev.esssplash.common.livenavigation

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.receiveOrNull

@InternalCoroutinesApi
@ExperimentalCoroutinesApi
class LiveNavigationCoroutineImplementation<T> : LiveNavigation<T> {

    private val listeners = mutableSetOf<SingleLiveEvent<T>>()
    private val actionsChannel = BroadcastChannel<(T) -> Unit>(Channel.CONFLATED)

    override fun observe(owner: LifecycleOwner, listener: ((T) -> Unit) -> Unit) {
        val subscription = actionsChannel.openSubscription()
        val event = SingleLiveEvent(
            channel = subscription,
            scope = owner.lifecycleScope,
            listener = listener
        )
        listeners.add(event)
    }

    override fun cancel(listener: ((T) -> Unit) -> Unit) {
        val event = listeners.firstOrNull { it == listener } ?: return
        event.cancel()
        listeners.remove(event)
    }

    fun post(action: (T) -> Unit) = call(action)

    fun call(action: T.() -> Unit) {
        if (actionsChannel.isClosedForSend)
            return
        actionsChannel.offer(action)
    }

    @InternalCoroutinesApi
    class SingleLiveEvent<T>(
        private val channel: ReceiveChannel<(T) -> Unit>,
        scope: CoroutineScope,
        private val listener: ((T) -> Unit) -> Unit
    ) : LifecycleObserver {
        init {
            scope.launch(Dispatchers.Default) {
                while (true) {
                    if (channel.isClosedForReceive)
                        break
                    withContext(Dispatchers.Main.immediate) {
                        channel.receiveOrNull()?.let(listener)
                    }
                }
            }
        }

        fun cancel() = channel.cancel()
    }
}
