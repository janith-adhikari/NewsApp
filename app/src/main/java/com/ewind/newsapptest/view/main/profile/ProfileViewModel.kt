package com.ewind.newsapptest.view.main.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ewind.newsapptest.domain.model.DUser
import com.ewind.newsapptest.domain.repository.ProfileRepository
import com.ewind.newsapptest.domain.usecase.ProfileUserCase
import com.ewind.newsapptest.util.Msg
import com.ewind.newsapptest.util.Resource
import com.ewind.newsapptest.util.ext.setError
import com.ewind.newsapptest.util.ext.setLoading
import com.ewind.newsapptest.util.ext.setSuccess
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ProfileViewModel(val profileRepository: ProfileRepository) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    val userLiveData = MutableLiveData<Resource<DUser>>()
    val updateLiveData = MutableLiveData<Resource<String>>()
    val isLogLiveData = MutableLiveData<Resource<Boolean>>()

    fun getUser() {
        userLiveData.setLoading()
        compositeDisposable.add(
            profileRepository.getUser()
                .subscribeOn(Schedulers.io())
                .subscribe(
                    { response ->
                        userLiveData.setSuccess(response)
                    },
                    {
                        userLiveData.setError()
                    })
        )
    }

    fun isLogUser() {
        compositeDisposable.add(
            profileRepository.getUser()
                .subscribeOn(Schedulers.io())
                .subscribe(
                    { response ->
                        if (response.email != null) {
                            isLogLiveData.setSuccess(true)
                        } else {
                            isLogLiveData.setSuccess(false)
                        }
                    },
                    {
                        isLogLiveData.setError()
                    })
        )
    }

    fun setUser(user: DUser) {
        compositeDisposable.add(
            profileRepository.saveUser(user)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        updateLiveData.setSuccess("Registration successful.")
                    },
                    {
                        updateLiveData.setError(Msg.INTERNAL_ISSUE)
                    })
        )
    }

    fun deleteUser() {
        compositeDisposable.add(
            profileRepository.deleteUser()
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        updateLiveData.setSuccess("Sign Out")
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