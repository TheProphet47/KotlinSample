package com.krucha.kotlinsample.screen.detail.film.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.krucha.kotlinsample.R
import com.krucha.kotlinsample.screen.detail.film.model.DataForFilm
import kotlinx.android.synthetic.main.fragment_film_edit.*
import kotlinx.android.synthetic.main.part_genre.*
import timber.log.Timber

class EditFilmFragment : BaseFilmFragment() {

    lateinit var yearsAdapter: ArrayAdapter<Int>
    lateinit var genreRvAdapter: GenreRvAdapter

    companion object {
        const val GALLERY_REQUEST = 1001
        const val INTENT_IMAGE_TYPE = "image/*"

        fun newInstance(): EditFilmFragment {
            return EditFilmFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        Timber.d("FilmEditFragment")
        return inflater.inflate(R.layout.fragment_film_edit, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        bindUi()
        bindViewModel()
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.setGroupVisible(R.id.menu_group_view, true)
    }

    private fun bindViewModel() {
        viewModel.editData.observe(this as LifecycleOwner, Observer {editData ->
            Timber.d("Data received: $editData")
            val data = editData ?: return@Observer

            detailName.setText(data.name)
            detailDescription.setText(data.description)

            yearsAdapter.clear()
            yearsAdapter.addAll(data.determined.listYear)
            detailSpinnerYear.setSelection(data.determined.listYear.indexOf(data.year))

            genreRvAdapter.genres = data.currentGenre

            data.imageUri?.let { viewModel.setImage(it) }
        })

        viewModel.imageUri.observe(this as LifecycleOwner, Observer {uri ->
            uri?.let { detailImage.setImageURI(it) }
        })
    }

    private fun bindUi() {
        yearsAdapter = ArrayAdapter(parentActivity, android.R.layout.simple_spinner_item, mutableListOf<Int>())
        detailSpinnerYear.adapter = yearsAdapter

        genreRvAdapter = GenreRvAdapter(parentActivity)
        detailRvGenre.adapter = genreRvAdapter
        detailRvGenre.layoutManager = LinearLayoutManager(parentActivity)

        detailButtonAddGenre.setOnClickListener {
            if (detailSpinnerGenre.selectedItemId != AdapterView.INVALID_ROW_ID)
                genreRvAdapter.addGenre(detailSpinnerGenre.selectedItem as String)
        }

        detailButtonChooseImage.setOnClickListener {
            val imagePickerIntent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            imagePickerIntent.type = INTENT_IMAGE_TYPE
            startActivityForResult(imagePickerIntent, GALLERY_REQUEST)
        }

        parentActivity.findViewById<FloatingActionButton>(R.id.detailFab).setOnClickListener {
            val year = if (detailSpinnerYear.selectedItemId != AdapterView.INVALID_ROW_ID)
                detailSpinnerYear.selectedItem as Int else null

            viewModel.save(
                DataForFilm(
                    name = detailName.text.toString(),
                    description = detailDescription.text.toString(),
                    year = year,
                    genre = genreRvAdapter.genres
                )
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)

        if (resultCode == Activity.RESULT_OK && requestCode == GALLERY_REQUEST) {
            val uri = intent?.data
            viewModel.setImage(uri)
        }
    }
}