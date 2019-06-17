package com.alexeeff.golangpuzzler.puzzler.presentation.ui.adapter

import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.view.View
import android.view.ViewGroup
import com.alexeeff.golangpuzzler.core.utils.inflate
import com.alexeeff.golangpuzzler.puzzler.R
import com.alexeeff.golangpuzzler.puzzler.domain.entity.card.Card
import com.alexeeff.golangpuzzler.puzzler.presentation.model.CardStateController
import com.alexeeff.golangpuzzler.puzzler.presentation.ui.widget.CardViewHolder

class CourseViewPagerAdapter(
        private val cardStateController: CardStateController,
        private val optionClickListener: (id: Int) -> Unit
) : PagerAdapter() {

    private val cardsList = ArrayList<Card>()

    fun setCards(list: List<Card>) {
        cardsList.clear()
        cardsList.addAll(list)
        notifyDataSetChanged()
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val cardView: ViewGroup = container.inflate(R.layout.view_card) as ViewGroup
        val cardViewHolder =
                CardViewHolder(
                        cardView,
                        optionClickListener
                )

        cardViewHolder.bind(
                cardsList[position],
                cardStateController.getSelectedAnswerFor(position)
        )

        container.addView(cardView)
        return cardView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        (container as ViewPager).removeView(`object` as View)
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount(): Int {
        return cardsList.size
    }
}