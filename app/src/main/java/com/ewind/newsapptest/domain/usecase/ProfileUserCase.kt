package com.ewind.newsapptest.domain.usecase

import com.ewind.newsapptest.domain.model.DUser
import com.ewind.newsapptest.domain.repository.ProfileRepository
import io.reactivex.Completable
import io.reactivex.Observable

class ProfileUserCase(val profileRepository: ProfileRepository) {
    fun getUser(): Observable<DUser> = profileRepository.getUser()
    fun saveUser(user: DUser): Completable = profileRepository.saveUser(user)
}