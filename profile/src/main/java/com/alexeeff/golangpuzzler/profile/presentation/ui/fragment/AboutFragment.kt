package com.alexeeff.golangpuzzler.profile.presentation.ui.fragment

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import com.alexeeff.golangpuzzler.core.interfaces.BackButtonListener
import com.alexeeff.golangpuzzler.core.ui.BaseFragment
import com.alexeeff.golangpuzzler.profile.R
import com.alexeeff.golangpuzzler.profile.di.getComponent
import com.alexeeff.golangpuzzler.profile.presentation.presenter.AboutFragmentPresenter
import com.alexeeff.golangpuzzler.profile.presentation.view.AboutFragmentView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import ru.terrakok.cicerone.Router
import javax.inject.Inject

/**
 * @author Yaroslav Alexeev
 */
class AboutFragment : BaseFragment(), AboutFragmentView, BackButtonListener {

    override val layoutRes = R.layout.fragment_about

    @Inject
    lateinit var router: Router

    @InjectPresenter
    lateinit var presenter: AboutFragmentPresenter

    @ProvidePresenter
    fun createPresenter(): AboutFragmentPresenter {
        return AboutFragmentPresenter(router)
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
        toolbar.title = getString(R.string.about)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> presenter.onBackClicked()
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed(): Boolean {
        return presenter.onBackClicked()
    }

    companion object {
        fun newInstance() = AboutFragment()
    }
}
