package com.movieapp.myapplication.repo

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.movieapp.myapplication.data.remote.MovieApi
import com.movieapp.myapplication.paging.MoviePagingSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject
 constructor(private val movieApi: MovieApi){
  fun getNowPlayingMovies()=
    Pager(
     config = PagingConfig(
      pageSize = 5,
      maxSize = 20,
      enablePlaceholders = false
     ),
     pagingSourceFactory ={MoviePagingSource(movieApi,null)}
    ).liveData

    fun getSearchMovie(query:String)=
        Pager(
            config = PagingConfig(
                pageSize = 5,
                maxSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory ={MoviePagingSource(movieApi,query)}
        ).liveData
}