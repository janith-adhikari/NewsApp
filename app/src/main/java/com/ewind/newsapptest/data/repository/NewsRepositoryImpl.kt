package com.ewind.newsapptest.data.repository

import com.ewind.newsapptest.data.source.remote.apis.NewsApi
import com.ewind.newsapptest.domain.model.DResponse
import com.ewind.newsapptest.domain.model.toViewModel
import com.ewind.newsapptest.domain.repository.NewsRepository
import io.reactivex.Observable

class NewsRepositoryImpl(val newsApi: NewsApi) : NewsRepository {

    override fun getTopNews(
        page: Int,
        country: String,
        category: String,
        language: String
    ): Observable<DResponse> {
        return newsApi.getTopNews(
            page = page,
            country = country,
            category = category,
            language = language
        ).map {
            it.toViewModel()
        }
    }

    override fun getNews(
        keyword: String?,
        country: String,
        language: String,
        page: Int
    ): Observable<DResponse> {
        return newsApi.getNews(
            keyword = keyword,
            page = page,
            language = language
        ).map {
            it.toViewModel()
        }
    }
}