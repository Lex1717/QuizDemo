package com.alexeeff.golangpuzzler.puzzler.presentation.view

import com.alexeeff.golangpuzzler.puzzler.domain.entity.course.Course
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

/**
 *
 * @author Yaroslav Alekseev
 */
@StateStrategyType(value = AddToEndSingleStrategy::class)
interface CourseListFragmentView : MvpView{

    fun showLoading(isShown: Boolean)

    fun showData(courseList: List<Course>)

    fun showError(isShown: Boolean, error: Throwable? = null)

    fun showEmptyView(isShown: Boolean)

    fun showLoadingLabel(isShown: Boolean)

    fun showBottomLoading(isShown: Boolean)
}