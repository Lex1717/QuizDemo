package com.alexeeff.golangpuzzler.puzzler.presentation.ui.adapter

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import com.alexeeff.golangpuzzler.core.ui.adapter.AbstractDelegatesAdapter
import com.alexeeff.golangpuzzler.puzzler.domain.entity.course.Course
import com.alexeeff.golangpuzzler.puzzler.presentation.ui.adapter.delegates.BottomLoadingDelegate
import com.alexeeff.golangpuzzler.puzzler.presentation.ui.adapter.delegates.CourseDelegate
import com.alexeeff.golangpuzzler.puzzler.presentation.ui.adapter.model.BottomLoadingItem

/**
 * @author Yaroslav Alexeev
 */
open class CourseListAdapter(
        private val itemClickListener: (id: Int) -> Unit,
        private val nextPageListener: () -> Unit
) : AbstractDelegatesAdapter<Any>() {

    init {
        delegatesManager.apply {
            addDelegate(CourseDelegate(itemClickListener))
            addDelegate(BottomLoadingDelegate())
        }
    }

    fun setItems(list: List<Course>) {
        val oldList = items.toList()

        val diffResult = DiffUtil.calculateDiff(CourseDiffCallback(oldList, list))

        items.clear()
        items.addAll(list)

        diffResult.dispatchUpdatesTo(this)
    }

    fun showBottomLoading(isShown: Boolean) {
        if (items.size == 0) return

        if (isShown && !isBottomLoadingShowed()) {
            items.add(BottomLoadingItem())
            notifyItemInserted(items.size - 1)
        } else if (!isShown && isBottomLoadingShowed()) {
            items.removeAt(items.size - 1)
            notifyItemRemoved(items.size - 1)
        }
    }

    private fun isBottomLoadingShowed(): Boolean {
        if (items.size == 0) return false

        return items[items.size - 1] is BottomLoadingItem
    }

    override fun onBindViewHolder(
            holder: RecyclerView.ViewHolder,
            position: Int,
            payloads: List<*>
    ) {
        super.onBindViewHolder(holder, position, payloads)

        if (position == items.size - 10) nextPageListener()
    }

    class CourseDiffCallback(
            private val oldList: List<Any>,
            private val newList: List<Any>
    ) : DiffUtil.Callback() {

        override fun areItemsTheSame(
                oldItemPosition: Int,
                newItemPosition: Int
        ): Boolean {
            val oldItem = oldList[oldItemPosition]
            val newItem = newList[newItemPosition]
            return (oldItem is Course && newItem is Course
                    && oldItem.courseId == newItem.courseId)
                    || oldItem is BottomLoadingItem && newItem is BottomLoadingItem
        }

        override fun getOldListSize(): Int {
            return oldList.size
        }

        override fun getNewListSize(): Int {
            return newList.size
        }

        override fun areContentsTheSame(
                oldItemPosition: Int,
                newItemPosition: Int
        ): Boolean {
            val oldItem = oldList[oldItemPosition]
            val newItem = newList[newItemPosition]

            val areCourseContentsTheSame =
                    oldItem is Course && newItem is Course
                            && oldItem == newItem

            return areCourseContentsTheSame
                    || oldItem is BottomLoadingItem && newItem is BottomLoadingItem
        }
    }
}

