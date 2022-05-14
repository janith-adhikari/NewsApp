package com.ewind.newsapptest.util.ext

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.os.Parcelable
import android.util.DisplayMetrics
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresPermission
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import java.io.IOException
import java.nio.charset.Charset

/**
 * Loads content of file from assets as String using UTF-8 charset
 */
fun Context.loadFromAsset(jsonName: String): String? {
    var stream: String? = null
    try {
        val inputStream = this.assets.open(jsonName)
        val size = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()
        stream = String(buffer, Charset.forName("UTF-8"))
    } catch (e: IOException) {
    }
    return stream
}

fun Context.getScreenDim(): DisplayMetrics {
    val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val dm = DisplayMetrics()
    windowManager.defaultDisplay.getMetrics(dm)
    return dm
}

/**
 * Computes status bar height
 */
fun Context.getStatusBarHeight(): Int {
    var result = 0
    val resourceId = this.resources.getIdentifier(
        "status_bar_height", "dimen",
        "android"
    )
    if (resourceId > 0) {
        result = this.resources.getDimensionPixelSize(resourceId)
    }
    return result
}


/**
 * Computes screen height
 */
fun Context.getScreenHeight(): Int {
    var screenHeight = 0
    val wm = this.getSystemService(Context.WINDOW_SERVICE) as? WindowManager
    wm?.let {
        val metrics = DisplayMetrics()
        wm.defaultDisplay.getMetrics(metrics)
        screenHeight = metrics.heightPixels
    }
    return screenHeight
}

/**
 * Convert dp integer to pixel
 */
fun Context.dpToPx(dp: Int): Float {
    val displayMetrics = this.resources.displayMetrics
    return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT)).toFloat()
}

/**
 * Get color from resources
 */
fun Context.getCompatColor(@ColorRes colorInt: Int): Int =
    ContextCompat.getColor(this, colorInt)

/**
 * Get drawable from resources
 */
fun Context.getCompatDrawable(@DrawableRes drawableRes: Int): Drawable? =
    ContextCompat.getDrawable(this, drawableRes)

fun Context.getCompatColorState(@ColorRes colorInt: Int) =
    ContextCompat.getColorStateList(this, colorInt)

/**
 * Start Activity from context
 * */
inline fun <reified T : Activity> Context?.startActivity(func: Intent.() -> Unit) {
    val intent = Intent(this, T::class.java).apply {
        func()
    }
    this?.startActivity(intent)
}

inline fun <reified T : Activity> Activity?.startActivityForResult(
    result: Int,
    func: Intent.() -> Unit
) {
    val intent = Intent(this, T::class.java).apply {
        func()
    }
    this?.startActivityForResult(intent, result)
}

inline fun <reified T : Activity> Fragment?.startActivityForResult(
    result: Int,
    func: Intent.() -> Unit
) {
    val intent = Intent(this?.context, T::class.java).apply {
        func()
    }
    this?.startActivityForResult(intent, result)
}

/**
 * Show Short Toast message from string resource
 * */
fun Context?.toastShort(@StringRes textId: Int, duration: Int = Toast.LENGTH_SHORT) =
    this?.let { Toast.makeText(it, textId, duration).show() }


/**
 * Show Long Toast message from string resource
 * */
fun Context?.toastLong(@StringRes textId: Int, duration: Int = Toast.LENGTH_LONG) =
    this?.let { Toast.makeText(it, textId, duration).show() }

/**
 * Show Long Toast message from string
 */
fun Context?.toastLong(text: String, duration: Int = Toast.LENGTH_LONG) =
    this?.let { Toast.makeText(it, text, duration).show() }

/**
 * This function allows add Parcelable to intent which takes class name as Extra's name
 * */
fun Intent?.putParcelable(T: Parcelable) = this?.putExtra(T::class.simpleName, T)


