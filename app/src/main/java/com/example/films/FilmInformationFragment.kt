package com.example.films

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.films.databinding.FragmentFilmInformationBinding
import com.example.films.filmsAPI.API
import com.example.films.filmsDataClasses.filmsDataClasses
import com.squareup.picasso.Picasso
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*


class FilmInformationFragment : Fragment() {
    private var _binding: FragmentFilmInformationBinding? = null

    private val binding get() = _binding!!

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

        binding.apply {
            getFilmInfo(552)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @DelicateCoroutinesApi
    private fun getFilmInfo(idFilm: Int) {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val answer = API.api.getFilmInfo(idFilm).execute()
                launch(Dispatchers.Main) {
                    filmDataLoaded(answer.body())
                }
            } catch (e: Exception) {
                launch(Dispatchers.Main) {
                    filmDataLoaded(null)
                }
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun filmDataLoaded(film: filmsDataClasses?){
        binding.apply {
            if (film != null) {
                nameFilm.text = film.title
                descriptionFilm.text = film.overview
                ratingFilm.rating = film.vote_average.toFloat()

                val runtime = Date(film.runtime.toLong())
                val format = SimpleDateFormat("HH:mm")
                runtimeFilm.text = format.format(runtime).toString()

                var genresFilm = ""
                for (i in film.genres.indices)
                    genresFilm += film.genres[i].name

                filmGenres.text = genresFilm

                var companies = ""
                for (i in film.production_companies.indices)
                    companies += film.production_companies[i].name

                filmCompanies.text = companies

                var url = "https://image.tmdb.org/t/p/w500${film.poster_path}"
                Picasso.with(context).load(url).into(filmLogo)

                url = "https://image.tmdb.org/t/p/w500${film.backdrop_path}"
                Picasso.with(context).load(url).into(backdropLogo)
            } else {
                errorImage.visibility = View.VISIBLE
                filmDetailInformation.visibility = View.INVISIBLE

                Glide.with(requireContext()).load(R.drawable.duck).into(errorImage)
            }
        }
    }
}