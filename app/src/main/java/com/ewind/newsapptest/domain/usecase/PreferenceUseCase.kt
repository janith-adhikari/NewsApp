package com.ewind.newsapi.domain.usecase

import com.ewind.newsapi.domain.model.Category
import com.ewind.newsapi.domain.repository.PreferenceRepository
import io.reactivex.Observable

class PreferenceUseCase(val preferenceRepository: PreferenceRepository) {
    fun getPreferenceAll(): Observable<List<Category>> = preferenceRepository.getAllPref()
}