package com.alexeeff.golangpuzzler.puzzler.presentation.ui.widget

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent

/**
 * @author Yaroslav Alexeev
 */
class NonSwipableViewPager@JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null)
    : ViewPager(context, attrs) {

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return false
    }
}