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

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: TopFilmsViewModel

    private lateinit var topFilmsAdapter: TopFilmsAdapter
    private val layoutManagerRv = LinearLayoutManager(this)

    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding
        get() = _binding ?: throw RuntimeException("ActivityMainBinding is null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupSwipeRefreshLayout()

        viewModel = ViewModelProvider(this)[TopFilmsViewModel::class.java]

        setupObservers()
    }

    private fun setupObservers() {
        // scrolling refresh- load new pages
        viewModel.listTopFilmsItems.observe(this) {
            topFilmsAdapter.submitList(it)
        }
        // swiping refresh
        viewModel.isRefreshing.observe(this) {
            binding.idSwipeRefreshLayout.isRefreshing = it
        }

        viewModel.listOfFavorites.observe(this) {
        }
    }

    private fun setupRecyclerView() {
        topFilmsAdapter = TopFilmsAdapter()
        with(binding.rvFilms) {
            itemAnimator = null
            adapter = topFilmsAdapter
            layoutManager = layoutManagerRv
        }

        setupOnScrollListener()
        setupOnClickListener()
        setupOnLongClickListener()
    }

    private fun setupSwipeRefreshLayout() {
        binding.idSwipeRefreshLayout.setOnRefreshListener {
            viewModel.refreshFilms()
        }
    }

    private fun setupOnScrollListener(){
        binding.rvFilms.setOnScrollChangeListener { _, _, _, _, _ ->
            if ((layoutManagerRv.findLastCompletelyVisibleItemPosition() >
                (topFilmsAdapter.itemCount?.minus(2) ?: 0)
                        && topFilmsAdapter.itemCount > 0 )
            ) {
                viewModel.loadFilms()
            }
        }
    }

    private fun setupOnClickListener() {
        topFilmsAdapter.onFilmItemClickListener = {
            if (isOnePaneMode()) {
                val intent = DetailedInfoActivity.newIntent(this, it.filmId)
                startActivity(intent)
            } else {
                val fragment = DetailedInfoFragment.newInstance(it.filmId)
                supportFragmentManager.beginTransaction()
                    .replace(R.id.detailed_film_container, fragment)
                    .commit()
            }
        }
    }

    private fun setupOnLongClickListener() {
        topFilmsAdapter.onFilmItemLongClickListener = {
            viewModel.changeFavoriteState(it)
            println("DataTopFilmsAdapter = ${viewModel.listTopFilmsItems.value.toString()}")
        }
    }

    private fun isOnePaneMode(): Boolean {
        return binding.detailedFilmContainer == null
    }

}