package com.krucha.kotlinsample.utils

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.krucha.kotlinsample.screen.main.RecyclerItemClickListener

fun TextInputEditText.afterTextChanged(handler: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            handler(editable.toString())
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
    })
}

fun RecyclerView.setItemClickListener(action: (View, Int) -> Unit) {
    addOnItemTouchListener(RecyclerItemClickListener(
        object : RecyclerItemClickListener.OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                action(view, position)
            }
        })
    )
}