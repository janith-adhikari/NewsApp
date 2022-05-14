package com.ewind.newsapi.presentation.main.profile

import android.app.Activity
import android.os.Bundle
import androidx.lifecycle.Observer
import com.ewind.newsapptest.util.ext.showToast
import com.ewind.newsapi.R
import com.ewind.newsapi.domain.model.DUser
import com.ewind.newsapptest.view.main.base.BaseActivity
import com.ewind.newsapptest.util.Resource
import com.ewind.newsapptest.util.ResourceState
import com.ewind.newsapptest.util.ext.hideKeyboard
import kotlinx.android.synthetic.main.activity_profile_edit.*
import org.koin.androidx.viewmodel.ext.android.viewModel

const val REQ_USER_UPDATE = 2145

class ProfileEditActivity : BaseActivity() {

    private val profileViewModel by viewModel<ProfileViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_edit)

        supportActionBar?.apply {
            this.setDisplayHomeAsUpEnabled(true)
            this.setDisplayShowHomeEnabled(true)
            this.setHomeAsUpIndicator(R.drawable.ic_back)
        }

        profileViewModel.userLiveData.observe(this, Observer { setData(it) })
        profileViewModel.updateLiveData.observe(this, Observer { handleUi(it) })
        profileViewModel.getUser()

        btn_save.setOnClickListener {
            saveUser()
        }
    }

    private fun saveUser() {
        if (validate()) {
            val dUser = DUser(et_name.getStringTrim())
            profileViewModel.setUser(dUser)
            hideKeyboard()
        }
    }

    private fun validate(): Boolean {
        return et_name.validate("User name is required") { it.length > 2 }
    }

    private fun setData(resource: Resource<DUser>?) {
        resource?.let {
            when (it.state) {
                ResourceState.LOADING -> {
                }
                ResourceState.SUCCESS -> {
                    et_name.setText(it.data?.name)
                }
                ResourceState.ERROR -> {
                    //Toast.makeText(this, it.message.toString(), Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun handleUi(resource: Resource<String>?) {
        resource?.let {
            when (it.state) {
                ResourceState.LOADING -> {
                }
                ResourceState.SUCCESS -> {
                    it.data?.showToast(this)
                    setResult(Activity.RESULT_OK)
                    finish()
                }
                ResourceState.ERROR -> {
                    it.message.toString().showToast(this)
                }
            }
        }
    }
}
