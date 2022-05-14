package com.ewind.newsapptest

import androidx.multidex.MultiDexApplication
import com.ewind.newsapptest.di.dataBaseModule
import com.ewind.newsapptest.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin


open class AppNews : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@AppNews)
            modules(listOf(networkModule, dataBaseModule))
        }
    }
}