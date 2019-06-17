package com.alexeeff.golangpuzzler.puzzler

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.ViewInteraction
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.view.View
import com.alexeeff.golangpuzzler.R
import com.alexeeff.golangpuzzler.utils.child
import com.alexeeff.golangpuzzler.utils.click
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf

val toolbar: ViewInteraction = onView(withId(R.id.toolbar))
val mainButton: ViewInteraction = onView(withId(R.id.actionButton))
val title: ViewInteraction = onView(allOf(withId(R.id.cardTitle), isDisplayed()))
val optionsGroup: Matcher<View> = allOf(withId(R.id.optionsGroup), isDisplayed())
val answerContainer: ViewInteraction = onView(withId(R.id.answerContainer))

fun selectAnswerAndSolve(position: Int) {
    optionsGroup.child(position).click()
    mainButton.click()
}


