package com.handoyo.reqresscorouties.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.handoyo.reqresscorouties.FlowState
import com.handoyo.reqresscorouties.event.MutableStateEventManager
import com.handoyo.reqresscorouties.event.StateEvent
import com.handoyo.reqresscorouties.event.StateEventManager
import com.handoyo.reqresscorouties.event.StateEventSubscriber
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import java.lang.Exception

fun <T> default() = MutableStateEventManager<T>()
fun <T> ViewModel.convertEventToSubscriber(
    eventManager: StateEventManager<T>,
    subscriber: StateEventSubscriber<T>
) {
    eventManager.subscribe(
        scope = viewModelScope,
        onIdle = subscriber::onIdle,
        onLoading = subscriber::onLoading,
        onFailure = subscriber::onFailure,
        onSuccess = subscriber::onSuccess
    )
}

fun <T, U> Response<T>.asFlowStateEvent(mapper: (T) -> U): FlowState<U> {
    return flow {
        emit(StateEvent.Loading())
        delay(2000)
        val emitData = try {
            val body = body()
            if (isSuccessful && body != null) {
                val dataMapper = mapper.invoke(body)
                StateEvent.Success(dataMapper)
            } else {
                val exception = Throwable(message())
                StateEvent.Failure(exception)
            }
        } catch (e: Exception) {
            StateEvent.Failure(e)
        }
        emit(emitData)
    }
}

fun <T, U> FlowState<T>.flatMap(transform: (T) -> U): FlowState<U> {
    return this.flatMapMerge {
        flow {
            when (it) {
                is StateEvent.Loading -> emit(StateEvent.Loading())
                is StateEvent.Idle -> emit(StateEvent.Idle())
                is StateEvent.Failure -> emit(StateEvent.Failure(it.exception))
                is StateEvent.Success -> {
                    val data = transform.invoke(it.data)
                    emit(StateEvent.Success(data))
                }

            }

        }
    }
}