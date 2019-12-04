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
import com.sixnez.databinding.FragmentHomeBinding
import com.sixnez.databinding.FragmentLoginBinding
import com.sixnez.viewmodel.HomeViewModel
import com.sixnez.viewmodel.LoginViewModel
import com.sixnez.viewmodelfactory.HomeViewModelFactory
import com.sixnez.viewmodelfactory.LoginViewModelFactory
import kotlinx.android.synthetic.main.fragment_home.*

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
            tvConnectedAs.text = getString(R.string.connected_as, activity?.getUsername())

//            if (activity?.isConnected() != true) {
                tvConnectedAs.visibility = View.GONE
//            }
        }

        return binding.root
    }
}