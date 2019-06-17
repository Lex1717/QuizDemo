package com.alexeeff.golangpuzzler.core.utils

import android.content.Context
import android.os.Build
import android.support.annotation.LayoutRes
import android.support.v4.content.ContextCompat
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

fun Context.color(colorRes: Int) = ContextCompat.getColor(this, colorRes)

fun View.visible(visible: Boolean) {
    this.visibility = if (visible) View.VISIBLE else View.GONE
}

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}

fun TextView.setHtmlText(text: String) {
    val sequence = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(text, 0)
    } else {
        Html.fromHtml(text)
    }
    this.setText(sequence, TextView.BufferType.SPANNABLE)
}