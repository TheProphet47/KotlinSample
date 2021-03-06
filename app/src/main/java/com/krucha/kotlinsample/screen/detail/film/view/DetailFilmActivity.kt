package com.krucha.kotlinsample.screen.detail.film.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.krucha.kotlinsample.R
import com.krucha.kotlinsample.data.model.Film
import com.krucha.kotlinsample.di.injector
import com.krucha.kotlinsample.screen.detail.film.viewmodel.ActionResult
import com.krucha.kotlinsample.screen.detail.film.viewmodel.DetailFilmViewModel
import com.krucha.kotlinsample.screen.detail.film.viewmodel.DetailScreen

import kotlinx.android.synthetic.main.activity_film_detail.*
import timber.log.Timber

class DetailFilmActivity : AppCompatActivity() {

    companion object {
        const val ARG_IS_OWNER = "isUserOwner"
    }

    private val viewModel: DetailFilmViewModel by lazy {
        ViewModelProvider(this, injector.detailFilmViewModelFactory())[DetailFilmViewModel::class.java]
    }

    val isUserOwner by lazy {
        intent.getBooleanExtra(ARG_IS_OWNER, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_film_detail)
        setSupportActionBar(toolbar)

        val filmId: Long? =
            if (intent.hasExtra(Film.Table.NAME + Film.Field.ID))
                intent.getLongExtra(Film.Table.NAME + Film.Field.ID, 0)
            else null
        viewModel.start(filmId)

        bindViewModel()
        chooseStartMode()
    }

    private fun chooseStartMode() {
        val mode = intent.getSerializableExtra(DetailScreen::class.java.simpleName) as DetailScreen?
        if (mode is DetailScreen.Edit) viewModel.activateEdit() else viewModel.activateView()
    }

    private fun bindViewModel() {
                viewModel.detailScreen.observe(this, Observer {
            val screen = it ?: return@Observer
            Timber.d("Activate ${screen.javaClass.simpleName}")

            if (screen.isFab) detailFab.show() else detailFab.hide()

            when(screen) {
                is DetailScreen.View -> {
                    changeFragment(ViewFilmFragment.newInstance())
                }
                is DetailScreen.Edit -> {
                    changeFragment(EditFilmFragment.newInstance())
                }
            }
        })

        viewModel.actionResult.observe(this, Observer {
            val result = it ?: return@Observer

            when(result) {
                is ActionResult.Update -> {
                    showSnackbar(result.msg)
                    viewModel.activateView()
                }
                is ActionResult.Remove -> finish()
            }
        })
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        menu?.let {
            it.setGroupVisible(R.id.menu_group_delete, isUserOwner)
            it.setGroupVisible(R.id.menu_group_view, false)
            it.setGroupVisible(R.id.menu_group_edit, false)
        }

        return false
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_action_delete -> viewModel.delete()
            R.id.menu_action_view -> viewModel.activateView()
            R.id.menu_action_edit -> viewModel.activateEdit()
        }

        return true
    }

    private fun changeFragment(newFragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, newFragment)
        transaction.commit()
    }

    private fun showSnackbar(@StringRes resId: Int) {
        Snackbar.make(detailFab, getString(resId), Snackbar.LENGTH_LONG).show()
    }
}
