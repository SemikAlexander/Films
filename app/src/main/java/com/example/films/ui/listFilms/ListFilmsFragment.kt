package com.example.films.ui.listFilms

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.films.core.ViewState
import com.example.films.databinding.FragmentListFilmsBinding
import com.example.films.services.retrofit.filmsDataClasses.FilmsDataClasses
import com.example.films.ui.adapters.ListFilmsAdapters
import kotlinx.coroutines.DelicateCoroutinesApi
import javax.inject.Inject

class ListFilmsFragment : Fragment() {

    private val filmsAdapter by lazy(LazyThreadSafetyMode.PUBLICATION) {
        ListFilmsAdapters(object : ListFilmsAdapters.OnItemClickListener {
            override fun onItemClick(position: Int) {

            }
        })
    }

    private var _binding: FragmentListFilmsBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModel: ListFilmsViewModel


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

        viewModel.run { actionState.observe({lifecycle}, ::showFilms) }

        binding.rvFilms.adapter = filmsAdapter
        viewModel.getListFilms("ru", 1)
    }

    private fun showFilms(state: ViewState<List<FilmsDataClasses>>) {
        filmsAdapter.submitList(state.data)
    }
}