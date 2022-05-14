package com.ewind.newsapptest.util

import android.graphics.Color
import android.text.Html
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.view.ViewTreeObserver
import android.widget.TextView
import androidx.core.text.bold

open class MySpanneble(private val isUnderline: Boolean) : ClickableSpan() {

    override fun updateDrawState(ds: TextPaint) {
        super.updateDrawState(ds)
        ds.isUnderlineText = isUnderline
        ds.linkColor = Color.parseColor("#0080A2")
    }

    override fun onClick(widget: View) {

    }
}

private fun addClickablePartTextViewResizable(
    strSpanned: Spanned, tv: TextView, spanableText: String, viewMore: Boolean
): SpannableStringBuilder {
    val str = strSpanned.toString()
    val ssb = SpannableStringBuilder(strSpanned)
    if (str.contains(spanableText)) {
        ssb.setSpan(object : MySpanneble(false) {
            override fun onClick(widget: View) {
                tv.layoutParams = tv.layoutParams
                tv.setText(tv.tag.toString(), TextView.BufferType.SPANNABLE)
                tv.invalidate()
                if (viewMore) {
                    tv.makeResizable(-1, "See Less", false)
                } else {
                    tv.makeResizable(maxLineTep, "View more", true)
                }
            }
        }, str.indexOf(spanableText), str.indexOf(spanableText).plus(spanableText.length), 0)
    }
    return ssb
}

fun TextView.makeResizable(maxLine: Int, expandText: String, viewMore: Boolean) {

    if (tag == null) tag = text
    if (viewMore) maxLineTep = maxLine

    val vto = viewTreeObserver
    vto.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            val lineEndIndex: Int
            val obs = viewTreeObserver
            obs.removeOnGlobalLayoutListener(this)
            if (lineCount in 0..maxLine) {
                lineEndIndex = layout.getLineEnd(0)
                text = "${text.subSequence(
                    0,
                    lineEndIndex
                )}"
            } else {
                when (maxLine) {
                    0 -> {
                        lineEndIndex = layout.getLineEnd(0)
                        text = SpannableStringBuilder().append(
                            text.subSequence(
                                0,
                                lineEndIndex.minus(expandText.length).plus(1)
                            )
                        )
                            .append("...")
                            .bold { append(expandText) }
                    }
                    in 0..lineCount -> {
                        lineEndIndex = layout.getLineEnd(maxLine.minus(1))
                        text = SpannableStringBuilder().append(
                            text.subSequence(
                                0,
                                lineEndIndex.minus(expandText.length).plus(1)
                            )
                        )
                            .append("...")
                            .bold { append(expandText) }
                    }
                    else -> {
                        lineEndIndex = layout.getLineEnd(layout.lineCount.minus(1))
                        text = SpannableStringBuilder().append(
                            text.subSequence(0, lineEndIndex)
                        )
                            .append("...")
                            .bold { append(expandText) }
                    }
                }
            }
            movementMethod = LinkMovementMethod.getInstance()
            setText(
                addClickablePartTextViewResizable(
                    Html.fromHtml(text.toString()),
                    this@makeResizable,
                    expandText,
                    viewMore
                ), TextView.BufferType.SPANNABLE
            )
        }
    })
}

var maxLineTep: Int = 4