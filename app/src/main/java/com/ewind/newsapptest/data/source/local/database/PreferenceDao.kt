package com.ewind.newsapptest.data.source.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ewind.newsapptest.data.source.local.model.PreferencesDB
import io.reactivex.Observable

@Dao
interface PreferenceDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun insertPref(preferencesDB: PreferencesDB)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertAll(list: List<PreferencesDB>)

    @Query("SELECT * FROM preferencesdb")
    abstract fun getAll(): Observable<List<PreferencesDB>>
}