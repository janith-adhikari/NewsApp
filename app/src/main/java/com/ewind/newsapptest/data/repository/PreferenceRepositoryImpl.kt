package com.ewind.newsapptest.data.repository

import com.ewind.newsapptest.data.source.local.database.DatabaseClient
import com.ewind.newsapptest.domain.model.Category
import com.ewind.newsapptest.domain.model.toViewModel
import com.ewind.newsapptest.domain.repository.PreferenceRepository
import io.reactivex.Observable

class PreferenceRepositoryImpl(private val databaseClient: DatabaseClient) : PreferenceRepository {
    override fun getAllPref(): Observable<List<Category>> =
        databaseClient.appDatabases().preferenceDao().getAll().map {
            it.map { pre -> pre.toViewModel() }
        }
}