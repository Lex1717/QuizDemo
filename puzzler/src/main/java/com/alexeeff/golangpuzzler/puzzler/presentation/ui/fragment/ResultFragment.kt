package com.alexeeff.golangpuzzler.puzzler.presentation.ui.fragment

import android.os.Bundle
import com.alexeeff.golangpuzzler.core.interfaces.BackButtonListener
import com.alexeeff.golangpuzzler.core.ui.BaseFragment
import com.alexeeff.golangpuzzler.puzzler.R
import com.alexeeff.golangpuzzler.puzzler.di.getComponent
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class ResultFragment : BaseFragment(), BackButtonListener {

    override val layoutRes = R.layout.fragment_result

    @Inject
    lateinit var router: Router


    override fun onCreate(savedInstanceState: Bundle?) {
        activity?.getComponent()?.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onBackPressed(): Boolean {
        router.exit()
        return true
    }

    companion object {
        fun newInstance() = ResultFragment()
    }
}
