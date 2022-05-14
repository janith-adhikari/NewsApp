package com.ewind.newsapptest.di

import com.ewind.newsapptest.BuildConfig
import com.ewind.newsapptest.data.source.local.database.DatabaseClient
import com.ewind.newsapptest.data.source.remote.apis.NewsApi
import com.ewind.newsapptest.util.network.SupportInterceptor
import com.ewind.newsapptest.util.network.createNetworkClient
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit

private val tokenAuthenticator = SupportInterceptor(BuildConfig.API_KEY)

val networkModule: Module = module {
    single { tokenAuthenticator }
    single {
        createNetworkClient(
            androidContext(),
            BuildConfig.API_URL,
            BuildConfig.DEBUG,
            tokenAuthenticator
        )
    }
    single { get<Retrofit>().create(NewsApi::class.java) }
}

val dataBaseModule: Module = module {
    single { DatabaseClient(androidApplication()) }
}