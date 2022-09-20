package com.ewind.newsapptest.di

import com.ewind.newsapptest.data.repository.NewsRepositoryImpl
import com.ewind.newsapptest.data.repository.PreferenceRepositoryImpl
import com.ewind.newsapptest.data.repository.ProfileRepositoryImpl
import com.ewind.newsapptest.domain.repository.NewsRepository
import com.ewind.newsapptest.domain.repository.PreferenceRepository
import com.ewind.newsapptest.domain.repository.ProfileRepository
import com.ewind.newsapptest.view.main.home.HomeViewModel
import com.ewind.newsapptest.view.main.news.BreakingNewsViewModel
import com.ewind.newsapptest.view.main.news.NewsViewModel
import com.ewind.newsapptest.view.main.profile.ProfileViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module

fun injectFeature() = loadFeature

private val loadFeature by lazy {
    loadKoinModules(
        listOf(
            viewModelModule,
            repositoryModule
        )
    )
}

val viewModelModule: Module = module {
    viewModel { HomeViewModel() }
    viewModel { NewsViewModel(newsRepository = get(), preferenceRepository = get()) }
    viewModel { ProfileViewModel(profileRepository = get()) }
    viewModel { BreakingNewsViewModel(topNewsUseCase = get()) }
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
        ProfileRepositoryImpl(
            databaseClient = get()
        )
    }
}