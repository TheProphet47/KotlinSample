package com.krucha.kotlinsample.screen.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.krucha.kotlinsample.R
import com.krucha.kotlinsample.data.model.Film
import com.krucha.kotlinsample.di.injector
import com.krucha.kotlinsample.screen.auth.login.LoginActivity
import com.krucha.kotlinsample.screen.detail.film.view.DetailFilmActivity
import com.krucha.kotlinsample.utils.setItemClickListener

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(), MainMvp.View {

    @Inject lateinit var filmsRvAdapter: FilmsRvAdapter
    @Inject lateinit var presenter: MainMvp.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        injector.inject(this)
        presenter.view = this

        mainRvFilms.adapter = filmsRvAdapter
        mainRvFilms.layoutManager = LinearLayoutManager(this)
        mainRvFilms.setItemClickListener { _, position ->
            presenter.onFilmItemClick(filmsRvAdapter.films[position].id)
        }

        addFab.setOnClickListener { presenter.onAddFabClick() }
    }

    override fun onResume() {
        super.onResume()
        presenter.subscribe()
    }

    override fun onPause() {
        super.onPause()
        presenter.unsubscribe()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.view = null
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_action_logout -> {
                presenter.logout()
                startActivity(Intent(this, LoginActivity::class.java))
                finishAffinity()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun setFilms(films: List<Film>) {
        filmsRvAdapter.films = films
    }

    override fun showFilmDetails(bundle: Bundle) {
        val intent = Intent(this, DetailFilmActivity::class.java)
        intent.putExtras(bundle)
        startActivity(intent)
    }
}
