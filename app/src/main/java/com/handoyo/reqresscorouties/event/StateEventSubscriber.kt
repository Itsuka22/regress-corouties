package com.handoyo.reqresscorouties.event

interface StateEventSubscriber<T> {
    fun onIdle()
    fun onLoading()
    fun onFailure(throwable: Throwable)
    fun onSuccess(data:T)

}