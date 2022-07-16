package com.handoyo.reqresscorouties.repository

import com.handoyo.reqresscorouties.data.User
import com.handoyo.reqresscorouties.event.StateEventManager

interface UserRepository {
    val userStateEventManager:StateEventManager<List<User>>

    suspend fun getUsers(page:Int = 1)
}