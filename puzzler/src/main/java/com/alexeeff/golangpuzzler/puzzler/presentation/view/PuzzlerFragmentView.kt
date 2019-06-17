package com.alexeeff.golangpuzzler.puzzler.presentation.view

import com.alexeeff.golangpuzzler.core.entity.AddToEndSingleTagStrategy
import com.alexeeff.golangpuzzler.puzzler.domain.entity.card.Card
import com.alexeeff.golangpuzzler.puzzler.presentation.model.ButtonType
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

/**
 * @author Yaroslav Alexeev
 */
@StateStrategyType(AddToEndSingleStrategy::class)
interface PuzzlerFragmentView : MvpView {
    
    fun showLoading(isShown: Boolean)

    fun showTitle(title: String)

    fun showCardView(isShown: Boolean)

    fun setCardsData(cardsList: List<Card>)

    fun showError(isShown: Boolean, error: Throwable?)

    fun showCard(cardNumber: Int)

    fun changeButtonAppearance(buttonType: ButtonType)

    @StateStrategyType(value = AddToEndSingleTagStrategy::class, tag = "Answer")
    fun showAnswer(isCorrect: Boolean, text: String)

    @StateStrategyType(value = AddToEndSingleTagStrategy::class, tag = "Answer")
    fun hideAnswer()
}