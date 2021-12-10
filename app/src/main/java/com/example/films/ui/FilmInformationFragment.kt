package com.example.films.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.films.R
import com.example.films.core.ViewState
import com.example.films.databinding.FragmentFilmInformationBinding
import com.example.films.services.retrofit.filmsDataClasses.FilmsDataClasses
import com.example.films.ui.listFilms.ListFilmsViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


class FilmInformationFragment : Fragment() {
    private var _binding: FragmentFilmInformationBinding? = null

    private val binding get() = _binding!!

    @Inject
    lateinit var viewModel: ListFilmsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilmInformationBinding.inflate(inflater, container, false)
        return binding.root
    }

    @DelicateCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.filmActionState.observe({ lifecycle }, ::showDetailFilm)
        viewModel.getFilmInfo(552)
        /*binding.apply {
            getFilmInfo(551)
        }*/
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @SuppressLint("SimpleDateFormat")
    private fun showDetailFilm(state: ViewState<FilmsDataClasses>) {
        binding.apply {
            if (state.data != null) {
                nameFilm.text = state.data.title
                descriptionFilm.text = state.data.overview
                ratingFilm.rating = state.data.vote_average.toFloat()

                val runtime = Date(state.data.runtime.toLong())
                val format = SimpleDateFormat("HH:mm")
                runtimeFilm.text = format.format(runtime).toString()

                var genresFilm = ""
                for (i in state.data.genres.indices)
                    genresFilm += "${state.data.genres[i].name}\n"

                filmGenres.text = genresFilm

                var companies = ""
                for (i in state.data.production_companies.indices)
                    companies += "${state.data.production_companies[i].name}\n"

                filmCompanies.text = companies

                var url = "https://image.tmdb.org/t/p/w500${state.data.poster_path}"
                Glide.with(requireContext()).load(url).into(filmLogo)

                url =
                    "https://image.tmdb.org/t/p/w500${state.data.belongs_to_collection.backdrop_path}"
                Glide.with(requireContext()).load(url).into(backdropLogo)
            } else {
                errorImage.visibility = View.VISIBLE
                filmDetailInformation.visibility = View.INVISIBLE

                Glide.with(requireContext()).load(R.drawable.duck).into(errorImage)
            }
        }
    }
}