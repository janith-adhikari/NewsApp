package com.ewind.newsapi.domain.repository

import com.ewind.newsapi.domain.model.Category
import io.reactivex.Observable

interface PreferenceRepository {
    fun getAllPref(): Observable<List<Category>>
}