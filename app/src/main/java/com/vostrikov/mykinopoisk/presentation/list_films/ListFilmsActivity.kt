package com.vostrikov.mykinopoisk.presentation.list_films

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.vostrikov.mykinopoisk.ApplicationMyKinopoisk
import com.vostrikov.mykinopoisk.R
import com.vostrikov.mykinopoisk.databinding.ActivityListFilmsBinding
import com.vostrikov.mykinopoisk.presentation.ViewModelFactory
import com.vostrikov.mykinopoisk.presentation.detailedinfo.DetailedInfoActivity
import com.vostrikov.mykinopoisk.presentation.detailedinfo.DetailedInfoFragment
import com.vostrikov.mykinopoisk.presentation.detailedinfo.DetailedInfoOpen
import com.vostrikov.mykinopoisk.presentation.list_films.favorite_films.FavoriteFilmsFragment
import com.vostrikov.mykinopoisk.presentation.list_films.top_films.TopFilmsFragment
import javax.inject.Inject

class ListFilmsActivity : AppCompatActivity(), DetailedInfoOpen {

    private lateinit var viewModel: ListFilmsViewModel

    private var _binding: ActivityListFilmsBinding? = null
    private val binding: ActivityListFilmsBinding
        get() = _binding ?: throw RuntimeException("ActivityMainBinding is null")

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val component by lazy {
        (application as ApplicationMyKinopoisk).component
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        _binding = ActivityListFilmsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, viewModelFactory)[ListFilmsViewModel::class.java]

        if (savedInstanceState == null) {
            startPopularFragment()
        }
        onClickButtonListener()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.search_menu, menu)
        return true
    }

    private fun startPopularFragment() {
        binding.idTabName?.text = resources.getString(R.string.pupular_appbar)
        val fragment = supportFragmentManager.findFragmentByTag(TopFilmsFragment.FRAGMENT_TAG)
        if (fragment == null) {
            supportFragmentManager.beginTransaction()
                .add(
                    R.id.film_list_container,
                    TopFilmsFragment.newInstance(),
                    TopFilmsFragment.FRAGMENT_TAG
                )
                .commit()
        } else {
            supportFragmentManager.beginTransaction()
                .show(fragment)
                .commit()
        }
        supportFragmentManager.findFragmentByTag(FavoriteFilmsFragment.TAG_FAVORITES)
            ?.let {
                supportFragmentManager.beginTransaction()
                    .hide(it)
                    .commit()

            }
    }

    private fun startFavoritesFragment() {
        binding.idTabName?.text = resources.getString(R.string.favorite_appbar)
        val fragment = supportFragmentManager.findFragmentByTag(FavoriteFilmsFragment.TAG_FAVORITES)
        if (fragment == null) {
            supportFragmentManager.beginTransaction()
                .add(
                    R.id.film_list_container,
                    FavoriteFilmsFragment.newInstance(),
                    FavoriteFilmsFragment.TAG_FAVORITES
                )
                .commit()
        } else {
            supportFragmentManager.beginTransaction()
                .show(fragment)
                .commit()
        }
        supportFragmentManager.findFragmentByTag(TopFilmsFragment.FRAGMENT_TAG)
            ?.let {
                supportFragmentManager.beginTransaction()
                    .hide(it)
                    .commit()
            }

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