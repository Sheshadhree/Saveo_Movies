package com.example.saveomovies.data.repositary

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.saveomovies.data.api.TheMovieDBInterface
import com.example.saveomovies.data.value_object.Movie_Details
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.lang.Exception

class MovieDetailsNetworkDataSource(private val apiService:TheMovieDBInterface,private val compositeDisposable: CompositeDisposable)
{

    private val _networkState = MutableLiveData<NetworkState>()
    val networkState :LiveData<NetworkState>
    get() = _networkState

    private val _downloadedMovieDetailsResponse = MutableLiveData<Movie_Details>()
    val downloadedMovieDetailsResponse :LiveData<Movie_Details>
    get() = _downloadedMovieDetailsResponse

    fun fetchMovieDetails(movieId:Int)
    {
        _networkState.postValue(NetworkState.LOADING)

        try {
            compositeDisposable.add(
                apiService.getMovieDetails(movieId)
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                        {
                            _downloadedMovieDetailsResponse.postValue(it)
                            _networkState.postValue(NetworkState.LOADED)
                        },
                        {
                            _networkState.postValue(NetworkState.ERROR)
                            Log.e("MovieDetailsDataSource", it.message.toString())

                        }
                    )
            )
        }
        catch (e:Exception)
        {
            Log.e("MovieDetailsDataSource", e.message.toString())

        }
    }

}