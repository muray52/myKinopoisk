package com.example.mykinopoisk.presentation.list_films

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.mykinopoisk.R
import com.example.mykinopoisk.databinding.ActivityListFilmsBinding
import com.example.mykinopoisk.presentation.detailedinfo.DetailedInfoActivity
import com.example.mykinopoisk.presentation.detailedinfo.DetailedInfoFragment
import com.example.mykinopoisk.presentation.detailedinfo.DetailedInfoOpen
import com.example.mykinopoisk.presentation.list_films.favorite_films.FavoriteFilmsFragment
import com.example.mykinopoisk.presentation.list_films.top_films.TopFilmsFragment

class ListFilmsActivity : AppCompatActivity(), DetailedInfoOpen {

    private lateinit var viewModel: ListFilmsViewModel

    private var _binding: ActivityListFilmsBinding? = null
    private val binding: ActivityListFilmsBinding
        get() = _binding ?: throw RuntimeException("ActivityMainBinding is null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityListFilmsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[ListFilmsViewModel::class.java]
        startPopularFragment()
        onClickButtonListener()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.search_menu, menu)
        return true
    }

    private fun startPopularFragment() {
        binding.idTabName?.text = resources.getString(R.string.pupular_appbar)
        val fragment = TopFilmsFragment.newInstance()
        supportFragmentManager.beginTransaction()
            .replace(R.id.film_list_container, fragment)
            .commit()
    }

    private fun startFavoritesFragment() {
        binding.idTabName?.text = resources.getString(R.string.favorite_appbar)
        val fragment = FavoriteFilmsFragment.newInstance()
        supportFragmentManager.beginTransaction()
            .replace(R.id.film_list_container, fragment)
            .commit()
    }

    private fun onClickButtonListener() {
        binding.buttonPopular.setOnClickListener {
            startPopularFragment()
        }
        binding.buttonFavorites.setOnClickListener {
            startFavoritesFragment()
        }
    }

    private fun isOnePaneMode(): Boolean {
        return binding.detailedFilmContainer == null
    }

    override fun callDetailedInfoActivityOrFragment(filmId: Int) {
        if (isOnePaneMode()) {
            val intent = DetailedInfoActivity.newIntent(this, filmId)
            startActivity(intent)
        } else {
            val fragment = DetailedInfoFragment.newInstance(filmId)
            supportFragmentManager.beginTransaction()
                .replace(R.id.detailed_film_container, fragment)
                .commit()
        }
    }

    companion object {

        fun newIntent(context: Context): Intent {
            val intent = Intent(context, ListFilmsActivity::class.java)
            return intent
        }
    }
}