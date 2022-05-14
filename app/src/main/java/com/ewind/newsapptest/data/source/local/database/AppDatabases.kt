package com.ewind.newsapptest.data.source.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ewind.newsapptest.data.source.local.model.PreferencesDB
import com.ewind.newsapptest.data.source.local.model.UserDB

@Database(entities = [UserDB::class, PreferencesDB::class], version = 1)
abstract class AppDatabases : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun preferenceDao(): PreferenceDao
}