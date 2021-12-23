package com.example.films.ui.infoFilm

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.films.core.CoreModuleDependencies
import com.example.films.core.DaggerLobbyComponent
import com.example.films.core.ViewState
import com.example.films.databinding.FragmentFilmInformationBinding
import com.example.films.services.retrofit.filmsDataClasses.Film
import com.example.films.ui.listFilms.InfoFilmViewModel
import com.google.gson.Gson
import dagger.hilt.android.EntryPointAccessors
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class FilmInformationFragment() : Fragment() {
    private var _binding: FragmentFilmInformationBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModel: InfoFilmViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val coreModuleDependencies = EntryPointAccessors.fromApplication(
            requireContext().applicationContext,
            CoreModuleDependencies::class.java
        )

        DaggerLobbyComponent.factory().create(
            coreComponentDependencies = coreModuleDependencies,
            fragment = this
        )
            .inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilmInformationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.actionState.observe({ lifecycle }, ::showDetailFilm)
        arguments
            ?.getString("film")
            ?.let { Gson().fromJson(it, Film::class.java) }
            ?.let { viewModel.getFilmInfo(it.id) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @SuppressLint("SimpleDateFormat")
    private fun showDetailFilm(state: ViewState<Film>) {
        binding.run {
            if (state.data != null) {
                nameFilm.text = state.data.title
                descriptionFilm.text = state.data.overview
                ratingFilm.rating = state.data.vote_average.toFloat()

                val runtime = Date(state.data.runtime.toLong())
                val format = SimpleDateFormat("HH:mm")
                runtimeFilm.text = format.format(runtime).toString()

                var genresFilm = ""
                for (i in state.data.genres.indices) {
                    genresFilm += if (i != state.data.genres.size - 1)
                        "${state.data.genres[i].name}, "
                    else
                        state.data.genres[i].name
                }

                filmGenres.text = genresFilm

                var companies = ""
                for (i in state.data.production_companies.indices) {
                    companies += if (i != state.data.production_companies.size - 1)
                        "${state.data.production_companies[i].name}, "
                    else
                        state.data.production_companies[i].name
                }

                filmCompanies.text = companies

                var countries = ""
                for (i in state.data.production_countries.indices) {
                    countries += if (i != state.data.production_countries.size - 1)
                        "${state.data.production_countries[i].name}, "
                    else
                        state.data.production_countries[i].name
                }

                filmCountries.text = countries

                var languages = ""
                for (i in state.data.spoken_languages.indices) {
                    languages += if (i != state.data.spoken_languages.size - 1)
                        "${state.data.spoken_languages[i].name}, "
                    else
                        state.data.spoken_languages[i].name
                }

                filmLanguages.text = languages

                var url = "https://image.tmdb.org/t/p/w500${state.data.poster_path}"
                Glide.with(requireContext()).load(url).into(filmLogo)

                url =
                    "https://image.tmdb.org/t/p/w500${state.data.belongs_to_collection.backdrop_path}"
                Glide.with(requireContext()).load(url).into(backdropLogo)
            }
        }
    }
}