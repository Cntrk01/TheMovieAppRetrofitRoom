package com.movieapp.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import com.movieapp.myapplication.repo.FavoriteMovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject
 constructor(private val repo: FavoriteMovieRepository):ViewModel(){

     val movies=repo.getFavoriteMovies()

}