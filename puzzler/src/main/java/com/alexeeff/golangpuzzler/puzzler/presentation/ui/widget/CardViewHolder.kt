package com.alexeeff.golangpuzzler.puzzler.presentation.ui.widget

import android.view.ViewGroup
import android.widget.RadioButton
import com.alexeeff.golangpuzzler.core.utils.inflate
import com.alexeeff.golangpuzzler.core.utils.setHtmlText
import com.alexeeff.golangpuzzler.puzzler.R
import com.alexeeff.golangpuzzler.puzzler.domain.entity.card.Card
import kotlinx.android.synthetic.main.view_card.view.*

/**
 * @author Yaroslav Alexeev
 */
class CardViewHolder(
        private val view: ViewGroup,
        private val answerClickListener: (id: Int) -> Unit = {}
) {

    fun bind(card: Card, selectedAnswer: Int) {
        view.cardTitle.setHtmlText(card.title)

        view.optionsGroup.removeAllViews()

        for (item in card.optionsList) {
            val optionItem = view.inflate(R.layout.view_option_item) as RadioButton

            optionItem.text = item.text

            view.optionsGroup.addView(optionItem)

            if (item.id == selectedAnswer) {
                view.optionsGroup.check(optionItem.id)
            }

            optionItem.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) answerClickListener(item.id)
            }

            val lp = optionItem.layoutParams as ViewGroup.MarginLayoutParams
            val topMargin = view.resources.getDimensionPixelSize(R.dimen.standard_indent)
            lp.setMargins(0, topMargin, 0, 0)

        }
    }
}