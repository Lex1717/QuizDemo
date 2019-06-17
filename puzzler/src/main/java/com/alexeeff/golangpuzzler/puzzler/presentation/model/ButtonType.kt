package com.alexeeff.golangpuzzler.puzzler.presentation.model

import android.widget.Button
import com.alexeeff.golangpuzzler.puzzler.R

/**
 * States of the button in puzzler
 *
 * @author Yaroslav Alexeev
 */
enum class ButtonType {
    DISABLED {
        override fun applyAppearanceTo(button: Button) {
            button.apply {
                isEnabled = false
                text = button.context.resources.getText(R.string.solve)
                setBackgroundResource(R.drawable.disabled_button)
            }
        }
    }, ENABLED_SOLVE {
        override fun applyAppearanceTo(button: Button) {
            button.apply {
                isEnabled = true
                text = button.context.resources.getText(R.string.solve)
                setBackgroundResource(R.drawable.enabled_button)
            }
        }
    }, ENABLED_NEXT {
        override fun applyAppearanceTo(button: Button) {
            button.apply {
                isEnabled = true
                text = button.context.resources.getText(R.string.next)
                setBackgroundResource(R.drawable.enabled_button)
            }
        }
    };

    abstract fun applyAppearanceTo(button: Button)
}