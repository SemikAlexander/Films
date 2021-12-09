package com.example.films.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.films.ui.adapters.ListFilmsAdapters
import com.example.films.databinding.FragmentListFilmsBinding
import com.example.films.services.retrofit.API
import com.example.films.services.filmsDataClasses.filmsDataClasses
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class ListFilmsFragment : Fragment(), ListFilmsAdapters.OnItemClickListener {
    private var _binding: FragmentListFilmsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListFilmsBinding.inflate(inflater, container, false)
        return binding.root
    }

    @DelicateCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            filmsList()
        }
    }

    @DelicateCoroutinesApi
    private fun filmsList() {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val answer = API.api.getPopularFilms(language = "ru", page = 1)
                launch(Dispatchers.Main) { filmDataLoaded(answer) }
            } catch (e: Exception) {
                Log.d("TAG", e.message.toString())
                launch(Dispatchers.Main) { filmDataLoaded(emptyList()) }
            }
        }
    }

    override fun onItemClick(position: Int) {

    }

    @SuppressLint("SimpleDateFormat")
    private fun filmDataLoaded(film: List<filmsDataClasses>){
        binding.apply {
            rvFilms.adapter =
                ListFilmsAdapters(film,this@ListFilmsFragment, requireContext())
        }
    }
}