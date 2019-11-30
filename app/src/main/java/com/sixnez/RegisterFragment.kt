package com.sixnez

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.sixnez.databinding.FragmentRegisterBinding
import com.sixnez.viewmodel.RegisterViewModel
import com.sixnez.viewmodelfactory.RegisterViewModelFactory

class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding
    private lateinit var viewModel: RegisterViewModel
    private lateinit var viewModelFactory: RegisterViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_register, container, false)
        binding.lifecycleOwner = this

        val application = requireNotNull(this.activity).application

        val viewModelFactory = RegisterViewModelFactory(application)

        viewModel =
            ViewModelProviders.of(
                this, viewModelFactory).get(RegisterViewModel::class.java)

        binding.viewModel = viewModel

        binding.apply {
            tvTitle.text = getString(R.string.register_title)
            btRegister.text = getString(R.string.register_button)
        }

        //Login
        viewModel.navigateToLoginFragment.observe(this, Observer { bool ->
            bool?.let {
                if (bool) {
                    this.findNavController().navigate(
                        RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
                    )
                    viewModel.doneNavigating()
                }
            }
        })

        return binding.root
    }
}
