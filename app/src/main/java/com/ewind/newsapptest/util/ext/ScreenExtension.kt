package com.ewind.newsapptest.util.ext

import android.content.Context
import android.content.res.Resources
import android.util.TypedValue

fun Float.pxToDp(): Float {
    val densityDpi = Resources.getSystem()
        .displayMetrics.densityDpi.toFloat()
    return this / (densityDpi / 160f)
}

fun Int.dpToPx(): Float {
    val density = Resources.getSystem()
        .displayMetrics.density
    return this * density
}

fun Float.dipsToPix(context: Context) = TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_DIP, this, context.resources.displayMetrics
)
