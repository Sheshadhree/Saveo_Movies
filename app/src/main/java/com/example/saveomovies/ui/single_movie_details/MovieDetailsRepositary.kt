package com.example.saveomovies.ui.single_movie_details

import androidx.lifecycle.LiveData
import com.example.saveomovies.data.api.TheMovieDBInterface
import com.example.saveomovies.data.repositary.MovieDetailsNetworkDataSource
import com.example.saveomovies.data.repositary.NetworkState
import com.example.saveomovies.data.value_object.Movie_Details
import io.reactivex.disposables.CompositeDisposable

class MovieDetailsRepositary(private val apiService:TheMovieDBInterface) {

    lateinit var movieDetailsNetworkDataSource :MovieDetailsNetworkDataSource

    fun fetchSingleMovieDetails(compositeDisposable: CompositeDisposable,movieId:Int):LiveData<Movie_Details>
    {
        movieDetailsNetworkDataSource = MovieDetailsNetworkDataSource(apiService,compositeDisposable)
        movieDetailsNetworkDataSource.fetchMovieDetails(movieId)

        return movieDetailsNetworkDataSource.downloadedMovieDetailsResponse
    }

    fun getMovieDetailsNetworkState():LiveData<NetworkState>
    {
        return movieDetailsNetworkDataSource.networkState
    }


}