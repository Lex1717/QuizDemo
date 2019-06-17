package com.alexeeff.golangpuzzler.puzzler.presentation.ui.adapter.delegates

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.alexeeff.golangpuzzler.core.utils.inflate
import com.alexeeff.golangpuzzler.puzzler.R
import com.alexeeff.golangpuzzler.puzzler.domain.entity.course.Course
import com.hannesdorfmann.adapterdelegates3.AdapterDelegate
import kotlinx.android.synthetic.main.list_item_course.view.*

/**
 * @author Yaroslav Alexeev
 */
class CourseDelegate(
        private val onItemClickListener: (id: Int) -> Unit
) : AdapterDelegate<List<Any>>() {

    override fun isForViewType(items: List<Any>, position: Int): Boolean {
        return items[position] is Course
    }

    public override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return CourseViewHolder(parent.inflate(R.layout.list_item_course))
    }

    override fun onBindViewHolder(items: List<Any>, position: Int,
                                  holder: RecyclerView.ViewHolder, payloads: List<Any>) {
        val vh = holder as CourseViewHolder
        val course = items[position] as Course

        vh.bind(course)
    }

    private inner class CourseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(course: Course) {
            with(itemView) {
                header.text = course.header
                subheader.text = course.subheader
                setOnClickListener {
                    onItemClickListener(course.courseId)
                }
            }
        }
    }
}
