package com.ewind.newsapptest.data.source.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ewind.newsapptest.data.source.local.model.UserDB
import io.reactivex.Completable
import io.reactivex.Observable

@Dao
interface UserDao {

    @Query("SELECT * FROM userdb")
    abstract fun getAll(): Observable<List<UserDB>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertUser(userDB: UserDB): Completable

    @Query("DELETE FROM userdb")
    abstract fun delete(): Completable

}