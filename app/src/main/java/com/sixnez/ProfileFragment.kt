package com.sixnez

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.sixnez.adapter.FilmAdapter
import com.sixnez.adapter.FilmListener
import com.sixnez.databinding.FragmentProfileBinding
import com.sixnez.model.FilmDetailledDTO
import com.sixnez.model.FilmIdDTO
import com.sixnez.viewmodel.ProfileViewModel
import com.sixnez.viewmodelfactory.ProfileViewModelFactory

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var viewModel: ProfileViewModel
    private lateinit var viewModelFactory: ProfileViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        binding.lifecycleOwner = this

        val application = requireNotNull(this.activity).application
        val viewModelFactory = ProfileViewModelFactory(application)

        viewModel =
            ViewModelProviders.of(
                this, viewModelFactory).get(ProfileViewModel::class.java)

        binding.viewModel = viewModel

        binding.apply {
            tvTitle.text = getString(R.string.profile_title)
        }

        val adapter = FilmAdapter(FilmListener { Film ->
            viewModel.getFilmById(Film.id)

        })
        binding.list.adapter = adapter

        viewModel.films.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        viewModel.film.observe(viewLifecycleOwner, Observer {
            it?.let {
                val activity = activity as MainActivity?
                activity?.changeFragment(FilmDetailsFragment(viewModel.film.value as FilmDetailledDTO, FilmIdDTO(viewModel.id)))
            }
        })

        return binding.root
    }
}