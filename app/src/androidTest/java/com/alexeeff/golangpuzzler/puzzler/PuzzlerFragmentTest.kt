package com.alexeeff.golangpuzzler.puzzler

import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.alexeeff.golangpuzzler.R
import com.alexeeff.golangpuzzler.navigation.presentation.ui.activity.BottomBarNavigationActivity
import com.alexeeff.golangpuzzler.puzzler.presentation.ui.fragment.PuzzlerFragment
import com.alexeeff.golangpuzzler.utils.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


const val COURSE_ID = 110
const val POSITIVE_ANSWER = "Yes! Strings are surrounded by quotes"
const val NEGATIVE_ANSWER = "You were close"

@RunWith(AndroidJUnit4::class)
@LargeTest
class PuzzlerFragmentTest {

    @get:Rule
    var activityRule: ActivityTestRule<BottomBarNavigationActivity> = ActivityTestRule(BottomBarNavigationActivity::class.java)

    @Before
    fun init() {
        activityRule.activity.runOnUiThread {
            val fragment = PuzzlerFragment.newInstance(COURSE_ID)
            activityRule.activity.supportFragmentManager.beginTransaction()
                    .replace(R.id.container, fragment)
                    .commitNow()
        }
    }

    @Test
    fun correct_initial_state() {
        toolbar.hasChildWithText("1/3")
        title.hasText("What are strings surrounded by?")
        optionsGroup.child(0).hasText("Double quotes")
        optionsGroup.child(1).hasText("Parentheses")
        optionsGroup.child(2).hasText("Print()")
        mainButton.enabled(false)
        mainButton.backgroundColor(R.color.hard_dark_gray)
        mainButton.hasText(R.string.solve)
        answerContainer.visible(false)
    }

    @Test
    fun click_option_changes_button_color_and_make_it_enabled() {
        optionsGroup.child(0).click()
        mainButton.enabled(true)
        mainButton.backgroundColor(R.color.colorAccent)
    }

    @Test
    fun click_solve_button_shows_positive_result() {
        selectAnswerAndSolve(0)
        answerContainer.visible(true)
        answerContainer.backgroundColor(R.color.positive_color)
        answerContainer.hasChildWithText(POSITIVE_ANSWER)
        answerContainer.hasChildWithText(R.string.correct_answer)
    }

    @Test
    fun click_solve_button_shows_negative_result() {
        selectAnswerAndSolve(1)
        answerContainer.visible(true)
        answerContainer.backgroundColor(R.color.negative_color)
        answerContainer.hasChildWithText(NEGATIVE_ANSWER)
        answerContainer.hasChildWithText(R.string.incorrect_answer)
    }

    @Test
    fun after_result_shown_button_text_changes() {
        selectAnswerAndSolve(0)
        mainButton.hasText(R.string.next)
    }


    @Test
    fun after_changing_answer_answer_container_disappears() {
        selectAnswerAndSolve(0)
        optionsGroup.child(1).click()
        answerContainer.visible(false)
        mainButton.hasText(R.string.solve)
    }

    @Test
    fun after_changing_answer_result_changes() {
        selectAnswerAndSolve(0)
        answerContainer.visible(true)
        answerContainer.backgroundColor(R.color.positive_color)
        answerContainer.hasChildWithText(POSITIVE_ANSWER)
        answerContainer.hasChildWithText(R.string.correct_answer)
        selectAnswerAndSolve(1)
        answerContainer.visible(true)
        answerContainer.backgroundColor(R.color.negative_color)
        answerContainer.hasChildWithText(NEGATIVE_ANSWER)
        answerContainer.hasChildWithText(R.string.incorrect_answer)
    }

    @Test
    fun after_next_clicked_new_question_appears() {
        optionsGroup.child(0).click()
        mainButton.click()
        mainButton.click()

        toolbar.hasChildWithText("2/3")
        title.hasText("What can you remember about variables and why are they useful in a program")
        optionsGroup.child(0).hasText("They store many values in a single name")
        optionsGroup.child(1).hasText("They set many names to the same value")
        optionsGroup.child(2).hasText("They use the same value in many places")
        optionsGroup.child(3).hasText("They use many values in the same place")
        mainButton.enabled(false)
        mainButton.backgroundColor(R.color.hard_dark_gray)
        mainButton.hasText(R.string.solve)
        answerContainer.visible(false)
    }
}