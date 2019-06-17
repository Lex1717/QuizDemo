package com.alexeeff.golangpuzzler.core.ui.widget

import android.view.ViewGroup
import com.alexeeff.golangpuzzler.core.R
import com.alexeeff.golangpuzzler.core.utils.visible
import kotlinx.android.synthetic.main.layout_empty_view.view.*

/**
 * @author Yaroslav Alexeev
 */

class EmptyViewHolder(
        private val view: ViewGroup,
        private val refreshListener: () -> Unit = {}
) {
    private val res = view.resources

    init {
        view.refreshButton.setOnClickListener { refreshListener() }
    }

    fun showLoadingError(message: String? = null) {
        view.emptyImageView.setImageResource(R.drawable.error_icon)
        view.titleTextView.text = res.getText(R.string.empty_view_error)
        view.descriptionTextView.text = message ?: res.getText(R.string.empty_view_error_description)
        view.visible(true)
    }

    fun showNetworkError(message: String? = null) {
        view.emptyImageView.setImageResource(R.drawable.connection_icon)
        view.titleTextView.text = res.getText(R.string.empty_view_error)
        view.descriptionTextView.text = message ?: res.getText(R.string.empty_view_network_error_description)
        view.visible(true)
    }

    fun showEmptyView() {
        view.titleTextView.text = res.getText(R.string.empty_view_default_title)
        view.descriptionTextView.text = res.getText(R.string.empty_view_default_description)
        view.visible(true)
    }

    fun hide() {
        view.visible(false)
    }
}