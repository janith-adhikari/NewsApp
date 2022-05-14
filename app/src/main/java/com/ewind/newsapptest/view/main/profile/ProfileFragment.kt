package com.ewind.newsapi.presentation.main.profile

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.ewind.newsapi.R
import com.ewind.newsapi.domain.model.DUser
import com.ewind.newsapi.presentation.main.base.BaseFragment
import com.ewind.newsapptest.util.Resource
import com.ewind.newsapptest.util.ResourceState
import com.ewind.newsapptest.util.ext.startActivityForResult
import kotlinx.android.synthetic.main.fragment_profile.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : BaseFragment() {

    private val profileViewModel by viewModel<ProfileViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        profileViewModel.userLiveData.observe(this, Observer { setData(it) })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_profile, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        profileViewModel.getUser()

        btn_edit_profile.setOnClickListener {
            startActivityForResult<ProfileEditActivity>(REQ_USER_UPDATE) {}
        }
    }

    private fun setData(resource: Resource<DUser>?) {
        resource?.let {
            when (it.state) {
                ResourceState.LOADING -> {
                    pull_refresh.startRefresh()
                }
                ResourceState.SUCCESS -> {
                    pull_refresh.stopRefresh()
                    tv_user_name.text = it.data?.name
                }
                ResourceState.ERROR -> {
                    pull_refresh.stopRefresh()
                    //Toast.makeText(context, it.message.toString(), Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQ_USER_UPDATE) {
            if (resultCode == Activity.RESULT_OK) {
                profileViewModel.getUser()
            }
        }
    }
}