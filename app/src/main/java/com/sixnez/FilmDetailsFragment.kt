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
import com.sixnez.adapter.ActeurFilmAdapter
import com.sixnez.adapter.ActeurFilmListener
import com.sixnez.databinding.FragmentFilmDetailsBinding
import com.sixnez.model.ActeurDetailledDTO
import com.sixnez.model.FilmDetailledDTO
import com.sixnez.model.FilmIdDTO
import com.sixnez.viewmodel.FilmDetailsViewModel
import com.sixnez.viewmodelfactory.FilmDetailsViewModelFactory
import kotlinx.android.synthetic.main.fragment_film_details.*


class FilmDetailsFragment (flm: FilmDetailledDTO, fid : FilmIdDTO) : Fragment() {
    private lateinit var binding: FragmentFilmDetailsBinding
    private lateinit var viewModel: FilmDetailsViewModel
    private lateinit var viewModelFactory: FilmDetailsViewModelFactory
    private var film: FilmDetailledDTO
    private var idDTO: FilmIdDTO

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_film_details, container, false)
        binding.lifecycleOwner = this

        val application = requireNotNull(this.activity).application
        val viewModelFactory = FilmDetailsViewModelFactory(application,film,idDTO)

        viewModel =
            ViewModelProviders.of(
                this, viewModelFactory).get(FilmDetailsViewModel::class.java)

        binding.viewModel = viewModel

        binding.apply {
            tvAnnee1.text = getString(R.string.sortie)
            tvCategories1.text = getString(R.string.categories)
            tvActeurs.text = getString(R.string.acteurs)
            if (film.fav)
                ibFav.setImageResource(R.drawable.red_heart)
            else
                ibFav.setImageResource(R.drawable.empty_heart)
        }

        val adapter = ActeurFilmAdapter(ActeurFilmListener { ActeurFilm ->
            viewModel.getActeurById(ActeurFilm.id)
        })

        binding.list.adapter = adapter

        adapter.submitList(film.acteurs)

        //Goto acteur
        viewModel.acteur.observe(viewLifecycleOwner, Observer {
            it?.let {
                val activity = activity as MainActivity?
                activity?.changeFragment(ActeurDetailsFragment(viewModel.acteur.value as ActeurDetailledDTO))
            }
        })

        //Add favorite
        viewModel.favAdded.observe(viewLifecycleOwner, Observer {
            it?.let {
                ib_fav.setImageResource(R.drawable.red_heart)
                Toast.makeText(this.context, "Film ajouté à vos favoris !", Toast.LENGTH_SHORT).show()
            }
        })

        //Delete favorite
        viewModel.favDeleted.observe(viewLifecycleOwner, Observer {
            it?.let {
                ib_fav.setImageResource(R.drawable.empty_heart)
                Toast.makeText(this.context, "Film enlevé de vos favoris !", Toast.LENGTH_SHORT).show()
            }
        })

        //Load image from URL
        viewModel.imgLoaded.observe(viewLifecycleOwner, Observer {
            it?.let {
                var path = "http://"+viewModel.film.value?.imgURL
                // image too long to load ...
//                Picasso.get().load(path).fit().centerCrop().into(iv_film)
            }
        })

        return binding.root
    }

    init {
        this.film = flm
        this.idDTO = fid
    }
}