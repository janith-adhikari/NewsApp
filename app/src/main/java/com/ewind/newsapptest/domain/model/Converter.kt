package com.ewind.newsapi.domain.model

import com.ewind.newsapptest.data.source.local.model.PreferencesDB
import com.ewind.newsapptest.data.source.local.model.UserDB
import com.ewind.newsapptest.data.source.remote.model.Articles
import com.ewind.newsapptest.data.source.remote.model.Response
import com.ewind.newsapptest.data.source.remote.model.Source

fun Response.toViewModel(): DResponse = DResponse(
    totalResults, articles?.map { it.toViewModel() }
)

fun Articles.toViewModel(): DArticles = DArticles(
    source?.toViewModel(), author, title, description, url, urlToImage, publishedAt, content
)

fun Source.toViewModel(): DSource = DSource(
    id, name
)

fun PreferencesDB.toViewModel(): Category = Category(keyword)

fun Category.toDBModel(): PreferencesDB = PreferencesDB(
    key
)

fun UserDB.toViewModel(): DUser = DUser(
    name
)

fun DUser.toDBModel(): UserDB = UserDB(
    name
)