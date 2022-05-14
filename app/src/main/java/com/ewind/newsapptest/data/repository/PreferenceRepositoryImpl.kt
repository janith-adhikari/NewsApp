package com.ewind.newsapptest.data.repository

import com.ewind.newsapptest.data.source.local.database.DatabaseClient
import com.ewind.newsapi.domain.model.Category
import com.ewind.newsapi.domain.model.toViewModel
import com.ewind.newsapi.domain.repository.PreferenceRepository
import io.reactivex.Observable

class PreferenceRepositoryImpl(private val databaseClient: DatabaseClient) : PreferenceRepository {
    override fun getAllPref(): Observable<List<Category>> =
        databaseClient.appDatabases().preferenceDao().getAll().map {
            it.map { pre -> pre.toViewModel() }
        }
}