package com.ewind.newsapptest.util.ext

import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions

/**
 * Created by Janith on 24/01/18.
 */
fun ImageView.loadImage(url: String?) {
    Glide.with(this.context)
        .load(url)
        .into(this)
}

fun ImageView.loadDrawable(@DrawableRes drawable: Int) {
    Glide.with(this.context).load(drawable).into(this)
}

fun ImageView.loadImageRound(url: String) =
    Glide.with(this).load(url).apply(RequestOptions.circleCropTransform()).into(this)

fun ImageView.loadImageCenterCrop(url: String?) {
    Glide.with(this.context)
        .load(url)
        .apply(RequestOptions().centerCrop())
        .into(this)
}

fun ImageView.loadImage(url: String?, @DrawableRes placeholder: Int) {
    Glide.with(this.context)
        .load(url)
        .apply(RequestOptions().placeholder(placeholder).centerCrop())
        .into(this)
}

fun ImageView.loadImageCornerRound(
    url: String?,
    radius: Int,
    placeHolder: Int = 0
) {
    Glide.with(this.context)
        .load(url)
        .apply(
            RequestOptions().transform(CenterCrop(), RoundedCorners(radius)).placeholder(
                placeHolder
            )
        ).into(this)
}
