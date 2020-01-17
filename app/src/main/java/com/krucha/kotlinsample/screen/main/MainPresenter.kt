package com.krucha.kotlinsample.screen.main

import android.os.Bundle
import androidx.lifecycle.LiveData
import com.krucha.kotlinsample.data.model.Film
import com.krucha.kotlinsample.data.repository.FilmRepository
import com.krucha.kotlinsample.features.auth.LoginRepository
import com.krucha.kotlinsample.screen.detail.film.view.DetailFilmActivity
import com.krucha.kotlinsample.screen.detail.film.viewmodel.DetailScreen
import dagger.Lazy
import javax.inject.Inject
import androidx.lifecycle.Observer as Observer1

class MainPresenter @Inject constructor(private val loginRepository: LoginRepository,
                                        filmRepository: FilmRepository)
    : MainMvp.Presenter {

    private val films: LiveData<List<Film>> = filmRepository.films
    private val filmsObserver = Observer1<List<Film>> { view?.setFilms(it) }

    override var view: MainMvp.View? = null

    override fun subscribe() {
        films.observeForever(filmsObserver)
    }

    override fun unsubscribe() {
        films.removeObserver(filmsObserver)
    }

    override fun logout() {
        loginRepository.logout()
    }

    override fun onAddFabClick() {
        val bundle = Bundle()
        bundle.putSerializable(DetailScreen::class.java.simpleName, DetailScreen.Edit())
        bundle.putBoolean(DetailFilmActivity.ARG_IS_OWNER, true)

        view?.showFilmDetails(bundle)
    }

    override fun onFilmItemClick(filmId: Long) {
        val isUserOwner = loginRepository.user?.id == films.value?.first{ it.id == filmId }?.userId

        val bundle = Bundle()
        bundle.putLong(Film.Table.NAME + Film.Field.ID, filmId)
        bundle.putSerializable(DetailScreen::class.java.simpleName, DetailScreen.View())
        bundle.putBoolean(DetailFilmActivity.ARG_IS_OWNER, isUserOwner)

        view?.showFilmDetails(bundle)
    }

}