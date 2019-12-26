package com.krucha.kotlinsample.screen.detail.film.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.krucha.kotlinsample.R
import com.krucha.kotlinsample.data.model.Film
import com.krucha.kotlinsample.data.repository.FilmRepository
import com.krucha.kotlinsample.features.auth.LoginRepository
import com.krucha.kotlinsample.screen.detail.DetailLog
import com.krucha.kotlinsample.screen.detail.film.model.DataForFilm
import com.krucha.kotlinsample.screen.detail.film.model.EditLiveData
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailFilmViewModel @Inject constructor(private val filmRepository: FilmRepository,
                                              private val loginRepository: LoginRepository)
    : ViewModel() {

    private val mTypeScreen = MutableLiveData<DetailScreen>()
    val detailScreen: LiveData<DetailScreen> = mTypeScreen
    private val mActionResult = MutableLiveData<ActionResult>()
    val actionResult: LiveData<ActionResult> = mActionResult

    private val mImageUri = MutableLiveData<Uri>()
    val imageUri: LiveData<Uri> = mImageUri

    private val mFilm = MutableLiveData<Film>()
    private val filmId: Long?
        get() = mFilm.value?.id

    val film: LiveData<Film> = mFilm
    val editData = EditLiveData(mFilm)


    fun start(filmId: Long?) {
        viewModelScope.launch {
            if (filmId == null) {
                val newFilmId = filmRepository.insert(Film(userId = loginRepository.user?.id))
                mFilm.value = filmRepository.getFilm(newFilmId)
                DetailLog.debug("DetailViewModel created new film with Id: $newFilmId")
            } else {
                mFilm.value = filmRepository.getFilm(filmId)
                DetailLog.debug("DetailViewModel started with filmId: $filmId")
            }
        }
    }

    fun save(data: DataForFilm) {
        viewModelScope.launch {
            val filmId = this@DetailFilmViewModel.filmId

            if (filmId != null) {
                val filmEntity = data.toFilm(filmId, loginRepository.user?.id, imageUri.value)
                filmRepository.update(filmEntity)
                mFilm.value = filmEntity

                DetailLog.debug("Update film: $filmEntity")
                mActionResult.value = ActionResult.Update(R.string.detail_msg_film_updated)
            }
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