package com.ewind.newsapptest.view.main.news

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ewind.newsapptest.domain.model.Category
import com.ewind.newsapptest.domain.model.DArticles
import com.ewind.newsapptest.domain.repository.NewsRepository
import com.ewind.newsapptest.domain.repository.PreferenceRepository
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
    private val newsRepository: NewsRepository,
    private val preferenceRepository: PreferenceRepository
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    val newsLiveDate = MutableLiveData<Resource<MutableList<DArticles>>>()
    val livedataPre = MutableLiveData<Resource<List<Category>>>()

    var isLoading = false
    var currentPage: Int = 1
    var totalCount: Int? = null
    var keyword: String = "all"

    var country: String = "us"
    var lang: String = "en"

    fun getNews() {
        newsLiveDate.setLoading()
        compositeDisposable.add(
            newsRepository.getNews(keyword, country, lang, currentPage)
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
                            newsLiveDate.setSuccess(listWork.toMutableList(), null)
                        }
                    },
                    {
                        newsLiveDate.setError(ErrorHandler.getApiErrorMessage(it))
                    })
        )
    }

    fun preferenceAll() {
        compositeDisposable.add(
            preferenceRepository
                .getAllPref()
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        livedataPre.setSuccess(it, null)
                    },
                    {

                    })
        )
    }

    fun setGlob(country: String, lang: String) {
        this.country = country
        this.lang = lang
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}