package com.krucha.kotlinsample.screen.detail.film.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.krucha.kotlinsample.R

class GenreRvAdapter(context: Context)
    : RecyclerView.Adapter<GenreRvAdapter.ViewHolder>() {

    private val inflater = LayoutInflater.from(context)
    private val internalGenres = ArrayList<String>()

    var genres: List<String>?
        get() = if (internalGenres.size > 0) internalGenres.toList() else null
        set(value) {
            internalGenres.clear()
            value?.let { internalGenres.addAll(it) }
            notifyDataSetChanged()
        }

    fun addGenre(genre: String) {
        internalGenres.add(genre)
        notifyDataSetChanged()
    }

    fun removeGenre(genre: String) {
        internalGenres.remove(genre)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.list_item_genre, parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.genre.text = internalGenres[position]
        holder.buttonRemove.setOnClickListener { removeGenre(internalGenres[position]) }
    }

    override fun getItemCount() = internalGenres.size


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val genre: TextView = itemView.findViewById(R.id.genreListItemText)
        val buttonRemove: ImageButton = itemView.findViewById(R.id.genreListItemButtonRemove)
    }
}