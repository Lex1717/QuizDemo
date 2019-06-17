package com.alexeeff.golangpuzzler.utils

import android.support.test.espresso.Espresso
import android.support.test.espresso.ViewInteraction
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.matcher.ViewMatchers
import android.view.View
import org.hamcrest.Matcher
import org.hamcrest.Matchers

fun ViewInteraction.hasText(text: String) {
    check(ViewAssertions.matches(ViewMatchers.withText(text)))
}

fun ViewInteraction.hasText(resId: Int) {
    check(ViewAssertions.matches(ViewMatchers.withText(resId)))
}

fun ViewInteraction.hasChildWithText(text: String) {
    check(ViewAssertions.matches(ViewMatchers.hasDescendant(ViewMatchers.withText(text))))
}
fun ViewInteraction.hasChildWithText(resId: Int) {
    check(ViewAssertions.matches(ViewMatchers.hasDescendant(ViewMatchers.withText(resId))))
}

fun Matcher<View>.child(position: Int): ViewInteraction {
    return Espresso.onView(childOf(this, position))
}

fun ViewInteraction.enabled(isEnabled: Boolean) {
    if (isEnabled) {
        check(ViewAssertions.matches(ViewMatchers.isEnabled()))
    } else {
        check(ViewAssertions.matches(Matchers.not(ViewMatchers.isEnabled())))
    }
}

fun ViewInteraction.click() {
    perform(ViewActions.click())
}

fun ViewInteraction.backgroundColor(colorRes: Int) {
    check(ViewAssertions.matches(hasBackgroundColor(colorRes)))
}

fun ViewInteraction.visible(isVisible: Boolean) {
    if (isVisible) {
        check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    } else {
        check(ViewAssertions.matches(Matchers.not(ViewMatchers.isDisplayed())))
    }
}