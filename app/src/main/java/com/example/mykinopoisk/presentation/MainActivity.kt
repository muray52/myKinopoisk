package com.example.mykinopoisk.presentation

import android.os.Bundle
import android.util.Log
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
    val binding: ActivityMainBinding
        get() = _binding ?: throw RuntimeException("ActivityMainBinding is null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
//        setupSwipeRefreshLayout()

        viewModel = ViewModelProvider(this)[TopFilmsViewModel::class.java]

        setupObservers()
    }

    private fun setupObservers(){
        // scrolling refresh
        viewModel.listTopFilmsItems.observe(this) {
            topFilmsAdapter.submitList(it)
        }
        // swiping refresh
//        viewModel.isRefreshing.isRefreshingobserve(this) {
//            binding.idSwipeRefreshLayout.isRefreshing = it
//        }

        viewModel.listOfFavorites.observe(this){
        }
    }

    private fun setupRecyclerView() {
        topFilmsAdapter = TopFilmsAdapter()
        with(binding.rvFilms) {
            itemAnimator = null
            adapter = topFilmsAdapter
            layoutManager = layoutManagerRv
            setOnScrollChangeListener { _, _, _, _, _ ->
                if (layoutManagerRv.findLastCompletelyVisibleItemPosition() >= topFilmsAdapter.itemCount?.minus(
                        1
                    ) ?: 0
                ) {
                    val recyclerViewState = layoutManagerRv.onSaveInstanceState();
                    viewModel.loadFilms()
                    layoutManagerRv.onRestoreInstanceState(recyclerViewState)
                }
            }
        }

        setupOnClickListener()
        setupOnLongClickListener()
    }

//    private fun setupSwipeRefreshLayout() {
//        binding.idSwipeRefreshLayout.setOnRefreshListener {
//            viewModel.loadFilms(true)
//        }
//    }

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

    private fun setupOnLongClickListener(){
        topFilmsAdapter.onFilmItemLongClickListener = {
            viewModel.changeFavoriteState(it)
            println("DataTopFilmsAdapter = ${viewModel.listTopFilmsItems.value.toString()}")
        }
    }

    private fun isOnePaneMode(): Boolean {
        return binding.detailedFilmContainer == null
    }

}