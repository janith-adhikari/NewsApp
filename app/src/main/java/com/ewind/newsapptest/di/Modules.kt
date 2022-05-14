package com.ewind.newsapptest.di

import com.ewind.newsapptest.data.repository.NewsRepositoryImpl
import com.ewind.newsapptest.data.repository.PreferenceRepositoryImpl
import com.ewind.newsapptest.data.repository.ProfileRespositoryImpl
import com.ewind.newsapptest.domain.repository.NewsRepository
import com.ewind.newsapptest.domain.repository.PreferenceRepository
import com.ewind.newsapptest.domain.repository.ProfileRepository
import com.ewind.newsapptest.domain.usecase.NewsUseCase
import com.ewind.newsapptest.domain.usecase.PreferenceUseCase
import com.ewind.newsapptest.domain.usecase.ProfileUserCase
import com.ewind.newsapptest.view.main.home.HomeViewModel
import com.ewind.newsapi.presentation.main.news.NewsViewModel
import com.ewind.newsapptest.view.main.profile.ProfileViewModel
import com.ewind.newsapi.presentation.main.topnews.TopNewsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module

fun injectFeature() = loadFeature

private val loadFeature by lazy {
    loadKoinModules(
        listOf(
            viewModelModule,
            useCaseModule,
            repositoryModule
        )
    )
}

val viewModelModule: Module = module {
    viewModel { HomeViewModel() }
    viewModel { NewsViewModel(newsUseCase = get(), preferenceUseCase = get()) }
    viewModel { ProfileViewModel(profileUserCase = get()) }
    viewModel { TopNewsViewModel(topNewsUseCase = get()) }
}

val useCaseModule: Module = module {
    factory { NewsUseCase(newsRepository = get()) }
    factory { PreferenceUseCase(preferenceRepository = get()) }
    factory { ProfileUserCase(profileRepository = get()) }
}

val repositoryModule: Module = module {
    single<NewsRepository> {
        NewsRepositoryImpl(
            newsApi = get()
        )
    }
    single<PreferenceRepository> {
        PreferenceRepositoryImpl(
            databaseClient = get()
        )
    }
    single<ProfileRepository> {
        ProfileRespositoryImpl(
            databaseClient = get()
        )
    }
}