package com.example.saveomovies.ui.single_movie_details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.saveomovies.R
import com.example.saveomovies.data.api.POSTER_BASE_URL
import com.example.saveomovies.data.api.TheMovieDBClient
import com.example.saveomovies.data.api.TheMovieDBInterface
import com.example.saveomovies.data.repositary.NetworkState
import com.example.saveomovies.data.value_object.Movie_Details
import kotlinx.android.synthetic.main.activity_single_movie.*
import java.text.NumberFormat
import java.util.*

class SingleMovie : AppCompatActivity() {

    private lateinit var viewModel: SingleMovieViewModel
    private lateinit var movieRepositary:MovieDetailsRepositary

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_movie)

        val movie_id :Int = intent.getIntExtra("id",1)
        val apiService :TheMovieDBInterface = TheMovieDBClient.getClient()
        movieRepositary = MovieDetailsRepositary(apiService)

        viewModel = getViewModel(movie_id)
        viewModel.movieDetails.observe(this, Observer {
            bindUI(it)
        })


        viewModel.networkState.observe(this, Observer {
            progress_bar.visibility = if(it== NetworkState.LOADING ) View.VISIBLE else View.GONE
            txt_error.visibility = if(it== NetworkState.ERROR ) View.VISIBLE else View.GONE
        })


    }

    private fun bindUI(it: Movie_Details?) {
        movie_title.text = it?.title
        movie_tagline.text = it?.tagline
        movie_release_date.text = it?.releaseDate
        movie_rating.text = it?.voteAverage.toString()
        movie_runtime.text = it?.runtime.toString() + " minutes"
        movie_overview.text = it?.overview

        val formatCurrency : NumberFormat= NumberFormat.getCurrencyInstance(Locale.US)
        movie_budget.text = formatCurrency.format(it?.budget)
        movie_revenue.text = formatCurrency.format(it?.revenue)

        val moviePosterURL :String = POSTER_BASE_URL+it?.posterPath
        Glide.with(this)
            .load(moviePosterURL)
            .into(iv_movie_poster)

    }

    private fun getViewModel(movieId:Int):SingleMovieViewModel
    {
        return ViewModelProviders.of(this,object :ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return SingleMovieViewModel(movieRepositary,movieId)as T
            }
        })[SingleMovieViewModel::class.java]
    }
}