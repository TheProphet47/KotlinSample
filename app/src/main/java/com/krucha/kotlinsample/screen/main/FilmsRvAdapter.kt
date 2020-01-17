package com.krucha.kotlinsample.screen.main

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.krucha.kotlinsample.R
import com.krucha.kotlinsample.data.model.Film
import javax.inject.Inject

class FilmsRvAdapter @Inject constructor(context: Context) : RecyclerView.Adapter<FilmsRvAdapter.ViewHolder>() {

    private val inflater = LayoutInflater.from(context)
    var films = emptyList<Film>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.list_item_film, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = films[position].name
        holder.genre.text = films[position].genres?.joinToString(", ")
        holder.owner.text = films[position].userId

        val path = films[position].imagePath
        holder.image.setImageURI(path?.let { Uri.parse(path) })
    }

    override fun getItemCount(): Int {
        return films.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.filmListItemName)
        val genre: TextView = itemView.findViewById(R.id.filmListItemGenre)
        val image: ImageView = itemView.findViewById(R.id.filmListItemImage)
        val owner: TextView = itemView.findViewById(R.id.filmListItemOwner)
    }
}