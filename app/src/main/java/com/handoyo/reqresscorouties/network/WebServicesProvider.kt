package com.handoyo.reqresscorouties.network

import org.koin.core.annotation.Single

@Single
class WebServicesProvider {
    fun get(): WebServices {
        return WebServices.build()
    }
}