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
            }
        }
    }
}