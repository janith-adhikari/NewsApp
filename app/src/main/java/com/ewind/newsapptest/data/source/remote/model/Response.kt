package com.ewind.newsapptest.data.source.remote.model

import com.ewind.newsapptest.data.source.remote.model.Articles
import com.google.gson.annotations.SerializedName

data class Response(
    @SerializedName("status") val status: String?,
    @SerializedName("totalResults") val totalResults: Int?,
    @SerializedName("articles") val articles: List<Articles>?
)