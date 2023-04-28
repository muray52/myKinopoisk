package com.example.mykinopoisk.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mykinopoisk.R
import com.example.mykinopoisk.databinding.ActivityMainBinding
import com.example.mykinopoisk.presentation.adapter.TopFilmsAdapter
import com.example.mykinopoisk.presentation.detailedinfo.DetailedInfoActivity
import com.example.mykinopoisk.presentation.detailedinfo.DetailedInfoFragment
import com.example.mykinopoisk.presentation.detailedinfo.DetailedInfoOpen
import com.example.mykinopoisk.presentation.favorite_films.FavoriteFilmsFragment
import com.example.mykinopoisk.presentation.top_films.TopFilmsFragment

class MainActivity : AppCompatActivity(), DetailedInfoOpen {

    private lateinit var viewModel: TopFilmsViewModel

    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding
        get() = _binding ?: throw RuntimeException("ActivityMainBinding is null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[TopFilmsViewModel::class.java]
        startPopularFragment()
        onClickButtonListener()
    }

    private fun startPopularFragment() {
        val fragment = TopFilmsFragment.newInstance()
        supportFragmentManager.beginTransaction()
            .replace(R.id.film_list_container, fragment)
            .commit()
    }

    private fun startFavoritesFragment() {
        val fragment = FavoriteFilmsFragment.newInstance()
        supportFragmentManager.beginTransaction()
            .replace(R.id.film_list_container, fragment)
            .commit()
    }

    private fun onClickButtonListener() {
        binding.buttonPopular?.setOnClickListener {
            startPopularFragment()
        }
        binding.buttonFavorites?.setOnClickListener {
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
}