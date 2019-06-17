package com.alexeeff.golangpuzzler.core.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.hannesdorfmann.adapterdelegates3.AdapterDelegatesManager

/**
 * @author Yaroslav Alexeev
 */
abstract class AbstractDelegatesAdapter<ItemType> : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    /**
     * Holds and manages adapter delegates
     */
    protected val delegatesManager: AdapterDelegatesManager<List<ItemType>> = AdapterDelegatesManager()

    /**
     * Adapter list
     */
    val items: MutableList<ItemType> = ArrayList()

    fun getItem(position: Int): ItemType? {
        return if (position < items.size) {
            items[position]
        } else {
            null
        }
    }

    override fun getItemViewType(position: Int): Int {
        return delegatesManager.getItemViewType(items, position)
    }

    override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
    ): RecyclerView.ViewHolder {
        return delegatesManager.onCreateViewHolder(parent, viewType)
    }

    override fun onBindViewHolder(
            holder: RecyclerView.ViewHolder,
            position: Int
    ) {
        delegatesManager.onBindViewHolder(items, position, holder)
    }

    override fun onBindViewHolder(
            holder: RecyclerView.ViewHolder,
            position: Int,
            payloads: List<*>
    ) {
        delegatesManager.onBindViewHolder(items, position, holder, payloads)
    }

    override fun getItemCount(): Int {
        return items.size
    }
}
