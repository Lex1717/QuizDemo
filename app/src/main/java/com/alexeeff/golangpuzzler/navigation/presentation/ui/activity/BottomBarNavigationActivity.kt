package com.alexeeff.golangpuzzler.navigation.presentation.ui.activity

import android.os.Bundle
import android.widget.Toast
import com.alexeeff.golangpuzzler.R
import com.alexeeff.golangpuzzler.core.interfaces.BackButtonListener
import com.alexeeff.golangpuzzler.di.getComponent
import com.alexeeff.golangpuzzler.navigation.presentation.common.listeners.ExitListener
import com.alexeeff.golangpuzzler.navigation.presentation.presentation.BottomNavigationPresenter
import com.alexeeff.golangpuzzler.navigation.presentation.presentation.BottomNavigationView
import com.alexeeff.golangpuzzler.navigation.presentation.ui.widget.BottomNavigationViewHolder
import com.alexeeff.golangpuzzler.navigation.presentation.ui.widget.FragmentContainerController
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kotlinx.android.synthetic.main.activity_main.*
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.commands.Back
import ru.terrakok.cicerone.commands.Command
import ru.terrakok.cicerone.commands.Replace
import ru.terrakok.cicerone.commands.SystemMessage
import javax.inject.Inject

/**
 * @author Yaroslav Alexeev
 */

class BottomBarNavigationActivity : MvpAppCompatActivity(), BottomNavigationView, ExitListener {

    private lateinit var bottomNavigationViewHolder: BottomNavigationViewHolder
    private lateinit var fragmentContainerController: FragmentContainerController

    @Inject
    lateinit var navigatorHolder: NavigatorHolder
    @Inject
    lateinit var router: Router
    @InjectPresenter
    lateinit var presenter: BottomNavigationPresenter

    @ProvidePresenter
    fun provideBottomNavigationPresenter() = BottomNavigationPresenter(router)

    private val navigator =
            object : Navigator {
                override fun applyCommands(commands: Array<Command>) {
                    for (command in commands) applyCommand(command)
                }

                private fun applyCommand(command: Command) {
                    when (command) {
                        is Back -> finish()
                        is SystemMessage -> Toast.makeText(
                                this@BottomBarNavigationActivity,
                                command.message,
                                Toast.LENGTH_SHORT
                        ).show()
                        is Replace -> fragmentContainerController.openFragment(command.screenKey)
                    }
                }
            }

    override fun onCreate(savedInstanceState: Bundle?) {
        getComponent().inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomNavigationViewHolder = BottomNavigationViewHolder(bottomNavigationBar) { position -> presenter.onTabClick(position) }

        fragmentContainerController = FragmentContainerController(supportFragmentManager)
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }

    override fun showTab(position: Int) {
        bottomNavigationViewHolder.showTab(position)
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(R.id.container)
        if (fragment != null
                && fragment is BackButtonListener
                && fragment.onBackPressed()) {
            return
        } else {
            presenter.onBackPressed()
        }
    }

    override fun onExit() {
        presenter.onExit()
    }
}
