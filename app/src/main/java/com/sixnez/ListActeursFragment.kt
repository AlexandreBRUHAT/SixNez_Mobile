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
import com.sixnez.adapter.ActeurAdapter
import com.sixnez.adapter.ActeurListener
import com.sixnez.databinding.FragmentListActeursBinding
import com.sixnez.model.ActeurDetailledDTO
import com.sixnez.model.ActeurRequest
import com.sixnez.viewmodel.ListActeursViewModel
import com.sixnez.viewmodelfactory.ListActeursViewModelFactory

class ListActeursFragment (req: ActeurRequest): Fragment() {
    private lateinit var binding: FragmentListActeursBinding
    private lateinit var viewModel: ListActeursViewModel
    private lateinit var viewModelFactory: ListActeursViewModelFactory
    private lateinit var request: ActeurRequest
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_list_acteurs, container, false)
        binding.lifecycleOwner = this

        val viewModelFactory = ListActeursViewModelFactory(request)

        viewModel =
            ViewModelProviders.of(
                this, viewModelFactory).get(ListActeursViewModel::class.java)

        binding.viewModel = viewModel

        binding.apply {
            tvTitle.text = getString(R.string.result_title)
            btNextPage.text = getString(R.string.nextPage)
            btPrecedentPage.text = getString(R.string.precedentPage)
        }

        val adapter = ActeurAdapter(ActeurListener { acteur ->
            viewModel.getActeurById(acteur.id)
            viewModel.loading(true)
        })
        binding.list.adapter = adapter

        viewModel.acteurs.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        viewModel.acteur.observe(viewLifecycleOwner, Observer {
            it?.let {
                val activity = activity as MainActivity?
                activity?.changeFragment(ActeurDetailsFragment(viewModel.acteur.value as ActeurDetailledDTO))
            }
        })

        viewModel.isLoading.observe(viewLifecycleOwner, Observer {
            it.let {
                if(it) {
                    binding.load.visibility = View.VISIBLE
                    binding.list.visibility = View.GONE
                    binding.btNextPage.visibility = View.GONE
                    binding.btPrecedentPage.visibility = View.GONE
                    adapter.submitList(null)
                }
                else {
                    binding.load.visibility = View.GONE
                    binding.list.visibility = View.VISIBLE

                    var page: Int = viewModel.currentPage.value?:0
                    if (page > 1) {
                        binding.btPrecedentPage.visibility = View.VISIBLE
                    }

                    if (viewModel.lastPage.value != true) {
                        binding.btNextPage.visibility = View.VISIBLE
                    }
                    else {
                        binding.btNextPage.visibility = View.GONE
                    }

                }
            }
        })

        return binding.root
    }

    init {
        this.request = req
    }
}