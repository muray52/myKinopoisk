package com.example.mykinopoisk.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.mykinopoisk.R
import com.example.mykinopoisk.presentation.adapter.TopFilmsAdapter
import com.example.mykinopoisk.presentation.detailedinfo.DetailedInfoActivity
import com.example.mykinopoisk.presentation.detailedinfo.DetailedInfoFragment

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: TopFilmsViewModel

    private lateinit var topFilmsAdapter: TopFilmsAdapter
    private val layoutManagerRv = LinearLayoutManager(this)

    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupRecyclerView()
        setupRefreshLayout()


        viewModel = ViewModelProvider(this)[TopFilmsViewModel::class.java]

        // scrolling refresh
        viewModel.listTopFilmsItems.observe(this){
            topFilmsAdapter.submitList(it)
            Log.d("TEST_SCROLL", "observed")
        }

        // swiping refresh
        viewModel.isRefreshing.observe(this){
            swipeRefreshLayout.isRefreshing = it
        }
    }



    private fun setupRecyclerView(){
        val rvFilmList = findViewById<RecyclerView>(R.id.rv_films)
        with(rvFilmList){
            itemAnimator = null
            topFilmsAdapter = TopFilmsAdapter()
            adapter = topFilmsAdapter
            layoutManager = layoutManagerRv
            setOnScrollChangeListener { _, _, _, _, _ ->
                if(layoutManagerRv.findLastCompletelyVisibleItemPosition() == topFilmsAdapter.itemCount?.minus(1) ?: -1) {
                    val recyclerViewState = layoutManagerRv.onSaveInstanceState();
                    viewModel.loadFilms()
                    layoutManagerRv.onRestoreInstanceState(recyclerViewState)
                }
            }
        }

        setupClickListener(rvFilmList)
//        setupLongClickListener()

//        setupSwipeListener(rvShopList)
    }

    private fun setupRefreshLayout(){
        swipeRefreshLayout = findViewById(R.id.idSwipeRefreshLayout)
        swipeRefreshLayout.setOnRefreshListener {
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