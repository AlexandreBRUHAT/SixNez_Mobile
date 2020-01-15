package com.sixnez

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.sixnez.databinding.FragmentActeursBinding
import com.sixnez.viewmodel.ActeursViewModel
import com.sixnez.viewmodelfactory.ActeursViewModelFactory

class ActeursFragment : Fragment() {
    private lateinit var binding: FragmentActeursBinding
    private lateinit var viewModel: ActeursViewModel
    private lateinit var viewModelFactory: ActeursViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_acteurs, container, false)
        binding.lifecycleOwner = this

        val application = requireNotNull(this.activity).application
        val viewModelFactory = ActeursViewModelFactory(application)

        viewModel =
            ViewModelProviders.of(
                this, viewModelFactory).get(ActeursViewModel::class.java)

        binding.viewModel = viewModel

        binding.apply {
            tvTitle.text = getString(R.string.actors_title)
            btSearch.text = getString(R.string.search_button)
        }

        //Search
        viewModel.navigateToListActorsFragment.observe(this, Observer { request ->
            request?.let {
                val activity = activity as MainActivity?
                activity?.changeFragment(ListActeursFragment(request))

                viewModel.doneNavigating()
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