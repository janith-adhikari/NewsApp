package com.ewind.newsapi.domain.model

data class DResponse(
    val totalResults: Int?,
    val articles: List<DArticles>?
)