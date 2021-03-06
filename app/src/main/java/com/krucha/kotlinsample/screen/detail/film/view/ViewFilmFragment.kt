package com.krucha.kotlinsample.screen.detail.film.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.krucha.kotlinsample.R
import com.krucha.kotlinsample.screen.detail.film.model.DisplayedDataView
import kotlinx.android.synthetic.main.fragment_film_view.*
import timber.log.Timber

class ViewFilmFragment : BaseFilmFragment() {

    companion object {
        fun newInstance() = ViewFilmFragment()
    }

    private val isUserOwner: Boolean by lazy {
        (parentActivity as DetailFilmActivity).isUserOwner
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        Timber.d("FilmViewFragment")
        return inflater.inflate(R.layout.fragment_film_view, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        bindViewModel()
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.setGroupVisible(R.id.menu_group_edit, isUserOwner)
    }

    private fun bindViewModel() {
        viewModel.film.observe(this as LifecycleOwner, Observer {film ->
            Timber.d("data received: $film")
            val data = film?.let { DisplayedDataView.fromFilm(it) } ?: return@Observer

            detailName.text = data.name
            detailYear.text = data.year?.toString()
            detailGenre.text = data.genre
            detailDescription.text = data.description
            data.imageUri?.let { detailImage.setImageURI(it) }
        })
    }
}