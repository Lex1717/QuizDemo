package com.alexeeff.golangpuzzler.navigation.presentation.ui.widget

import android.support.v4.content.ContextCompat
import com.alexeeff.golangpuzzler.R
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.ashokvarma.bottomnavigation.BottomNavigationItem

/**
 * @author Yaroslav Alexeev
 */

class BottomNavigationViewHolder(
        private val navigationBar: BottomNavigationBar,
        private val onTabSelectedListener: (position: Int) -> Unit
) {
    init {
        navigationBar
                .addItem(BottomNavigationItem(R.drawable.ic_home, R.string.tab_main))
                .addItem(BottomNavigationItem(R.drawable.ic_courses, R.string.tab_courses))
                .addItem(BottomNavigationItem(R.drawable.ic_person, R.string.tab_profile))
                .initialise()

        navigationBar.backgroundColor =
                ContextCompat.getColor(navigationBar.context, R.color.tabbar_background)
        navigationBar.activeColor = R.color.colorAccent
        navigationBar.inActiveColor = R.color.tabbar_text

        navigationBar.setTabSelectedListener(
                object : BottomNavigationBar.OnTabSelectedListener {
                    override fun onTabSelected(position: Int) {
                        onTabSelectedListener(position)
                    }

                    override fun onTabUnselected(position: Int) {}

                    override fun onTabReselected(position: Int) {}
                }
        )
    }

    fun showTab(position: Int) {
        navigationBar.selectTab(position, false)
    }
}