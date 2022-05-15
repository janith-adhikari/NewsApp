package com.ewind.newsapptest.domain.usecase

import com.ewind.newsapptest.domain.model.DResponse
import com.ewind.newsapptest.domain.repository.NewsRepository
import io.reactivex.Observable

class NewsUseCase(
    val newsRepository: NewsRepository
) {
    fun getTopNews(
        page: Int,
        country: String,
        category: String,
        language: String
    ) =
        newsRepository.getTopNews(page, country, category, language)

    fun getNews(
        keyword: String?,
        country: String,
        language: String,
        page: Int
    ): Observable<DResponse> =
        newsRepository.getNews(keyword, country, language, page)
}