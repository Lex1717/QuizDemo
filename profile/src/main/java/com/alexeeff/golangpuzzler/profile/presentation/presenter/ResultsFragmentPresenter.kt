package com.alexeeff.golangpuzzler.profile.presentation.presenter

import com.alexeeff.golangpuzzler.core.presenter.BasePresenter
import com.alexeeff.golangpuzzler.profile.domain.GetUserResultInteractor
import com.alexeeff.golangpuzzler.profile.presentation.view.ResultsFragmentView
import com.arellomobile.mvp.InjectViewState
import ru.terrakok.cicerone.Router

/**
 * @author Yaroslav Alexeev
 */
@InjectViewState
class ResultsFragmentPresenter(
        private val router: Router,
        private val getUserResultInteractor: GetUserResultInteractor
) : BasePresenter<ResultsFragmentView>() {
    fun loadResults() {
        // Data loading here is simplified
        // For an example of more complex loading logic with different states
        // go to CourseListFragmentPresenter
        getUserResultInteractor.execute()
                .subscribe(
                        { userInfo -> viewState.showResults(userInfo) },
                        {}
                ).addToComposite()
    }

    fun onBackClicked(): Boolean {
        router.exit()
        return true
    }
}