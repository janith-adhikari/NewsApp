package com.ewind.newsapi.domain.usecase

import com.ewind.newsapi.domain.model.DUser
import com.ewind.newsapi.domain.repository.ProfileRepository
import io.reactivex.Completable
import io.reactivex.Observable

class ProfileUserCase(val profileRepository: ProfileRepository) {
    fun getUser(): Observable<DUser> = profileRepository.getUser()
    fun saveUser(user: DUser): Completable = profileRepository.saveUser(user)
}