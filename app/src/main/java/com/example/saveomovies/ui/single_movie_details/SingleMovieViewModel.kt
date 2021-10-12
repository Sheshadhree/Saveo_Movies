package com.example.saveomovies.ui.single_movie_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.saveomovies.data.repositary.NetworkState
import com.example.saveomovies.data.value_object.Movie_Details
import io.reactivex.disposables.CompositeDisposable

class SingleMovieViewModel (private val movieRepositary:MovieDetailsRepositary , movieId:Int ):ViewModel()
{
    private val compositeDisposable = CompositeDisposable()

    val movieDetails :LiveData<Movie_Details> by lazy {
        movieRepositary.fetchSingleMovieDetails(compositeDisposable, movieId)
    }
    val networkState :LiveData<NetworkState> by lazy {
        movieRepositary.getMovieDetailsNetworkState()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}