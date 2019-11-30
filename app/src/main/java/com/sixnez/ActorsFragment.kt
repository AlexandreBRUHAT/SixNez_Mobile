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
import com.sixnez.databinding.FragmentActorsBinding
import com.sixnez.databinding.FragmentLoginBinding
import com.sixnez.viewmodel.ActorsViewModel
import com.sixnez.viewmodel.LoginViewModel
import com.sixnez.viewmodelfactory.ActorsViewModelFactory
import com.sixnez.viewmodelfactory.LoginViewModelFactory

class ActorsFragment : Fragment() {
    private lateinit var binding: FragmentActorsBinding
    private lateinit var viewModel: ActorsViewModel
    private lateinit var viewModelFactory: ActorsViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_actors, container, false)
        binding.lifecycleOwner = this

        val application = requireNotNull(this.activity).application
        val viewModelFactory = ActorsViewModelFactory(application)

        viewModel =
            ViewModelProviders.of(
                this, viewModelFactory).get(ActorsViewModel::class.java)

        binding.viewModel = viewModel

        binding.apply {
            tvTitle.text = getString(R.string.actors_title)
        }

        return binding.root
    }
}