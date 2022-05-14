package com.ewind.newsapptest.view.main.splash

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import com.ewind.newsapptest.di.injectFeature
import com.ewind.newsapptest.util.Resource
import com.ewind.newsapptest.util.ResourceState
import com.ewind.newsapptest.util.ext.showToast
import com.ewind.newsapptest.view.main.base.BaseActivity
import com.ewind.newsapptest.view.main.home.HomeActivity
import com.ewind.newsapptest.view.main.profile.ProfileViewModel
import com.ewind.newsapptest.view.main.register.RegisterActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashActivity : BaseActivity() {

    val viewModel by viewModel<ProfileViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectFeature()

        viewModel.isLogLiveData.observe(this, Observer {
            updateView(it)
        })

        viewModel.isLogUser()
    }

    private fun updateView(resource: Resource<Boolean>?) {
        resource?.let {
            when(it.state){
                ResourceState.LOADING -> {}
                ResourceState.SUCCESS -> {
                    if(it.data == true){
                        goToHome()
                    } else {
                        goToRegister()
                    }
                }
                ResourceState.ERROR -> {
                    goToRegister()
                }
            }
        }
    }

    private fun goToRegister() {
        startActivity(Intent(this, RegisterActivity::class.java))
        finish()
    }

    private fun goToHome() {
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }
}