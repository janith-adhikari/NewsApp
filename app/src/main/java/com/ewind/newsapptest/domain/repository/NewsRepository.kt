package com.ewind.newsapptest.domain.repository

import com.ewind.newsapptest.domain.model.DResponse
import io.reactivex.Observable

interface NewsRepository {
    fun getTopNews(
        page: Int,
        country: String,
        category: String,
        language: String
    ): Observable<DResponse>

    fun getNews(
        keyword: String? = null,
        country: String,
        language: String,
        page: Int
    ): Observable<DResponse>
}