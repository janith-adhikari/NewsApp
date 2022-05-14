package com.ewind.newsapi.domain.repository

import com.ewind.newsapi.domain.model.DUser
import io.reactivex.Completable
import io.reactivex.Observable

interface ProfileRepository {
    fun getUser(): Observable<DUser>
    fun saveUser(user: DUser): Completable
}