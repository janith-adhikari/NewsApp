package com.ewind.newsapptest.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DSource(
    val id: String?,
    val name: String?
) : Parcelable