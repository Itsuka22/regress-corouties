package com.handoyo.reqresscorouties.event

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableStateFlow

class MutableStateEventManager<T> : StateEventManager<T>(), FlowCollector<StateEvent<T>> {
    private val flowEvent: MutableStateFlow<StateEvent<T>> = MutableStateFlow(StateEvent.Idle())
    override var errorDispatcher: CoroutineExceptionHandler =
        CoroutineExceptionHandler { _, throwable ->
            throwable.printStackTrace()
            runBlocking {
                val stateError = StateEvent.Failure<T>(throwable)
                flowEvent.emit(stateError)
            }

        }
    override var listener: StateEvent<T>.(StateEventManager<T>) -> Unit = {}

    override fun subscribe(
        scope: CoroutineScope,
        onIdle: () -> Unit,
        onLoading: () -> Unit,
        onFailure: (throwable: Throwable) -> Unit,
        onSuccess: (T) -> Unit
    ) {
        createScope(scope).launch {
            flowEvent.collect {
                value = it
                listener.invoke(it, this@MutableStateEventManager)
                when (it) {
                    is StateEvent.Idle -> onIdle.invoke()
                    is StateEvent.Loading -> onLoading.invoke()
                    is StateEvent.Failure -> onFailure.invoke(it.exception)
                    is StateEvent.Success -> onSuccess.invoke(it.data)
                }
            }
        }
    }

    override fun invoke(): T? {
        return (value as? StateEvent.Success<T>)?.data
    }

    override fun createScope(another: CoroutineScope): CoroutineScope {
        return another + errorDispatcher
    }

    override suspend fun emit(value: StateEvent<T>) {
        flowEvent.emit(value)
    }
}