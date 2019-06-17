package com.alexeeff.golangpuzzler.puzzler.presentation.ui.widget

import android.animation.ObjectAnimator
import android.support.v4.content.ContextCompat
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import com.alexeeff.golangpuzzler.core.utils.visible
import com.alexeeff.golangpuzzler.puzzler.R
import kotlinx.android.synthetic.main.view_answer.view.*

/**
 * @author Yaroslav Alexeev
 */
class AnswerViewHolder(private val view: ViewGroup) {

    private val showAnimator = ObjectAnimator.ofFloat(view, View.TRANSLATION_X, -350f, 0f)
            .apply {
                duration = 250
                interpolator = AccelerateDecelerateInterpolator()
            }

    fun show(isCorrect: Boolean, text: String) {

        if (isCorrect) {
            view.answerTitle.setText(R.string.correct_answer)
            view.setBackgroundColor(ContextCompat.getColor(view.context, R.color.positive_color))
        } else {
            view.answerTitle.setText(R.string.incorrect_answer)
            view.setBackgroundColor(ContextCompat.getColor(view.context, R.color.negative_color))
        }

        view.answerText.text = text

        view.visible(true)

        showAnimator.start()
    }

    fun hide() {
        view.visible(false)
    }

    fun onDestroy() {
        showAnimator.cancel()
    }
}