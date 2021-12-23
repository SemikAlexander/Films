package com.example.films.ui.listFilms

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.films.R
import com.example.films.core.CoreModuleDependencies
import com.example.films.core.DaggerLobbyComponent
import com.example.films.databinding.FragmentListFilmsBinding
import com.example.films.ui.MainActivity
import com.example.films.ui.adapters.FilmsAdapter
import com.google.gson.Gson
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class ListFilmsFragment : Fragment() {

    private val filmsAdapter by lazy(LazyThreadSafetyMode.PUBLICATION) {
        FilmsAdapter {
            findNavController().navigate(R.id.action_listFilmsFragment_to_filmInformationFragment, bundleOf(
                "film" to Gson().toJson(it)
            ))
        }
    }

    private var _binding: FragmentListFilmsBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModel: ListFilmsViewModel

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
        _binding = FragmentListFilmsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        try {
            viewModel.viewModelScope.launch(Dispatchers.Main) {
                viewModel.suggestions.collectLatest {
                    filmsAdapter.submitData(it)

//                    delay(500)
//                    dataBinding.baloonStub.setVisibleOrGone(filmsAdapter.snapshot().isEmpty())
                }
            }
        } catch (e: Exception) {
        }
        binding.run {
            search.doOnTextChanged { text, _, _, _ ->
                viewModel.search = text.toString()
                filmsAdapter.refresh()

                clearText.visibility = View.VISIBLE
            }

            clearText.setOnClickListener {
                search.text.clear()
                clearText.visibility = View.INVISIBLE
            }

            rvFilms.adapter = filmsAdapter
            rvFilms.layoutManager = GridLayoutManager(requireContext(), 3)
        }
    }
}