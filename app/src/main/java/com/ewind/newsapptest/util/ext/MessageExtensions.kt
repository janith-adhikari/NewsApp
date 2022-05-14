package com.ewind.newsapptest.util.ext

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

fun Context.showSettingsOpen() {
    Snackbar.make(
        (this as Activity).window.decorView.findViewById(android.R.id.content),
        "Please allow permission from settings.",
        Snackbar.LENGTH_LONG
    ).setAction("Settings") {
        val intent = Intent()
        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        val uri = Uri.fromParts("package", application.packageName, null)
        intent.data = uri
        startActivity(intent)
    }.show()
}

fun String.showToast(context: Context) {
    Toast.makeText(context, this, Toast.LENGTH_SHORT).show()
}