package com.sixnez

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.sixnez.databinding.FragmentFilmDetailsBinding
import com.sixnez.model.FilmDTO
import com.sixnez.model.FilmDetailledDTO
import com.sixnez.viewmodel.FilmDetailsViewModel
import com.sixnez.viewmodelfactory.FilmDetailsViewModelFactory

class FilmDetailsFragment (flm: FilmDetailledDTO) : Fragment() {
    private lateinit var binding: FragmentFilmDetailsBinding
    private lateinit var viewModel: FilmDetailsViewModel
    private lateinit var viewModelFactory: FilmDetailsViewModelFactory
    private var film: FilmDetailledDTO

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_film_details, container, false)
        binding.lifecycleOwner = this

        val application = requireNotNull(this.activity).application
        val viewModelFactory = FilmDetailsViewModelFactory(application,film)

        viewModel =
            ViewModelProviders.of(
                this, viewModelFactory).get(FilmDetailsViewModel::class.java)

        binding.viewModel = viewModel

        binding.apply {
            //
        }

        return binding.root
    }

    init {
        this.film = flm
    }
}