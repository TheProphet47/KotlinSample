package com.krucha.kotlinsample.screen.main

import android.view.MotionEvent
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class RecyclerItemClickListener(private val listener: OnItemClickListener?) : RecyclerView.OnItemTouchListener {

    override fun onInterceptTouchEvent(view: RecyclerView, e: MotionEvent): Boolean {
        val childView = view.findChildViewUnder(e.x, e.y)
        if (childView != null && listener != null) {
            listener.onItemClick(childView, view.getChildAdapterPosition(childView))
            return true
        }
        return false
    }

    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {}
    override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {}

    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)
    }
}