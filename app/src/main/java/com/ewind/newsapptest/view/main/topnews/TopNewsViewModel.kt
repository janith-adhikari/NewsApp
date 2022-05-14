package com.ewind.newsapi.presentation.main.topnews

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ewind.newsapi.domain.model.DArticles
import com.ewind.newsapi.domain.usecase.NewsUseCase
import com.ewind.newsapptest.util.Constant
import com.ewind.newsapptest.util.Resource
import com.ewind.newsapptest.util.TempVar
import com.ewind.newsapptest.util.ext.setError
import com.ewind.newsapptest.util.ext.setLoading
import com.ewind.newsapptest.util.ext.setSuccess
import com.ewind.newsapptest.util.network.ErrorHandler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class TopNewsViewModel(val topNewsUseCase: NewsUseCase) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    val newsliveDate = MutableLiveData<Resource<MutableList<DArticles>>>()
    var isLoading = false
    var currentPage: Int = 1
    var totalCount: Int? = null

    fun getTopNews() {
        newsliveDate.setLoading()
        compositeDisposable.add(
            topNewsUseCase.getTopNews(currentPage, "us")
                .subscribeOn(Schedulers.io())
                .doOnSubscribe {
                    isLoading = true
                }
                .doOnTerminate {
                    isLoading = false
                }
                .subscribe(
                    { response ->
                        totalCount = response.totalResults
                        val listWork = response.articles
                        if (!listWork.isNullOrEmpty()) {
                            TempVar.per_page = Constant.PER_PAGE
                            newsliveDate.setSuccess(listWork.toMutableList(), null)
                        }
                    },
                    {
                        newsliveDate.setError(ErrorHandler.getApiErrorMessage(it))
                    })
        )
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}