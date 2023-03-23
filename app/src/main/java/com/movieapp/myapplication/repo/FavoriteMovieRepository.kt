package com.movieapp.myapplication.repo

import com.movieapp.myapplication.local.FavoriteMovie
import com.movieapp.myapplication.local.FavoriteMovieDao
import javax.inject.Inject

class FavoriteMovieRepository @Inject
 constructor(private val favoriteMovieDao: FavoriteMovieDao){

     suspend fun addToFavorite(favoriteMovie: FavoriteMovie)=favoriteMovieDao.addFavoriteMovie(favoriteMovie)
     suspend fun checkMovie(id:String)=favoriteMovieDao.checkMovie(id)
     suspend fun removeFromFavorite(id: String) = favoriteMovieDao.removeFromFavorite(id)

    fun getFavoriteMovies() = favoriteMovieDao.getFavoriteMovie()
}