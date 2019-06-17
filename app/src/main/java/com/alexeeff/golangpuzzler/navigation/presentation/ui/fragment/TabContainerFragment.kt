package com.alexeeff.golangpuzzler.navigation.presentation.ui.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alexeeff.golangpuzzler.R
import com.alexeeff.golangpuzzler.core.cicerone.LocalCiceroneHolder
import com.alexeeff.golangpuzzler.core.interfaces.BackButtonListener
import com.alexeeff.golangpuzzler.di.getComponent
import com.alexeeff.golangpuzzler.navigation.presentation.common.Screens
import com.alexeeff.golangpuzzler.navigation.presentation.common.listeners.ExitListener
import com.alexeeff.golangpuzzler.navigation.presentation.common.tabmanager.NavigationManager
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.android.SupportAppNavigator
import javax.inject.Inject

/**
 * Tab container with internal navigation. Every tab has its own independent navigation state.
 *
 * @author Yaroslav Alexeev
 */

class TabContainerFragment : Fragment(), BackButtonListener {

    private val navigator: Navigator
            by lazy {
                object : SupportAppNavigator(
                        activity,
                        childFragmentManager,
                        R.id.fragmentContainer
                ) {
                    override fun createActivityIntent(
                            context: Context,
                            screenKey: String,
                            data: Any?
                    ): Intent? =
                            navigationManager.getActivityIntentByKey(screenKey, context, data)

                    override fun createFragment(
                            screenKey: String,
                            data: Any?
                    ): Fragment? =
                            navigationManager.getFragmentByKey(screenKey, data)

                    override fun exit() {
                        (activity as? ExitListener)?.onExit()
                    }
                }
            }

    @Inject
    lateinit var ciceroneHolder: LocalCiceroneHolder
    @Inject
    lateinit var navigationManager: NavigationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        activity?.getComponent()?.inject(this)
        super.onCreate(savedInstanceState)
    }

    private fun getCicerone(): Cicerone<Router> {
        return Screens.valueOf(getContainerName()).getCicerone(ciceroneHolder)
    }

    private fun getContainerName(): String {
        return arguments!!.getString(EXTRA_NAME)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_container, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initContainer()
    }

    private fun initContainer() {
        if (childFragmentManager.findFragmentById(R.id.fragmentContainer) != null) return

        val screen = Screens.valueOf(getContainerName())
        getCicerone().router.replaceScreen(screen.initialFragmentTag, 0)
    }

    override fun onResume() {
        super.onResume()
        getCicerone().navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        getCicerone().navigatorHolder.removeNavigator()
        super.onPause()
    }

    override fun onBackPressed(): Boolean {
        val fragment = childFragmentManager.findFragmentById(R.id.fragmentContainer)
        return if (fragment != null
                && fragment is BackButtonListener
                && (fragment as BackButtonListener).onBackPressed()) {
            true
        } else {
            (activity as? ExitListener)?.onExit()
            true
        }
    }

    companion object {
        private const val EXTRA_NAME = "tab_container_extra_name"

        fun newInstance(name: String): TabContainerFragment {
            val fragment = TabContainerFragment()

            val arguments = Bundle()
            arguments.putString(EXTRA_NAME, name)
            fragment.arguments = arguments

            return fragment
        }
    }
}
