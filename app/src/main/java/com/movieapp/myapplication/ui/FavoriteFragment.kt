package com.movieapp.myapplication.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.movieapp.myapplication.R
import com.movieapp.myapplication.adapter.FavoriteAdapter
import com.movieapp.myapplication.data.remote.Movie
import com.movieapp.myapplication.databinding.FragmentDetailBinding
import com.movieapp.myapplication.databinding.FragmentFavoriteBinding
import com.movieapp.myapplication.local.FavoriteMovie
import com.movieapp.myapplication.viewmodel.DetailsViewModel
import com.movieapp.myapplication.viewmodel.FavoriteViewModel
import com.movieapp.myapplication.viewmodel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : Fragment() {
    private var _binding : FragmentFavoriteBinding?=null
    private val binding get() = _binding!!
    private val args by navArgs<DetailFragmentArgs>()
    private val viewModel by viewModels<FavoriteViewModel>()
    private lateinit var adapter : FavoriteAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentFavoriteBinding.inflate(layoutInflater)
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter= FavoriteAdapter()
        viewModel.movies.observe(viewLifecycleOwner){
            adapter.setMovieList(it)

                binding.apply {
                    rvMovie.setHasFixedSize(true)
                    rvMovie.adapter=adapter
                }
        }

        adapter.setOnItemClickCallBack(object : FavoriteAdapter.OnClickItemCallBack{
            override fun onItemClick(favoriteMovie: FavoriteMovie) {
                val movie=Movie(favoriteMovie.id_movie, favoriteMovie.overview!!,favoriteMovie.poster_path,favoriteMovie.original_title)
                val action=FavoriteFragmentDirections.actionNavFavoriteToDetailFragment(movie)
                findNavController().navigate(action)
            }

        })
    }



}