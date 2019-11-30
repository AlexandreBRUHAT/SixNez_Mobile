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
import com.sixnez.databinding.FragmentAboutBinding
import com.sixnez.databinding.FragmentHomeBinding
import com.sixnez.databinding.FragmentLoginBinding
import com.sixnez.viewmodel.AboutViewModel
import com.sixnez.viewmodel.HomeViewModel
import com.sixnez.viewmodel.LoginViewModel
import com.sixnez.viewmodelfactory.AboutViewModelFactory
import com.sixnez.viewmodelfactory.HomeViewModelFactory
import com.sixnez.viewmodelfactory.LoginViewModelFactory

class AboutFragment : Fragment() {
    private lateinit var binding: FragmentAboutBinding
    private lateinit var viewModel: AboutViewModel
    private lateinit var viewModelFactory: AboutViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_about, container, false)
        binding.lifecycleOwner = this

        val application = requireNotNull(this.activity).application
        val viewModelFactory = AboutViewModelFactory(application)

        viewModel =
            ViewModelProviders.of(
                this, viewModelFactory).get(AboutViewModel::class.java)

        binding.viewModel = viewModel

        binding.apply {
            tvTitle.text = getString(R.string.about_title)
            tvAbout.text = getString(R.string.about)
        }

        return binding.root
    }
}