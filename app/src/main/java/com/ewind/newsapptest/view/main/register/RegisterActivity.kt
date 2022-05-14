package com.ewind.newsapptest.view.main.register

import android.content.Intent
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import androidx.lifecycle.Observer
import com.ewind.newsapptest.databinding.ActivityRegisterBinding
import com.ewind.newsapptest.domain.model.DUser
import com.ewind.newsapptest.util.Resource
import com.ewind.newsapptest.util.ResourceState
import com.ewind.newsapptest.util.ext.showToast
import com.ewind.newsapptest.view.main.base.BaseActivity
import com.ewind.newsapptest.view.main.home.HomeActivity
import com.ewind.newsapptest.view.main.profile.ProfileViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterActivity : BaseActivity() {

    private lateinit var binding: ActivityRegisterBinding
    val viewModel by viewModel<ProfileViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        viewModel.updateLiveData.observe(this, Observer {
            updateView(it)
        })
        binding.etPassword.transformationMethod = PasswordTransformationMethod.getInstance()

        binding.btnSignUp.setOnClickListener {
            if (validation()) {
                viewModel.setUser(
                    DUser(
                        binding.etFullName.text.toString(),
                        binding.etEmail.text.toString()
                    )
                )
            }
        }
    }

    private fun updateView(resource: Resource<String>?) {
        resource?.let {
            when (it.state) {
                ResourceState.LOADING -> {}
                ResourceState.SUCCESS -> {
                    it.data?.showToast(this)
                    goToHome()
                }
                ResourceState.ERROR -> {
                    it.message?.showToast(this)
                }
            }
        }
    }

    private fun validation(): Boolean {
        if (binding.etFullName.text.isBlank() || binding.etFullName.text.toString()
                .endsWith(" ") || binding.etFullName.text.toString()
                .startsWith(" ") || !binding.etFullName.text.toString().contains(" ")
        ) {
            "Invalid User Name, Example: Henry David".showToast(this)
            return false
        }

        if (!binding.etEmail.text.toString().trim()
                .matches(Regex("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"))
        ) {
            "Invalid email".showToast(this)
            return false
        }

        if (binding.etPassword.text.toString().length < 8) {
            "Password must be more than 8 characters".showToast(this)
            return false
        }

        return true
    }

    private fun goToHome() {
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }
}