package com.handoyo.reqresscorouties

import android.app.Application
import com.handoyo.reqresscorouties.di.MainModule
import com.handoyo.reqresscorouties.event.StateEvent
import kotlinx.coroutines.flow.Flow
import org.koin.core.context.startKoin
import org.koin.ksp.generated.module

typealias FlowState<T> = Flow<StateEvent<T>>

class MainApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(MainModule().module)
        }
    }

}