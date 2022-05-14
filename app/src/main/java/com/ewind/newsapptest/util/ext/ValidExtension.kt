package com.ewind.newsapptest.util.ext

import android.util.Patterns

fun String.isValidEmail(): Boolean = this.isNotEmpty() &&
        Patterns.EMAIL_ADDRESS.matcher(this.trim()).matches()

fun String.isValidPhone(): Boolean = this.isNotEmpty() &&
        Patterns.PHONE.matcher(this.trim()).matches()

fun String.isValidPassword(): Boolean {
    var valid: Boolean
    try {
        valid = this.length >= 6
    } catch (e: Exception) {
        valid = false
        e.printStackTrace()
    }

    return valid
}

fun String.matchTo(
    string: String
): Boolean {
    return this == string
}