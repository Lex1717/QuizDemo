package com.alexeeff.golangpuzzler.puzzler.presentation.ui.adapter.delegates

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.alexeeff.golangpuzzler.core.utils.inflate
import com.alexeeff.golangpuzzler.puzzler.R
import com.alexeeff.golangpuzzler.puzzler.presentation.ui.adapter.model.BottomLoadingItem
import com.hannesdorfmann.adapterdelegates3.AdapterDelegate

/**
 * @author Yaroslav Alexeev
 */
class BottomLoadingDelegate : AdapterDelegate<List<Any>>() {

    override fun isForViewType(
            items: List<Any>,
            position: Int
    ): Boolean {
        return items[position] is BottomLoadingItem
    }

    public override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return BottomLoadingViewHolder(parent.inflate(R.layout.list_item_bottom_loading))
    }

    override fun onBindViewHolder(
            items: List<Any>,
            position: Int,
            holder: RecyclerView.ViewHolder,
            payloads: List<Any>
    ) {
    }

    private inner class BottomLoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
