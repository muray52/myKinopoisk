package com.example.mykinopoisk.presentation.detailedinfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mykinopoisk.databinding.FragmentDetailedInfoBinding
import com.squareup.picasso.Picasso

class DetailedInfoFragment : Fragment() {

    private var filmId: Int = 0
    private lateinit var viewModel: DetailedInfoViewModel

    private var _binding: FragmentDetailedInfoBinding? = null
    private val binding: FragmentDetailedInfoBinding
        get() = _binding ?: throw RuntimeException("FragmentDetailedInfoBinding is null")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseParams()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailedInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[DetailedInfoViewModel::class.java]
        viewModel.getDetailedInfo(filmId)
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.detailedInfo.observe(viewLifecycleOwner) {
            with(binding) {
                idAllGenres.text = it.genresString
                idFilmDescription.text = it.description
                idCountries.text = it.countriesString
                idFilmName.text = it.nameRu
                Picasso.get()
                    .load(it.posterUrl)
                    .resize(1000,0)
                    .into(binding.idFilmBigImage)
            }
        }
    }

    private fun parseParams() {
        val args = requireArguments()

        if (!args.containsKey(FILM_ID)) {
            throw RuntimeException("FilmId is empty")
        }
        filmId = args.getInt(FILM_ID, 0)
    }


    companion object {
        private const val FILM_ID = "filmId"

        fun newInstance(filmId: Int): DetailedInfoFragment {
            return DetailedInfoFragment().apply {
                arguments = Bundle().apply {
                    putInt(FILM_ID, filmId)
                }
            }
        }
    }
}