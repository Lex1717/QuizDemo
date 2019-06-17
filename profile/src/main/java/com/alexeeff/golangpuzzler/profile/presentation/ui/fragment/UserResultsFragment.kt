package com.alexeeff.golangpuzzler.profile.presentation.ui.fragment

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import com.alexeeff.golangpuzzler.config.domain.entity.UserInfo
import com.alexeeff.golangpuzzler.core.interfaces.BackButtonListener
import com.alexeeff.golangpuzzler.core.ui.BaseFragment
import com.alexeeff.golangpuzzler.profile.R
import com.alexeeff.golangpuzzler.profile.di.getComponent
import com.alexeeff.golangpuzzler.profile.domain.GetUserResultInteractor
import com.alexeeff.golangpuzzler.profile.presentation.presenter.ResultsFragmentPresenter
import com.alexeeff.golangpuzzler.profile.presentation.view.ResultsFragmentView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kotlinx.android.synthetic.main.fragment_users_result.*
import ru.terrakok.cicerone.Router
import javax.inject.Inject

/**
 * @author Yaroslav Alexeev
 */
class UserResultsFragment : BaseFragment(), ResultsFragmentView, BackButtonListener {

    override val layoutRes = R.layout.fragment_users_result

    @Inject
    lateinit var router: Router
    @Inject
    lateinit var getUserResultInteractor: GetUserResultInteractor

    @InjectPresenter
    lateinit var presenter: ResultsFragmentPresenter

    @ProvidePresenter
    fun createPresenter(): ResultsFragmentPresenter {
        return ResultsFragmentPresenter(router, getUserResultInteractor)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        activity?.getComponent()?.inject(this)
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar(view)
    }

    private fun initToolbar(view: View) {
        val toolbar: Toolbar = view.findViewById(R.id.toolbar)
        toolbar.title = getString(R.string.results)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        presenter.loadResults()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> presenter.onBackClicked()
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun showResults(userInfo: UserInfo) {
        resultsText.text = getString(R.string.your_result, userInfo.questionAnswered,
                userInfo.questionShowed, userInfo.correctAnswerCount)
    }

    override fun onBackPressed(): Boolean {
        return presenter.onBackClicked()
    }

    companion object {
        fun newInstance() = UserResultsFragment()
    }
}
