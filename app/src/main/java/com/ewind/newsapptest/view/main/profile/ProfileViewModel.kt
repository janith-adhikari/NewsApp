package com.ewind.newsapi.presentation.main.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ewind.newsapi.domain.model.DUser
import com.ewind.newsapi.domain.usecase.ProfileUserCase
import com.ewind.newsapptest.util.Msg
import com.ewind.newsapptest.util.Resource
import com.ewind.newsapptest.util.ext.setError
import com.ewind.newsapptest.util.ext.setLoading
import com.ewind.newsapptest.util.ext.setSuccess
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ProfileViewModel(val profileUserCase: ProfileUserCase) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    val userLiveData = MutableLiveData<Resource<DUser>>()
    val updateLiveData = MutableLiveData<Resource<String>>()

    fun getUser() {
        userLiveData.setLoading()
        compositeDisposable.add(
            profileUserCase.getUser()
                .subscribeOn(Schedulers.io())
                .subscribe(
                    { response ->
                        userLiveData.setSuccess(response, null)
                    },
                    {
                        userLiveData.setError()
                    })
        )
    }

    fun setUser(user: DUser) {
        compositeDisposable.add(
            profileUserCase.saveUser(user)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        updateLiveData.setSuccess("Update success.", null)
                    },
                    {
                        updateLiveData.setError(Msg.INTERNAL_ISSUE)
                    })
        )
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }

}