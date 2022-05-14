package com.ewind.newsapptest.util.ext

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings
import com.google.gson.Gson

inline fun <reified T : Any> Any.mapTo(): T {
    val gson = Gson()
    val toJson = gson.toJson(this@mapTo)
    return gson.fromJson(toJson, T::class.java)
}

inline fun <reified T : Any> String.jsonStringMapTo(): T =
    Gson().fromJson(this@jsonStringMapTo, T::class.java)


fun Any.toJsonString(): String = Gson().toJson(this@toJsonString)


@SuppressLint("HardwareIds")
fun Context.getDeviceId(): String {
    return Settings.Secure.getString(this.contentResolver, Settings.Secure.ANDROID_ID)
}

fun Context.getDeviceType(): String {
    return "Android"
}