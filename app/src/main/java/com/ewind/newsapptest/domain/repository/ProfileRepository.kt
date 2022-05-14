package com.ewind.newsapptest.domain.repository

import com.ewind.newsapptest.domain.model.DUser
import io.reactivex.Completable
import io.reactivex.Observable

interface ProfileRepository {
    fun getUser(): Observable<DUser>
    fun saveUser(user: DUser): Completable
}