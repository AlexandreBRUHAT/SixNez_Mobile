package com.sixnez

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.sixnez.databinding.FragmentLoginBinding
import com.sixnez.viewmodel.LoginViewModel
import com.sixnez.viewmodelfactory.LoginViewModelFactory

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var viewModel: LoginViewModel
    private lateinit var viewModelFactory: LoginViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        binding.lifecycleOwner = this

        val application = requireNotNull(this.activity).application
        val viewModelFactory = LoginViewModelFactory(application)

        viewModel =
            ViewModelProviders.of(
                this, viewModelFactory).get(LoginViewModel::class.java)

        binding.viewModel = viewModel

        binding.apply {
            tvTitle.text = getString(R.string.login_title)
            btLogin.text = getString(R.string.login_button)
        }

        //Login
        viewModel.navigateToNextFragment.observe(this, Observer { bool ->
            bool?.let {
                if (bool) {
                    val activity = activity as MainActivity?
                    activity?.connect(viewModel.user.value)
                    activity?.changeFragment(HomeFragment(), R.id.nav_home)

                    viewModel.doneNavigating()
                }
            }
        })


        //Register
        viewModel.navigateToRegister.observe(this, Observer { bool ->
            bool?.let {
                if (bool) {
                    val activity = activity as MainActivity?
                    activity?.changeFragment(RegisterFragment(), R.id.nav_register)

                    viewModel.doneNavigating()
                }
            }
        })

        return binding.root
    }
}