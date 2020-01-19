package com.sixnez

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.sixnez.databinding.FragmentActeurDetailsBinding
import com.sixnez.model.ActeurDTO
import com.sixnez.model.ActeurDetailledDTO
import com.sixnez.viewmodel.ActeurDetailsViewModel
import com.sixnez.viewmodelfactory.ActeurDetailsViewModelFactory
import kotlinx.android.synthetic.main.fragment_acteur_details.*

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
//            tvMort1.text = getString(R.string.mort)
//            tvNaissance1.text = getString(R.string.naissance)
        }

        return binding.root
    }

    init {
        this.acteur = act
    }
}