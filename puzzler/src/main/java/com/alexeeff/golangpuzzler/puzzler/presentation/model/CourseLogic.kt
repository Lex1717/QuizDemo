package com.alexeeff.golangpuzzler.puzzler.presentation.model

import com.alexeeff.golangpuzzler.puzzler.domain.entity.card.Result

/**
 * @author Yaroslav Alexeev
 */
class CourseLogic(
        private val answerChecker: (cardPosition: Int, answer: Int) -> Result,
        private val cardStateController: CardStateController,
        private val viewController: ViewController,
        private val callback: Callback
) {
    var totalCardsCount = 0
    private var currentCardPosition = 0
    private var currentState: CardState = NotAnswered()

    fun chooseOption(optionId: Int) {
        currentState.chooseOption(optionId)
    }

    fun clickButton() {
        currentState.clickButton()
    }

    interface ViewController {
        fun changeButtonAppearance(buttonType: ButtonType)
        fun showAnswer(isCorrect: Boolean, text: String)
        fun hideAnswer()
        fun showCard(position: Int)
    }

    interface Callback {
        fun onCourseFinished()
        fun onQuestionAnswered(isCorrect: Boolean)
    }

    interface CardState {
        fun chooseOption(optionId: Int) {}
        fun clickButton() {}
    }

    private inner class NotAnswered : CardState {
        override fun chooseOption(optionId: Int) {
            currentState = Answered()
            cardStateController.storeSelectedPositionFor(currentCardPosition, optionId)
            viewController.changeButtonAppearance(ButtonType.ENABLED_SOLVE)
        }
    }

    private inner class Answered : CardState {
        override fun chooseOption(optionId: Int) {
            cardStateController.storeSelectedPositionFor(currentCardPosition, optionId)
        }

        override fun clickButton() {
            currentState = ResultShowed()
            val answerId = cardStateController.getSelectedAnswerFor(currentCardPosition)
            val answerResult = answerChecker(currentCardPosition, answerId)
            viewController.showAnswer(answerResult.isCorrect, answerResult.text)
            viewController.changeButtonAppearance(ButtonType.ENABLED_NEXT)
            callback.onQuestionAnswered(answerResult.isCorrect)
        }
    }

    private inner class ResultShowed : CardState {
        override fun chooseOption(optionId: Int) {
            currentState = Answered()
            cardStateController.storeSelectedPositionFor(currentCardPosition, optionId)
            viewController.hideAnswer()
            viewController.changeButtonAppearance(ButtonType.ENABLED_SOLVE)
        }

        override fun clickButton() {
            if (++currentCardPosition < totalCardsCount) {
                currentState = NotAnswered()
                viewController.hideAnswer()
                viewController.showCard(currentCardPosition)
                viewController.changeButtonAppearance(ButtonType.DISABLED)
            } else {
                cardStateController.reset()
                callback.onCourseFinished()
            }
        }
    }

    fun cancel() {
        cardStateController.reset()
    }
}