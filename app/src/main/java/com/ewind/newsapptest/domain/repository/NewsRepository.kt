package com.ewind.newsapi.domain.repository

import com.ewind.newsapi.domain.model.DResponse
import io.reactivex.Observable

interface NewsRepository {
    fun getTopNews(page: Int, country: String): Observable<DResponse>
    fun getNews(keyword: String? = null, page: Int): Observable<DResponse>
}