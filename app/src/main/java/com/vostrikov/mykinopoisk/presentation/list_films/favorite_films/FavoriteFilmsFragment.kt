package com.vostrikov.mykinopoisk.presentation.list_films.favorite_films

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.vostrikov.mykinopoisk.databinding.FragmentFavoritesBinding
import com.vostrikov.mykinopoisk.presentation.detailedinfo.DetailedInfoOpen
import com.vostrikov.mykinopoisk.presentation.list_films.ListFilmsViewModel
import com.vostrikov.mykinopoisk.presentation.list_films.adapter.TopFilmsAdapter

class FavoriteFilmsFragment : Fragment() {

    private var topFilmsAdapter = TopFilmsAdapter()

    private val viewModel: ListFilmsViewModel by activityViewModels()
    private val layoutManagerRv = LinearLayoutManager(context)

    private var _binding: FragmentFavoritesBinding? = null
    private val binding: FragmentFavoritesBinding
        get() = _binding ?: throw RuntimeException("FragmentFavoritesBinding is null")
    private var toastMessage: Toast? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupSwipeRefreshLayout()
        setupObservers()
        setupSearch()
    }


    private fun setupObservers() {
        // swiping refresh
        viewModel.isRefreshing.observe(viewLifecycleOwner) {
            binding.idSwipeRefreshLayout.isRefreshing = it
        }

        //list of favorites films
        viewModel.listOfFavorites.observe(viewLifecycleOwner) {
            topFilmsAdapter.submitList(it)
        }

        //toast with errors
        viewModel.errorResponseMessage.observe(viewLifecycleOwner) {
            toastObserve(it)
        }

        //search bar
        viewModel.searchFavoriteFilms.observe(viewLifecycleOwner) {
            topFilmsAdapter.submitList(it)
        }
    }

    private fun toastObserve(message: String) {
        toastMessage?.cancel()
        toastMessage = Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT)
        toastMessage?.show()
    }

    private fun setupRecyclerView() {
        with(binding.rvFilms) {
            adapter = topFilmsAdapter
            layoutManager = layoutManagerRv
        }
        setupOnClickListener()
        setupOnLongClickListener()
    }

    private fun setupSwipeRefreshLayout() {
        binding.idSwipeRefreshLayout.setOnRefreshListener {
            viewModel.refreshFavoritesFilms()
        }
    }

    private fun setupOnClickListener() {
        topFilmsAdapter.onFilmItemClickListener = {
            val detailedInfoOpen: DetailedInfoOpen
            if (context is DetailedInfoOpen) {
                detailedInfoOpen = context as DetailedInfoOpen
                detailedInfoOpen.callDetailedInfoActivityOrFragment(it.filmId)
            }
        }
    }

    private fun setupOnLongClickListener() {
        topFilmsAdapter.onFilmItemLongClickListener = {
            viewModel.changeFavoriteState(it)
        }
    }

    private fun setupSearch() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(mask: String?): Boolean {
                viewModel.searchFavoriteFilms(mask ?: "")
                return false
            }

        })
    }


    companion object {
        const val TAG_FAVORITES = "Favorites"

        fun newInstance(): FavoriteFilmsFragment {
            return FavoriteFilmsFragment()
        }
    }
}