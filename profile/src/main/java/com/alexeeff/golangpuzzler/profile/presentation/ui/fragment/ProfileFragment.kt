package com.alexeeff.golangpuzzler.profile.presentation.ui.fragment

import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.View
import com.alexeeff.golangpuzzler.core.ui.BaseFragment
import com.alexeeff.golangpuzzler.profile.R
import com.alexeeff.golangpuzzler.profile.di.getComponent
import com.alexeeff.golangpuzzler.profile.presentation.presenter.ProfileFragmentPresenter
import com.alexeeff.golangpuzzler.profile.presentation.view.ProfileFragmentView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kotlinx.android.synthetic.main.fragment_profile.*
import ru.terrakok.cicerone.Router
import javax.inject.Inject

/**
 * @author Yaroslav Alexeev
 */
class ProfileFragment : BaseFragment(), ProfileFragmentView {

    override val layoutRes = R.layout.fragment_profile

    @Inject
    lateinit var router: Router

    @InjectPresenter
    lateinit var presenter: ProfileFragmentPresenter

    @ProvidePresenter
    fun createPresenter(): ProfileFragmentPresenter {
        return ProfileFragmentPresenter(router)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        activity?.getComponent()?.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar(view)
    }

    private fun initToolbar(view: View) {
        val toolbar: Toolbar = view.findViewById(R.id.toolbar)
        toolbar.title = getString(R.string.about)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        resultsButton.setOnClickListener { presenter.onUsersResultClicked() }
        aboutButton.setOnClickListener { presenter.onAboutClicked() }
    }

    companion object {
        fun newInstance() = ProfileFragment()
    }
}
