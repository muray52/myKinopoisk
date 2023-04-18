package com.example.mykinopoisk.presentation

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.mykinopoisk.R
import com.example.mykinopoisk.databinding.ActivityDetailedInfoBinding
import com.example.mykinopoisk.databinding.ActivityMainBinding
import com.example.mykinopoisk.databinding.FragmentDetailedInfoBinding
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
        setupRefreshLayout()

        viewModel = ViewModelProvider(this)[TopFilmsViewModel::class.java]

        // scrolling refresh
        viewModel.listTopFilmsItems.observe(this){
            topFilmsAdapter.submitList(it)
        }

        // swiping refresh
        viewModel.isRefreshing.observe(this){
            binding.idSwipeRefreshLayout.isRefreshing = it
        }
    }


    private fun setupRecyclerView(){
        with(binding.rvFilms){
            itemAnimator = null
            topFilmsAdapter = TopFilmsAdapter()
            adapter = topFilmsAdapter
            layoutManager = layoutManagerRv
            setOnScrollChangeListener { _, _, _, _, _ ->
                if(layoutManagerRv.findLastCompletelyVisibleItemPosition() == topFilmsAdapter.itemCount?.minus(1) ?: 0) {
                    val recyclerViewState = layoutManagerRv.onSaveInstanceState();
                    viewModel.loadFilms()
                    layoutManagerRv.onRestoreInstanceState(recyclerViewState)
                }
            }
        }

        setupClickListener(binding.rvFilms)
//        setupLongClickListener()

//        setupSwipeListener(rvShopList)
    }

    private fun setupRefreshLayout(){
        binding.idSwipeRefreshLayout.setOnRefreshListener {
            viewModel.loadFilms(true)
        }
    }

    private fun setupClickListener(rvFilmList: RecyclerView) {
        topFilmsAdapter.onFilmItemClickListener = {
            val intent = DetailedInfoActivity.newIntent(this, it.filmId?: 0)
            startActivity(intent)
        }
    }

    private fun isOnePaneMode(): Boolean {
        return true
    }

}