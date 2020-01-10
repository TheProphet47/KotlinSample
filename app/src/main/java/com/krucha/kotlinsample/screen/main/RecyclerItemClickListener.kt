package com.krucha.kotlinsample.screen.main

import android.view.MotionEvent
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class RecyclerItemClickListener(private val listener: OnItemClickListener?) : RecyclerView.OnItemTouchListener {

    var cachedChildView: View? = null

    override fun onInterceptTouchEvent(view: RecyclerView, event: MotionEvent): Boolean {
        val childView = view.findChildViewUnder(event.x, event.y) ?: return false

        when (event.action) {
            MotionEvent.ACTION_DOWN -> cachedChildView = childView
            MotionEvent.ACTION_UP -> {
                if (childView == cachedChildView && listener != null) {
                    listener.onItemClick(childView, view.getChildAdapterPosition(childView))
                    return true
                }
            }
        }

        return false
    }

    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {}
    override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {}

    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)
    }
}