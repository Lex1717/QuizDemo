package com.alexeeff.golangpuzzler.puzzler.presentation.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.ViewGroup
import com.alexeeff.golangpuzzler.core.exception.NoInternetConnectionException
import com.alexeeff.golangpuzzler.core.interfaces.BackButtonListener
import com.alexeeff.golangpuzzler.core.ui.BaseFragment
import com.alexeeff.golangpuzzler.core.ui.widget.EmptyViewHolder
import com.alexeeff.golangpuzzler.core.utils.visible
import com.alexeeff.golangpuzzler.puzzler.R
import com.alexeeff.golangpuzzler.puzzler.di.getComponent
import com.alexeeff.golangpuzzler.puzzler.domain.entity.card.Card
import com.alexeeff.golangpuzzler.puzzler.domain.interactor.CheckCardAnswerInteractor
import com.alexeeff.golangpuzzler.puzzler.domain.interactor.GetCourseInteractor
import com.alexeeff.golangpuzzler.puzzler.domain.interactor.SaveAnswerInteractor
import com.alexeeff.golangpuzzler.puzzler.presentation.model.ButtonType
import com.alexeeff.golangpuzzler.puzzler.presentation.model.CardStateController
import com.alexeeff.golangpuzzler.puzzler.presentation.presenter.PuzzlerFragmentPresenter
import com.alexeeff.golangpuzzler.puzzler.presentation.ui.adapter.CourseViewPagerAdapter
import com.alexeeff.golangpuzzler.puzzler.presentation.ui.widget.AnswerViewHolder
import com.alexeeff.golangpuzzler.puzzler.presentation.view.PuzzlerFragmentView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kotlinx.android.synthetic.main.fragment_puzzler.*
import kotlinx.android.synthetic.main.view_answer.*
import ru.terrakok.cicerone.Router
import javax.inject.Inject

/**
 * @author Yaroslav Alexeev
 */
class PuzzlerFragment : BaseFragment(), PuzzlerFragmentView, BackButtonListener {

    override val layoutRes = R.layout.fragment_puzzler

    @Inject
    lateinit var getCourseInteractor: GetCourseInteractor
    @Inject
    lateinit var checkCardAnswerInteractor: CheckCardAnswerInteractor
    @Inject
    lateinit var saveAnswerInteractor: SaveAnswerInteractor
    @Inject
    lateinit var cardStateController: CardStateController
    @Inject
    lateinit var router: Router

    private lateinit var emptyViewHolder: EmptyViewHolder
    private lateinit var answerViewHolder: AnswerViewHolder
    private lateinit var adapter: CourseViewPagerAdapter

    @InjectPresenter
    lateinit var presenter: PuzzlerFragmentPresenter

    private var courseId: Int = 0

    @ProvidePresenter
    fun providePuzzleFragmentPresenter() =
            PuzzlerFragmentPresenter(
                    courseId,
                    getCourseInteractor,
                    checkCardAnswerInteractor,
                    saveAnswerInteractor,
                    router,
                    cardStateController
            )

    override fun onCreate(savedInstanceState: Bundle?) {
        activity?.getComponent()?.inject(this)
        courseId = arguments?.getInt(EXTRA_COURSE_ID) ?: 0
        super.onCreate(savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        actionButton.setOnClickListener { presenter.onButtonClicked() }
        initToolbar()
        initCardViewPager()
        setHasOptionsMenu(true)
        emptyViewHolder = EmptyViewHolder(emptyView as ViewGroup) { presenter.onErrorButtonClicked() }
        answerViewHolder = AnswerViewHolder(answerContainer)
    }

    private fun initToolbar() {
        toolbar.title = ""
        toolbar.setNavigationIcon(R.drawable.ic_close)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> presenter.onBackButtonClicked()
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun initCardViewPager() {
        adapter = CourseViewPagerAdapter(cardStateController) { id -> presenter.onOptionChoosed(id) }
        cardViewPager.offscreenPageLimit = 1
        cardViewPager.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        answerViewHolder.onDestroy()
    }

    override fun showTitle(title: String) {
        toolbar.title = title
    }

    override fun showLoading(isShown: Boolean) {
        progressBar.visible(isShown)
    }

    override fun showCardView(isShown: Boolean) {
        cardViewPager.visible(isShown)
        actionButton.visible(isShown)
    }

    override fun setCardsData(cardsList: List<Card>) {
        adapter.setCards(cardsList)
    }

    override fun showError(isShown: Boolean, error: Throwable?) {
        if (!isShown || error == null) {
            emptyViewHolder.hide()
            return
        }

        when (error) {
            is NoInternetConnectionException -> emptyViewHolder.showNetworkError()
            else -> emptyViewHolder.showLoadingError()
        }
    }

    override fun changeButtonAppearance(buttonType: ButtonType) {
        buttonType.applyAppearanceTo(actionButton)
    }

    override fun showCard(cardNumber: Int) {
        cardViewPager.setCurrentItem(cardNumber, true)
    }

    override fun showAnswer(isCorrect: Boolean, text: String) {
        answerViewHolder.show(isCorrect, text)
    }

    override fun hideAnswer() {
        answerViewHolder.hide()
    }

    override fun onBackPressed(): Boolean {
        return presenter.onBackButtonClicked()
    }

    companion object {
        fun newInstance(courseId: Int): Fragment {
            val fragment = PuzzlerFragment()
            val bundle = Bundle()
                    .apply {
                        putInt(EXTRA_COURSE_ID, courseId)
                    }

            fragment.arguments = bundle
            return fragment
        }

        private const val EXTRA_COURSE_ID = "extra_course_id"
    }
}
