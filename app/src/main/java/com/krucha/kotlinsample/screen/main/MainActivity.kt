package com.krucha.kotlinsample.screen.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.krucha.kotlinsample.R
import com.krucha.kotlinsample.data.model.Film
import com.krucha.kotlinsample.data.repository.FilmRepository
import com.krucha.kotlinsample.data.room.SimpleDatabase
import com.krucha.kotlinsample.features.auth.LoginRepository
import com.krucha.kotlinsample.screen.detail.film.view.DetailFilmActivity
import com.krucha.kotlinsample.screen.detail.film.viewmodel.DetailScreen
import com.krucha.kotlinsample.utils.setItemClickListener

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.coroutines.runBlocking

class MainActivity : AppCompatActivity() {

    lateinit var filmsRvAdapter: FilmsRvAdapter
    lateinit var films: LiveData<List<Film>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val user = LoginRepository.getInstance().user

        films = FilmRepository(dao = SimpleDatabase.getInstance(this).filmDao()).films

        filmsRvAdapter = FilmsRvAdapter(this)
        mainRvFilms.adapter = filmsRvAdapter
        mainRvFilms.layoutManager = LinearLayoutManager(this)
        mainRvFilms.setItemClickListener { _, position ->
            val filmId = filmsRvAdapter.films[position].id
            val intent = Intent(this@MainActivity, DetailFilmActivity::class.java)
            intent.putExtra(Film.Table.NAME + Film.Field.ID, filmId)
            intent.putExtra(DetailScreen::class.java.simpleName, DetailScreen.View())
            intent.putExtra(DetailFilmActivity.ARG_CAN_DELETE, filmsRvAdapter.films[position].userId == user?.id)
            startActivity(intent)
        }

        films.observe(this, Observer {
            filmsRvAdapter.films = it ?: return@Observer
        })

        mainFab.setOnClickListener {
            val newFilmId = runBlocking {
                FilmRepository(dao = SimpleDatabase.getInstance(this@MainActivity).filmDao())
                    .insert(Film(userId = user?.id))
            }

            val intent = Intent(this@MainActivity, DetailFilmActivity::class.java)
            intent.putExtra(Film.Table.NAME + Film.Field.ID, newFilmId)
            intent.putExtra(DetailScreen::class.java.simpleName, DetailScreen.Edit())
            intent.putExtra(DetailFilmActivity.ARG_CAN_DELETE, true)
            startActivity(intent)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
