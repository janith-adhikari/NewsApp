package com.ewind.newsapptest.domain.repository

import com.ewind.newsapptest.domain.model.Category
import io.reactivex.Observable

interface PreferenceRepository {
    fun getAllPref(): Observable<List<Category>>
}