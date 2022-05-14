package com.ewind.newsapptest.util.ext

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText


/**
 * This extension supports EditText validation such as emails, maximum and minimum length
 * inspired from
 * @see https://proandroiddev.com/easy-edittext-content-validation-with-kotlin-316d835d25b3
 * */

fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    val textWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            afterTextChanged.invoke(s.toString().trim())
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    }
    this.addTextChangedListener(textWatcher)
}

/**
 * You can validate the EditText like
 *
 * et_email.validate("Valid email address required"){ s -> s.isValidEmail() }
 *
 * et_email.validate("Minimum length is 6"){ s-> s.length>=6 }
 *
 * */
fun EditText.validate(message: String, validator: (String) -> Boolean): Boolean {
    this.afterTextChanged {
        this.error = if (validator(it)) null else message
    }
    this.error = if (validator(this.getString())) null else message

    return validator(this.getString())
}

/**
 * @return String value of the EditTextView
 * */
fun EditText.getString(): String {
    return this.text.toString()
}

/**
 * @return Trimmed String value of the EditTextView
 * */
fun EditText.getStringTrim(): String {
    return this.getString().trim()
}

fun EditText.clear() {
    this.text.clear()
    this.validate("") { s -> true }
}