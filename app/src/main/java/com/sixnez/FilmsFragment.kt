package com.sixnez

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.sixnez.databinding.FragmentFilmsBinding
import com.sixnez.databinding.FragmentLoginBinding
import com.sixnez.viewmodel.FilmsViewModel
import com.sixnez.viewmodel.LoginViewModel
import com.sixnez.viewmodelfactory.FilmsViewModelFactory
import com.sixnez.viewmodelfactory.LoginViewModelFactory
import kotlinx.android.synthetic.main.fragment_films.*

class FilmsFragment() : Fragment() {
    private lateinit var binding: FragmentFilmsBinding
    private lateinit var viewModel: FilmsViewModel
    private lateinit var viewModelFactory: FilmsViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_films, container, false)
        binding.lifecycleOwner = this

        val application = requireNotNull(this.activity).application
        val viewModelFactory = FilmsViewModelFactory(application)

        viewModel =
            ViewModelProviders.of(
                this, viewModelFactory).get(FilmsViewModel::class.java)

        binding.viewModel = viewModel

        binding.apply {
            tvTitle.text = getString(R.string.films_title)
            btSearch.text = getString(R.string.search_button)
//            spGenre.adapter = ArrayAdapter<String>(application,android.R.layout.simple_list_item_1, genres)
//            spGenre.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
//                override fun onNothingSelected(parent: AdapterView<*>?) {
//                    //
//                }
//
//                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
//                    viewModel?.onGenreSelected(genres.get(position))
//                }
//
//            }
        }

        //Search
        viewModel.navigateToListFilmsFragment.observe(this, Observer { request ->
            request?.let {
                val activity = activity as MainActivity?
                activity?.changeFragment(ListFilmsFragment(request))

                viewModel.doneNavigating()
            }
        })

        //Genres
        viewModel.genres.observe(this, Observer { genres ->
            genres?.let {
                sp_genre.adapter = ArrayAdapter<String>(application,android.R.layout.simple_list_item_1, genres)
                sp_genre.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                    override fun onNothingSelected(parent: AdapterView<*>?) {
                        //
                    }

                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        viewModel.onGenreSelected(genres.get(position))
                    }

                }
            }
        })

        //Alerts
        viewModel.alert.observe(this, Observer { message ->
            message?.let {
                Toast.makeText(this.context, message, Toast.LENGTH_SHORT).show()
                viewModel.doneAlerting()
            }
        })

        return binding.root
    }
}