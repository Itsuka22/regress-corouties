package com.handoyo.reqresscorouties.repository

import com.handoyo.reqresscorouties.data.User
import com.handoyo.reqresscorouties.event.StateEventManager
import com.handoyo.reqresscorouties.network.NetworkSource
import com.handoyo.reqresscorouties.utils.default
import org.koin.core.annotation.Single

@Single
class MainRepositoryImpl(
    private val networkSource: NetworkSource
) : UserRepository {

    private val _userStateEventManager = default<List<User>>()
    override val userStateEventManager: StateEventManager<List<User>>
        get() = _userStateEventManager

    override suspend fun getUsers(page: Int) {
        networkSource
            .getList(page)
            .collect(_userStateEventManager)
    }
}