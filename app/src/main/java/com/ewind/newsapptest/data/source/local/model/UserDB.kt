package com.ewind.newsapptest.data.source.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserDB(
    @ColumnInfo(name = "name")
    var name: String? = null,
    @ColumnInfo(name = "email")
    var email: String? = null
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 1
}