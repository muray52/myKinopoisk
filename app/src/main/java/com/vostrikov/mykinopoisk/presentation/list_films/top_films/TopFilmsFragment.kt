package com.vostrikov.mykinopoisk.presentation.list_films.top_films

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vostrikov.mykinopoisk.databinding.FragmentPopularBinding
import com.vostrikov.mykinopoisk.presentation.detailedinfo.DetailedInfoOpen
import com.vostrikov.mykinopoisk.presentation.list_films.ListFilmsViewModel
import com.vostrikov.mykinopoisk.presentation.list_films.adapter.TopFilmsAdapter

class TopFilmsFragment : Fragment() {

    private var topFilmsAdapter = TopFilmsAdapter()

    private val viewModel: ListFilmsViewModel by activityViewModels()
    private val layoutManagerRv = LinearLayoutManager(context)

    private var _binding: FragmentPopularBinding? = null
    private val binding: FragmentPopularBinding
        get() = _binding ?: throw RuntimeException("FragmentDetailedInfoBinding is null")
    private var toastMessage: Toast? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPopularBinding.inflate(inflater, container, false)
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
        // scrolling refresh- load new pages
        viewModel.listTopFilms.observe(viewLifecycleOwner) {
            topFilmsAdapter.submitList(it)
        }

        // swiping refresh
        viewModel.isRefreshing.observe(viewLifecycleOwner) {
            binding.idSwipeRefreshLayout.isRefreshing = it
        }

        //toast with errors
        viewModel.errorResponseMessage.observe(viewLifecycleOwner) {
            toastObserve(it)
        }

        //search bar
        viewModel.searchTopFilms.observe(viewLifecycleOwner) {
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
            adapter?.stateRestorationPolicy =
                RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
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
                        (topFilmsAdapter.itemCount.minus(2))
                        && topFilmsAdapter.itemCount > 3)
            ) {
                viewModel.loadFilms()
            }
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
                viewModel.searchTopFilms(mask ?: "")
                return false
            }

        })
    }


    companion object {
        const val FRAGMENT_TAG = "Popular"

        fun newInstance(): TopFilmsFragment {
            return TopFilmsFragment()
        }
    }
}