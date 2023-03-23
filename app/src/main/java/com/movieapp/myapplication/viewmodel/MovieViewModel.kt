package com.movieapp.myapplication.viewmodel

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.movieapp.myapplication.data.remote.Movie
import com.movieapp.myapplication.repo.MovieRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.http.Query
import javax.inject.Inject

@HiltViewModel
class MovieViewModel
    @Inject constructor(
    private val movieRepository: MovieRepository,
    state: SavedStateHandle
) : ViewModel() {

    companion object {
        private const val current = "current_query"
        private const val empty = ""
    }

    //Bu yapı kullanıcıdan ilk etapta veri girişi almayıp ekrana veri getirmek istediğimizde kullanabiliriz.
    private val _query: MutableLiveData<String> = state.getLiveData(current, empty)

    val observeData = _query.switchMap {
        if (it.isEmpty()) {
            movieRepository.getNowPlayingMovies()
        } else {
            movieRepository.getSearchMovie(it)
        }
    }.cachedIn(viewModelScope) //bunu eklemeyi unuttum favorite geçip gelince çökme yaptı.Coroutine olunca olay olmuyor


    fun searchMovie(query: String) {
        _query.postValue(query)
    }

}