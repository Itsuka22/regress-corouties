package com.handoyo.reqresscorouties.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.handoyo.reqresscorouties.data.User
import com.handoyo.reqresscorouties.event.StateEventSubscriber
import com.handoyo.reqresscorouties.repository.UserRepository
import com.handoyo.reqresscorouties.utils.convertEventToSubscriber
import kotlinx.coroutines.launch
import org.koin.core.annotation.Scope

@Scope(MainActivity::class)
class MainViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val userManager = userRepository.userStateEventManager

    private val userScope = userManager.createScope(viewModelScope)

    fun subscribeUser(subscriber: StateEventSubscriber<List<User>>) {
        convertEventToSubscriber(userManager, subscriber)
    }

    fun getUsers(page: Int) = userScope.launch {
        userRepository.getUsers(page)
    }
}