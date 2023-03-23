package com.movieapp.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import com.movieapp.myapplication.data.remote.Movie
import com.movieapp.myapplication.local.FavoriteMovie
import com.movieapp.myapplication.repo.FavoriteMovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject
 constructor(private val repo: FavoriteMovieRepository):ViewModel(){
     fun addToFavorite(movie:Movie){
         CoroutineScope(Dispatchers.IO).launch {
             repo.addToFavorite(
                FavoriteMovie(
                    movie.id,
                    movie.original_title,
                    movie.overview,
                    movie.poster_path
                )
             )
         }
     }

    suspend fun checkMovie(id:String)=repo.checkMovie(id)

    fun removeFromList(id:String){
        CoroutineScope(Dispatchers.IO).launch {
            repo.removeFromFavorite(id)
        }
    }
}