package com.sixnez

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.sixnez.databinding.FragmentFilmsBinding
import com.sixnez.databinding.FragmentLoginBinding
import com.sixnez.viewmodel.FilmsViewModel
import com.sixnez.viewmodel.LoginViewModel
import com.sixnez.viewmodelfactory.FilmsViewModelFactory
import com.sixnez.viewmodelfactory.LoginViewModelFactory

class FilmsFragment : Fragment() {
    private lateinit var binding: FragmentFilmsBinding
    private lateinit var viewModel: FilmsViewModel
    private lateinit var viewModelFactory: FilmsViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_films, container, false)
        binding.lifecycleOwner = this

        val application = requireNotNull(this.activity).application
        val viewModelFactory = FilmsViewModelFactory(application)

        viewModel =
            ViewModelProviders.of(
                this, viewModelFactory).get(FilmsViewModel::class.java)

        binding.viewModel = viewModel

        binding.apply {
            tvTitle.text = getString(R.string.films_title)
        }

        return binding.root
    }
}