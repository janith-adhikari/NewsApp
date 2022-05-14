package com.ewind.newsapptest.data.source.local.database

import android.content.Context
import android.util.Log
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.ewind.newsapptest.data.source.local.model.PreferencesDB
import com.ewind.newsapptest.util.Constant
import com.ewind.newsapptest.R
import java.util.concurrent.Executors

class DatabaseClient(val context: Context) : RoomDatabase.Callback() {

    private var appDatabase: AppDatabases =
        Room.databaseBuilder(
            context,
            AppDatabases::class.java,
            context.getString(R.string.app_name)
        ).allowMainThreadQueries()
            .addCallback(this)
            .build()

    fun appDatabases(): AppDatabases = appDatabase

    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        Log.e("database", "Create")
        Executors.newSingleThreadScheduledExecutor().execute {
            val list = Constant.PRE_ARRAY.map {
                PreferencesDB(it)
            }
            appDatabase.preferenceDao().insertAll(list)
        }
    }

    override fun onOpen(db: SupportSQLiteDatabase) {
        super.onOpen(db)
        Log.e("database", "Open")
    }
}
