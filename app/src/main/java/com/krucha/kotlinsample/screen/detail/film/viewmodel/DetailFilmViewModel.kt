package com.krucha.kotlinsample.screen.detail.film.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.krucha.kotlinsample.R
import com.krucha.kotlinsample.data.model.Film
import com.krucha.kotlinsample.data.model.User
import com.krucha.kotlinsample.data.repository.FilmRepository
import com.krucha.kotlinsample.screen.detail.DetailLog
import com.krucha.kotlinsample.screen.detail.film.model.DataForFilm
import com.krucha.kotlinsample.screen.detail.film.model.EditLiveData
import kotlinx.coroutines.launch

class DetailFilmViewModel(private val filmRepository: FilmRepository,
                          private val filmId: Long,
                          private val currentUser: User) : ViewModel() {

    private val mTypeScreen = MutableLiveData<DetailScreen>()
    val detailScreen: LiveData<DetailScreen> = mTypeScreen
    private val mActionResult = MutableLiveData<ActionResult>()
    val actionResult: LiveData<ActionResult> = mActionResult

    private val mImageUri = MutableLiveData<Uri>()
    val imageUri: LiveData<Uri> = mImageUri

    val film: LiveData<Film> = filmRepository.getFilm(filmId)
    val editData = EditLiveData(film)

    fun save(data: DataForFilm) {
        viewModelScope.launch {
            val filmEntity = data.toFilm(filmId, currentUser.id, imageUri.value)
            filmRepository.update(filmEntity)
            DetailLog.debug("Update film: $filmEntity")

            mActionResult.value = ActionResult.Update(R.string.detail_msg_film_updated)
        }
    }

    fun delete() {
        viewModelScope.launch {
            DetailLog.debug("Delete film: ${film.value}")
            filmRepository.delete(film.value)
            mActionResult.value = ActionResult.Remove()
        }
    }

    fun setImage(uri: Uri?) {
        mImageUri.value = uri
    }

    fun activateEdit() {
        mTypeScreen.value = DetailScreen.Edit()
    }

    fun activateView() {
        mTypeScreen.value = DetailScreen.View()
    }
}