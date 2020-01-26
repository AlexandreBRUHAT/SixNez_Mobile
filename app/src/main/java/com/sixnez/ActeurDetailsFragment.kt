package com.sixnez

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.sixnez.adapter.FilmActeurAdapter
import com.sixnez.adapter.FilmActeurListener
import com.sixnez.databinding.FragmentActeurDetailsBinding
import com.sixnez.model.ActeurDetailledDTO
import com.sixnez.model.FilmDetailledDTO
import com.sixnez.model.FilmIdDTO
import com.sixnez.viewmodel.ActeurDetailsViewModel
import com.sixnez.viewmodelfactory.ActeurDetailsViewModelFactory

class ActeurDetailsFragment (act: ActeurDetailledDTO) : Fragment() {
    private lateinit var binding: FragmentActeurDetailsBinding
    private lateinit var viewModel: ActeurDetailsViewModel
    private lateinit var viewModelFactory: ActeurDetailsViewModelFactory
    private var acteur: ActeurDetailledDTO

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_acteur_details, container, false)
        binding.lifecycleOwner = this

        val application = requireNotNull(this.activity).application
        val viewModelFactory = ActeurDetailsViewModelFactory(application,acteur)

        viewModel =
            ViewModelProviders.of(
                this, viewModelFactory).get(ActeurDetailsViewModel::class.java)

        binding.viewModel = viewModel

        binding.apply {
            tvMort1.text = getString(R.string.mort)
            tvNaissance1.text = getString(R.string.naissance)
            tvFilms.text =  getString(R.string.films)
            layMort.visibility = View.VISIBLE
            if (acteur.mort == null) {
                layMort.visibility = View.GONE
            } else {
                layMort.visibility = View.VISIBLE
            }
            tvMetier1.text = getString(R.string.metiers)
        }

        val adapter = FilmActeurAdapter(FilmActeurListener { FilmActeur ->
            viewModel.getFilmById(FilmActeur.id)
        })

        binding.list.adapter = adapter

        adapter.submitList(acteur.filmDTOS)

        //Goto film
        viewModel.film.observe(viewLifecycleOwner, Observer {
            it?.let {
                val activity = activity as MainActivity?
                activity?.changeFragment(FilmDetailsFragment(viewModel.film.value as FilmDetailledDTO,
                    FilmIdDTO(viewModel.id)
                ))
            }
        })

        return binding.root
    }

    init {
        this.acteur = act
    }
}