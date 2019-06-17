package com.alexeeff.golangpuzzler.core.ui.adapter

import android.graphics.Canvas
import android.graphics.Paint
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.alexeeff.golangpuzzler.core.R

/**
 * @author Yaroslav Alexeev
 */
class CustomDividerItemDecoration(
        private val leftInset: Float = 0f,
        private val rightInset: Float = 0f
) : RecyclerView.ItemDecoration() {

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val left = parent.paddingLeft + leftInset
        val right = parent.width - parent.paddingRight - rightInset

        for (i in 0 until parent.childCount - 1) {
            val view = parent.getChildAt(i)

            val lp = view.layoutParams as ViewGroup.MarginLayoutParams

            val dividerLeft = left + lp.leftMargin
            val dividerRight = right - lp.rightMargin
            val bottom = view.bottom.toFloat()
            val paint = Paint()

            paint.color = ContextCompat.getColor(parent.context, R.color.divider_color)
            paint.strokeWidth = view.context.resources.getDimension(R.dimen.divider_thickness)

            c.drawLine(dividerLeft, bottom, dividerRight, bottom, paint)
        }
    }
}