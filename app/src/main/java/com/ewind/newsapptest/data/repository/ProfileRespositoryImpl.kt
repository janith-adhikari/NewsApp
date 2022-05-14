package com.ewind.newsapptest.data.repository

import com.ewind.newsapptest.data.source.local.database.DatabaseClient
import com.ewind.newsapi.domain.model.DUser
import com.ewind.newsapi.domain.model.toDBModel
import com.ewind.newsapi.domain.model.toViewModel
import com.ewind.newsapi.domain.repository.ProfileRepository
import io.reactivex.Observable

class ProfileRespositoryImpl(val databaseClient: DatabaseClient) : ProfileRepository {
    override fun getUser(): Observable<DUser> =
        databaseClient.appDatabases().userDao().getAll().map {
            it.firstOrNull()?.toViewModel()
        }

    override fun saveUser(user: DUser) =
        databaseClient.appDatabases().userDao().insertUser(user.toDBModel())
}