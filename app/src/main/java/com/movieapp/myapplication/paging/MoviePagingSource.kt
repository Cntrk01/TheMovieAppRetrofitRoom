package com.movieapp.myapplication.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.movieapp.myapplication.data.remote.Movie
import com.movieapp.myapplication.data.remote.MovieApi
import retrofit2.HttpException
import java.io.IOException

class MoviePagingSource(private val api: MovieApi,private val query:String?) : PagingSource<Int,Movie>() {
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return  try {
            val position=params.key ?: 1
            val response = if (query !=null) api.searchMovies(query,position) else
                api.getNowPlayingMovies(position)
            val movies=response.results

            LoadResult.Page(
                data = movies,
                prevKey = if(position == 1) null else position-1,
                nextKey = if(movies.isEmpty()) null else position+1
            )
        }catch (e:java.lang.Exception){
            LoadResult.Error(e)
        }catch (e:IOException){
            LoadResult.Error(e)
        }catch (e:HttpException){
            LoadResult.Error(e)
        }
    }
}