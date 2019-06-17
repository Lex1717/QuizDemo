package com.alexeeff.golangpuzzler.puzzler.presentation.presenter

import com.alexeeff.golangpuzzler.core.presenter.BasePresenter
import com.alexeeff.golangpuzzler.core.state.SimpleListLoader
import com.alexeeff.golangpuzzler.puzzler.domain.entity.card.Card
import com.alexeeff.golangpuzzler.puzzler.domain.interactor.CheckCardAnswerInteractor
import com.alexeeff.golangpuzzler.puzzler.domain.interactor.GetCourseInteractor
import com.alexeeff.golangpuzzler.puzzler.domain.interactor.SaveAnswerInteractor
import com.alexeeff.golangpuzzler.puzzler.presentation.facade.screen.PuzzlerScreens
import com.alexeeff.golangpuzzler.puzzler.presentation.model.ButtonType
import com.alexeeff.golangpuzzler.puzzler.presentation.model.CardStateController
import com.alexeeff.golangpuzzler.puzzler.presentation.model.CourseLogic
import com.alexeeff.golangpuzzler.puzzler.presentation.view.PuzzlerFragmentView
import com.arellomobile.mvp.InjectViewState
import ru.terrakok.cicerone.Router

/**
 * @author Yaroslav Alekseev
 */
@InjectViewState
class PuzzlerFragmentPresenter(
        private val courseId: Int,
        private val getCourseInteractor: GetCourseInteractor,
        private val checkCardAnswerInteractor: CheckCardAnswerInteractor,
        private val saveAnswerInteractor: SaveAnswerInteractor,
        private val router: Router,
        cardStateController: CardStateController
) : BasePresenter<PuzzlerFragmentView>() {

    private val listLoader: SimpleListLoader<Card> = SimpleListLoader(
            {
                getCourseInteractor.getCourseList(courseId)
                        .map { container -> container.cardsList }
            },
            object : SimpleListLoader.ViewController<Card> {
                override fun showLoading(isShown: Boolean) {
                    viewState.showLoading(isShown)
                }

                override fun showDataView(isShown: Boolean) {
                    viewState.showCardView(isShown)
                }

                override fun showData(data: List<Card>) {
                    viewState.setCardsData(data)
                }

                override fun showError(isShown: Boolean, error: Throwable?) {
                    viewState.showError(isShown, error)
                }
            }
    )

    private val courseLogic = CourseLogic(
            { cardPosition: Int, answer: Int ->
                val card = listLoader.getData()[cardPosition]
                checkCardAnswerInteractor.checkAnswer(card, answer)
            },
            cardStateController,
            object : CourseLogic.ViewController {
                override fun changeButtonAppearance(buttonType: ButtonType) {
                    viewState.changeButtonAppearance(buttonType)
                }

                override fun showAnswer(isCorrect: Boolean, text: String) {
                    viewState.showAnswer(isCorrect, text)
                }

                override fun hideAnswer() {
                    viewState.hideAnswer()
                }

                override fun showCard(position: Int) {
                    viewState.showCard(position)
                    showTitle(position + 1)
                }
            },
            object : CourseLogic.Callback {
                override fun onCourseFinished() {
                    router.replaceScreen(PuzzlerScreens.RESULT.toString())
                }

                override fun onQuestionAnswered(isCorrect: Boolean) {
                    saveAnswerInteractor.saveAnswer(isCorrect)
                            .subscribe({}, {})
                            .addToComposite()
                }
            }
    )

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        listLoader.onListLoadedListener = { list ->
            courseLogic.totalCardsCount = list.size
            showTitle(1)
        }
        listLoader.loadData()
        viewState.changeButtonAppearance(ButtonType.DISABLED)
    }

    private fun showTitle(currentCard: Int) {
        viewState.showTitle("$currentCard/${listLoader.getData().size}")
    }

    fun onButtonClicked() {
        courseLogic.clickButton()
    }

    fun onOptionChoosed(optionId: Int) {
        courseLogic.chooseOption(optionId)
    }

    fun onErrorButtonClicked() {
        listLoader.loadData()
    }

    fun onBackButtonClicked(): Boolean {
        router.exit()
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        listLoader.cancel()
        courseLogic.cancel()
    }
}