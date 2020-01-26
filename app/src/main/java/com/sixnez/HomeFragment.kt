package com.sixnez

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.sixnez.databinding.FragmentHomeBinding
import com.sixnez.viewmodel.HomeViewModel
import com.sixnez.viewmodelfactory.HomeViewModelFactory

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var viewModelFactory: HomeViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        binding.lifecycleOwner = this

        val activity = activity as MainActivity?
        val application = requireNotNull(this.activity).application
        val viewModelFactory = HomeViewModelFactory(application)

        viewModel =
            ViewModelProviders.of(
                this, viewModelFactory).get(HomeViewModel::class.java)

        binding.viewModel = viewModel
//


        binding.apply {
            tvTitle.text = getString(R.string.home_title)
            tvWelcome.text = getString(R.string.presentation)
        }

        return binding.root
    }
}