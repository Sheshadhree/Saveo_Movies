package com.example.saveomovies.ui.popular_movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.example.saveomovies.data.repositary.NetworkState
import com.example.saveomovies.data.value_object.whole_movie.Movie
import io.reactivex.disposables.CompositeDisposable

class MainActivityViewModel(private val movieRepositary:MoviePagedListRepositary): ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val moviePagedList:LiveData<PagedList<Movie>> by lazy {
        movieRepositary.fetchLiveMoviePagedList(compositeDisposable)
    }

    val networkState : LiveData<NetworkState> by lazy{
        movieRepositary.getNetworkState()
    }

    fun listIsEmpty():Boolean
    {
        return moviePagedList.value?.isEmpty()?:true
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}