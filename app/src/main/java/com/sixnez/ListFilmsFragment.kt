package com.sixnez

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.sixnez.adapter.FilmAdapter
import com.sixnez.adapter.FilmListener
import com.sixnez.databinding.FragmentListFilmsBinding
import com.sixnez.model.FilmDetailledDTO
import com.sixnez.model.FilmRequest
import com.sixnez.viewmodel.ListFilmsViewModel
import com.sixnez.viewmodelfactory.ListFilmsViewModelFactory

class ListFilmsFragment (req: FilmRequest): Fragment() {
    private lateinit var binding: FragmentListFilmsBinding
    private lateinit var viewModel: ListFilmsViewModel
    private lateinit var viewModelFactory: ListFilmsViewModelFactory
    private lateinit var request: FilmRequest
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_list_films, container, false)
        binding.lifecycleOwner = this

        val viewModelFactory = ListFilmsViewModelFactory(request)

        viewModel =
            ViewModelProviders.of(
                this, viewModelFactory).get(ListFilmsViewModel::class.java)

        binding.viewModel = viewModel

        binding.apply {
            tvTitle.text = getString(R.string.result_title)
            btNextPage.text = getString(R.string.nextPage)
            btPrecedentPage.text = getString(R.string.precedentPage)
        }

        val adapter = FilmAdapter(FilmListener { Film ->
            viewModel.getFilmById(Film.id)
            viewModel.loading(true)

        })
        binding.list.adapter = adapter

        viewModel.films.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        viewModel.film.observe(viewLifecycleOwner, Observer {
            it?.let {
                val activity = activity as MainActivity?
                activity?.changeFragment(FilmDetailsFragment(viewModel.film.value as FilmDetailledDTO))
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

                    if (page < viewModel.nbPages.value?:0) {
                        binding.btNextPage.visibility = View.VISIBLE
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