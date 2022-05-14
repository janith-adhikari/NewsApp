package com.ewind.newsapptest.domain.model

import com.ewind.newsapptest.domain.model.DArticles

data class DResponse(
    val totalResults: Int?,
    val articles: List<DArticles>?
)