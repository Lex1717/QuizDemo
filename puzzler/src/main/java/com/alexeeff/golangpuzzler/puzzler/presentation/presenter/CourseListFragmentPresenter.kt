package com.alexeeff.golangpuzzler.puzzler.presentation.presenter

import com.alexeeff.golangpuzzler.core.entity.Paginator
import com.alexeeff.golangpuzzler.core.presenter.BasePresenter
import com.alexeeff.golangpuzzler.puzzler.domain.entity.course.Course
import com.alexeeff.golangpuzzler.puzzler.domain.interactor.GetCourseListInteractor
import com.alexeeff.golangpuzzler.puzzler.presentation.facade.screen.PuzzlerScreens
import com.alexeeff.golangpuzzler.puzzler.presentation.view.CourseListFragmentView
import com.arellomobile.mvp.InjectViewState
import ru.terrakok.cicerone.Router

/**
 * @author Yaroslav Alekseev
 */
@InjectViewState
open class CourseListFragmentPresenter(
        private val getCourseListInteractor: GetCourseListInteractor,
        protected val router: Router
) : BasePresenter<CourseListFragmentView>() {

    private val paginator = Paginator(
            { page: Int, size: Int, isForced: Boolean ->
                getCourseListInteractor.loadList(page, size, isForced)
                        .map { it.courseList }
            },
            object : Paginator.ViewController<Course> {
                override fun showLoading(isShown: Boolean) {
                    viewState.showLoading(isShown)
                }

                override fun showData(data: List<Course>) {
                    viewState.showData(data)
                }

                override fun showError(isShown: Boolean, error: Throwable?) {
                    viewState.showError(isShown, error)
                }

                override fun showEmptyView(isShown: Boolean) {
                    viewState.showEmptyView(isShown)
                }

                override fun showRefreshProgress(isShown: Boolean) {
                    viewState.showLoadingLabel(isShown)
                }

                override fun showPageProgress(isShown: Boolean) {
                    viewState.showBottomLoading(isShown)
                }

                override fun showErrorMessage(error: Throwable) {
                    //Haven't implemented for the demo, can be shown as a snackbar or an element
                    //at the bottom of the list.
                }
            },

            object : Paginator.Filter<Course> {
                val set = HashSet<Int>()

                override fun filter(item: Course): Boolean {
                    return set.add(item.courseId)
                }

                override fun clear() {
                    set.clear()
                }
            }
    )

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        paginator.refresh(false)
    }

    fun nextPage() {
        paginator.loadNextPage()
    }

    fun onRefresh() {
        paginator.refresh(true)
    }

    fun onCourseClicked(courseId: Int) {
        router.navigateTo(PuzzlerScreens.PUZZLER.toString(), courseId)
    }

    override fun onDestroy() {
        paginator.cancel()
        super.onDestroy()
    }
}