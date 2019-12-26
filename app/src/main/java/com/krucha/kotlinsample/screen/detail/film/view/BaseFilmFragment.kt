package com.krucha.kotlinsample.screen.detail.film.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.krucha.kotlinsample.di.injector
import com.krucha.kotlinsample.screen.detail.film.viewmodel.DetailFilmViewModel
import java.lang.Exception

abstract class BaseFilmFragment : Fragment() {

    protected val parentActivity by lazy {
        activity ?: throw Exception("Invalid Activity")
    }

    protected val viewModel: DetailFilmViewModel by lazy {
        ViewModelProvider(parentActivity, parentActivity.injector.detailFilmViewModelFactory())[DetailFilmViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
}