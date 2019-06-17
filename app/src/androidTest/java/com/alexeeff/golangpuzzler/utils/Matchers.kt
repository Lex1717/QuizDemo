package com.alexeeff.golangpuzzler.utils

import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.GradientDrawable
import android.support.test.internal.util.Checks
import android.support.v4.content.ContextCompat
import android.view.View
import android.view.ViewGroup
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher

fun childOf(parentMatcher: Matcher<View>, childPosition: Int): Matcher<View> {
    return object : TypeSafeMatcher<View>() {
        override fun describeTo(description: Description) {
            description.appendText("with $childPosition child view of type parentMatcher")
        }

        override fun matchesSafely(view: View): Boolean {
            if (view.parent !is ViewGroup) {
                return parentMatcher.matches(view.parent)
            }

            val group = view.parent as ViewGroup
            return parentMatcher.matches(view.parent) && group.getChildAt(childPosition) == view
        }
    }
}

fun hasBackgroundColor(colorRes: Int): Matcher<View> {
    Checks.checkNotNull(colorRes)

    return object : TypeSafeMatcher<View>() {
        override fun describeTo(description: Description?) {
            description?.appendText("background color: $colorRes")
        }

        override fun matchesSafely(item: View): Boolean {

            val context = item.context
            val backgroundColor =
                    when {
                        item.background is GradientDrawable -> (item.background as GradientDrawable).color?.defaultColor
                        item.background is ColorDrawable -> (item.background as ColorDrawable).color
                        else -> -1
                    }

            val expectedColor = ContextCompat.getColor(context, colorRes)

            return backgroundColor == expectedColor
        }
    }
}