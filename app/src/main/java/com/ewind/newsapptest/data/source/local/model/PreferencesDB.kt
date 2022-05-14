package com.ewind.newsapptest.data.source.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PreferencesDB(
    @ColumnInfo(name = "keyword")
    var keyword: String? = null
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}