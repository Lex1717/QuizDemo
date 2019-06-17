package com.alexeeff.golangpuzzler.puzzler.presentation.ui.fragment

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.ViewGroup
import com.alexeeff.golangpuzzler.core.exception.NoInternetConnectionException
import com.alexeeff.golangpuzzler.core.ui.BaseFragment
import com.alexeeff.golangpuzzler.core.ui.adapter.CustomDividerItemDecoration
import com.alexeeff.golangpuzzler.core.ui.widget.EmptyViewHolder
import com.alexeeff.golangpuzzler.core.utils.visible
import com.alexeeff.golangpuzzler.puzzler.R
import com.alexeeff.golangpuzzler.puzzler.di.getComponent
import com.alexeeff.golangpuzzler.puzzler.domain.entity.course.Course
import com.alexeeff.golangpuzzler.puzzler.domain.interactor.GetCourseListInteractor
import com.alexeeff.golangpuzzler.puzzler.presentation.presenter.CourseListFragmentPresenter
import com.alexeeff.golangpuzzler.puzzler.presentation.ui.adapter.CourseListAdapter
import com.alexeeff.golangpuzzler.puzzler.presentation.view.CourseListFragmentView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kotlinx.android.synthetic.main.fragment_course_list.*
import ru.terrakok.cicerone.Router
import javax.inject.Inject

/**
 * @author Yaroslav Alexeev
 */

class CourseListFragment : BaseFragment(), CourseListFragmentView {

    override val layoutRes = R.layout.fragment_course_list

    @Inject
    lateinit var getCourseListInteractor: GetCourseListInteractor
    @Inject
    lateinit var router: Router

    private lateinit var emptyViewHolder: EmptyViewHolder
    private lateinit var adapter: CourseListAdapter

    @InjectPresenter
    lateinit var presenter: CourseListFragmentPresenter

    @ProvidePresenter
    fun provideCourseListFragmentPresenter() =
            CourseListFragmentPresenter(
                    getCourseListInteractor,
                    router
            )

    override fun onCreate(savedInstanceState: Bundle?) {
        activity?.getComponent()?.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initToolbar()
        initRecyclerView()
        initSwipeRefresh()
        emptyViewHolder = EmptyViewHolder(emptyView as ViewGroup) { presenter.onRefresh() }
    }

    private fun initToolbar() {
        toolbar.setTitle(R.string.course_list)
    }

    private fun initRecyclerView() {
        adapter = CourseListAdapter(
                { id -> presenter.onCourseClicked(id) },
                { presenter.nextPage() }
        )
        listRecyclerView.layoutManager = LinearLayoutManager(context!!)
        listRecyclerView.adapter = adapter
        listRecyclerView.addItemDecoration(
                CustomDividerItemDecoration(resources.getDimension(R.dimen.standard_indent))
        )
    }

    private fun initSwipeRefresh() {
        swipeRefreshLayout.apply {
            setOnRefreshListener { presenter.onRefresh() }
            setColorSchemeColors(ContextCompat.getColor(context, R.color.colorAccent))
        }
    }

    override fun showLoading(isShown: Boolean) {
        progressBar.visible(isShown)
        listRecyclerView.visible(!isShown)
    }

    override fun showData(courseList: List<Course>) {
        adapter.setItems(courseList)
    }

    override fun showEmptyView(isShown: Boolean) {
        listRecyclerView.visible(!isShown)
        if (isShown) {
            emptyViewHolder.showEmptyView()
        } else {
            emptyViewHolder.hide()
        }
    }

    override fun showLoadingLabel(isShown: Boolean) {
        swipeRefreshLayout.isRefreshing = isShown
    }

    override fun showBottomLoading(isShown: Boolean) {
        postViewAction {
            adapter.showBottomLoading(isShown)
        }
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

    companion object {
        fun newInstance() = CourseListFragment()
    }
}
