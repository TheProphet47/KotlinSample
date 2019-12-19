package com.krucha.kotlinsample.screen.detail.film.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.krucha.kotlinsample.screen.detail.DetailViewModelFactory
import com.krucha.kotlinsample.screen.detail.film.viewmodel.DetailFilmViewModel
import java.lang.Exception

abstract class BaseFilmFragment : Fragment() {

    protected lateinit var parentActivity: FragmentActivity
    protected lateinit var viewModel: DetailFilmViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        parentActivity = activity ?: throw Exception("Invalid Activity")
        viewModel = ViewModelProvider(parentActivity, DetailViewModelFactory(parentActivity.application))[DetailFilmViewModel::class.java]
        setHasOptionsMenu(true)
    }
}