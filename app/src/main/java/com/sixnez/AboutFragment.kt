package com.sixnez

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.sixnez.databinding.FragmentAboutBinding
import com.sixnez.viewmodel.AboutViewModel
import com.sixnez.viewmodelfactory.AboutViewModelFactory

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
            tvAboutPolytech.text = getString(R.string.about_polytech)
            tvAboutProject.text = getString(R.string.about_project)
        }

        return binding.root
    }
}