package com.handoyo.reqresscorouties.ui

import android.os.Bundle
import com.handoyo.reqresscorouties.databinding.ActivityMainBinding
import com.handoyo.reqresscorouties.data.User
import com.handoyo.reqresscorouties.event.StateEventSubscriber
import org.koin.androidx.scope.ScopeActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ScopeActivity() {

    //    private val scope : Scope by activityScope()
    private val viewModel: MainViewModel by viewModel()
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        subscribeUser()
        binding.run {
            btnFetchUser.setOnClickListener {
                viewModel.getUsers(1)
            }
        }
        viewModel.subscribeUser(subscribeUser())

    }


    private fun subscribeUser() = object : StateEventSubscriber<List<User>> {
        override fun onIdle() {
            binding.tvResultUser.append("idle...\n")
        }

        override fun onLoading() {
            binding.tvResultUser.append("loading...\n")

        }

        override fun onFailure(throwable: Throwable) {
            binding.tvResultUser.append("${throwable.message}...\n")
        }

        override fun onSuccess(data: List<User>) {
            binding.tvResultUser.append("$data...\n")

        }

    }
}