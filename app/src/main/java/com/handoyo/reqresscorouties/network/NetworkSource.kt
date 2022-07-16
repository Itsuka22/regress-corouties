package com.handoyo.reqresscorouties.network

import com.handoyo.reqresscorouties.FlowState
import com.handoyo.reqresscorouties.data.Mapper
import com.handoyo.reqresscorouties.data.User
import com.handoyo.reqresscorouties.utils.asFlowStateEvent
import org.koin.core.annotation.Single

@Single
class NetworkSource(private val webServicesProvider: WebServicesProvider) {

    suspend fun getList(page: Int): FlowState<List<User>> {
        return webServicesProvider.get().getList(page).asFlowStateEvent {
            Mapper.mapUserResponse(it)
        }
    }

//    suspend fun getDetails(id: Int): FlowState<List<User>> {
//        return webServicesProvider.get().getList(1).asFlowStateEvent {
//            Mapper.mapUserResponse(it)
//        }
//    }
}