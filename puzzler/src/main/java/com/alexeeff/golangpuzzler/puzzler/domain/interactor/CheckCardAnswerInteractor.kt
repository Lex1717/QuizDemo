package com.alexeeff.golangpuzzler.puzzler.domain.interactor

import com.alexeeff.golangpuzzler.puzzler.domain.entity.card.Card
import com.alexeeff.golangpuzzler.puzzler.domain.entity.card.Result

/**
 * @author Yaroslav Alexeev
 */
class CheckCardAnswerInteractor {

    fun checkAnswer(card: Card, answerId: Int): Result {
        return card.resultMap[answerId] ?: Result("", false, 0)
    }
}