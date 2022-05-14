package com.ewind.newsapptest.data.repository

import com.ewind.newsapptest.data.source.remote.apis.NewsApi
import com.ewind.newsapi.domain.model.DResponse
import com.ewind.newsapi.domain.model.toViewModel
import com.ewind.newsapi.domain.repository.NewsRepository
import io.reactivex.Observable

class NewsRepositoryImpl(val newsApi: NewsApi) : NewsRepository {
    override fun getTopNews(page: Int, country: String): Observable<DResponse> {
        return newsApi.getTopNews(page = page, country = country).map {
            it.toViewModel()
        }
    }

    override fun getNews(keyword: String?, page: Int): Observable<DResponse> {
        return newsApi.getNews(keyword, page).map {
            it.toViewModel()
        }
    }
}