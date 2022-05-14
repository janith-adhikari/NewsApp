package com.ewind.newsapptest.view.main.register

import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import com.ewind.newsapptest.databinding.ActivityRegisterBinding
import com.ewind.newsapptest.view.main.base.BaseActivity

class RegisterActivity : BaseActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.etPassword.transformationMethod = PasswordTransformationMethod.getInstance()


    }
}