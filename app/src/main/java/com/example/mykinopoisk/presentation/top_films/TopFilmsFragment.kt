package com.example.mykinopoisk.presentation.top_films

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mykinopoisk.databinding.FragmentPopularBinding
import com.example.mykinopoisk.presentation.TopFilmsViewModel
import com.example.mykinopoisk.presentation.adapter.TopFilmsAdapter
import com.example.mykinopoisk.presentation.detailedinfo.DetailedInfoOpen

class TopFilmsFragment : Fragment() {

    private var topFilmsAdapter = TopFilmsAdapter()

    private val viewModel: TopFilmsViewModel by activityViewModels()
    private val layoutManagerRv = LinearLayoutManager(context)

    private var _binding: FragmentPopularBinding? = null
    private val binding: FragmentPopularBinding
        get() = _binding ?: throw RuntimeException("FragmentDetailedInfoBinding is null")


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPopularBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupSwipeRefreshLayout()
        setupObservers()
    }


    private fun setupObservers() {
        // scrolling refresh- load new pages
        viewModel.listTopFilmsItems.observe(viewLifecycleOwner) {
            topFilmsAdapter.submitList(it)
            println("listTopFilmsItems = ${viewModel.listTopFilmsItems.value?.toString()}")
        }
        // swiping refresh
        viewModel.isRefreshing.observe(viewLifecycleOwner) {
            binding.idSwipeRefreshLayout.isRefreshing = it
        }

        viewModel.listOfFavorites.observe(viewLifecycleOwner) {
        }
    }

    private fun setupRecyclerView() {
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

    private fun setupOnScrollListener() {
        binding.rvFilms.setOnScrollChangeListener { _, _, _, _, _ ->
            if ((layoutManagerRv.findLastCompletelyVisibleItemPosition() >
                        (topFilmsAdapter.itemCount?.minus(2) ?: 0)
                        && topFilmsAdapter.itemCount > 0)
            ) {
                viewModel.loadFilms()
            }
        }
    }

    private fun setupOnClickListener() {
        topFilmsAdapter.onFilmItemClickListener = {
            var detailedInfoOpen: DetailedInfoOpen
            if (context is DetailedInfoOpen) {
                detailedInfoOpen = context as DetailedInfoOpen
                detailedInfoOpen.callDetailedInfoActivityOrFragment(it.filmId)
            }
        }
    }

    private fun setupOnLongClickListener() {
        topFilmsAdapter.onFilmItemLongClickListener = {
            viewModel.changeFavoriteState(it)
            println("DataTopFilmsAdapter = ${viewModel.listTopFilmsItems.value.toString()}")
        }
    }


    companion object {
        fun newInstance(): TopFilmsFragment {
            return TopFilmsFragment()
        }
    }
}