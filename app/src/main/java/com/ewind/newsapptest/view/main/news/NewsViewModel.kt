package com.ewind.newsapi.presentation.main.news

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ewind.newsapptest.domain.model.Category
import com.ewind.newsapptest.domain.model.DArticles
import com.ewind.newsapptest.domain.usecase.NewsUseCase
import com.ewind.newsapptest.domain.usecase.PreferenceUseCase
import com.ewind.newsapptest.util.Constant
import com.ewind.newsapptest.util.Resource
import com.ewind.newsapptest.util.TempVar
import com.ewind.newsapptest.util.ext.setError
import com.ewind.newsapptest.util.ext.setLoading
import com.ewind.newsapptest.util.ext.setSuccess
import com.ewind.newsapptest.util.network.ErrorHandler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class NewsViewModel(
    private val newsUseCase: NewsUseCase,
    private val preferenceUseCase: PreferenceUseCase
) :
    ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    val newsliveDate = MutableLiveData<Resource<MutableList<DArticles>>>()
    val livedataPre = MutableLiveData<Resource<List<Category>>>()
    var isLoading = false
    var currentPage: Int = 1
    var totalCount: Int? = null
    var keyword: String = "all"

    fun getNews() {
        newsliveDate.setLoading()
        compositeDisposable.add(
            newsUseCase.getNews(keyword, currentPage)
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

    fun preferenceAll() {
        compositeDisposable.add(
            preferenceUseCase
                .getPreferenceAll()
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        livedataPre.setSuccess(it, null)
                    },
                    {

                    })
        )
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}