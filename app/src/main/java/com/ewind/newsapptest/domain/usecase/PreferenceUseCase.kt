package com.ewind.newsapptest.domain.usecase

import com.ewind.newsapptest.domain.model.Category
import com.ewind.newsapptest.domain.repository.PreferenceRepository
import io.reactivex.Observable

class PreferenceUseCase(val preferenceRepository: PreferenceRepository) {
    fun getPreferenceAll(): Observable<List<Category>> = preferenceRepository.getAllPref()
}