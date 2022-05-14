package com.ewind.newsapptest.util.ext

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.util.TypedValue
import android.view.Gravity
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.constraintlayout.widget.ConstraintLayout
import com.ewind.newsapptest.R
import com.ewind.newsapptest.util.ext.dpToPx

/*internal fun ActionBar.setActionBar(
  title: String,
  homeBack: Boolean
) {
  this.title = title
  this.elevation = 4f
  this.setDisplayHomeAsUpEnabled(homeBack)
  this.setDisplayShowHomeEnabled(homeBack)
  this.setHomeAsUpIndicator(R.drawable.ic_back)
}*/

/*
fun ActionBar.setActionBar(
    context: Context,
    title: String,
    isHomeUpEnables: Boolean = false,
    isCenterTitle: Boolean = false
) {
    val tv = TextView(context.applicationContext)

    //Create a LayoutParams for TextView
    val lp = ConstraintLayout.LayoutParams(
        ConstraintLayout.LayoutParams.WRAP_CONTENT, // Width of TextView
        ConstraintLayout.LayoutParams.WRAP_CONTENT
    ) // Height of TextView

    // Apply the layout parameters to TextView widget
    tv.layoutParams = lp

    // Set text to display in TextView
    tv.text = title // ActionBar title text

    // Set the text color of TextView to black
    // This line change the ActionBar title text color
    tv.setTextColor(Color.BLACK)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        tv.setTextAppearance(R.style.TextBlackNormalStyle_Size16)
    }
    tv.typeface
    if (isCenterTitle) {
        tv.gravity = Gravity.CENTER_HORIZONTAL
    }
    // Set the TextView text size in dp
    // This will change the ActionBar title text size
    tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 17f)

    // Set the ActionBar display option
    this.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
    this.customView = tv

    this.elevation = 2.dpToPx()
    if (isHomeUpEnables) {
        this.setDisplayHomeAsUpEnabled(true)
        this.setDisplayShowHomeEnabled(true)
        this.setHomeAsUpIndicator(R.drawable.ic_back)
    }
}*/
