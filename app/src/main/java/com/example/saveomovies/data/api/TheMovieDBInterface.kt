package com.example.saveomovies.data.api

import com.example.saveomovies.data.value_object.Movie_Details
import com.example.saveomovies.data.value_object.whole_movie.MovieResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TheMovieDBInterface {

    //https://api.themoviedb.org/3/movie/popular?api_key=2bcb3e43d30f0fdaf4ab7cb5b994a6c4
    //https://api.themoviedb.org/3/movie/4700?api_key=2bcb3e43d30f0fdaf4ab7cb5b994a6c4&language=en-US
    //https://api.themoviedb.org/3/


    @GET("movie/popular")
    fun getPopularMovie(@Query("page")page:Int):Single<MovieResponse>

    @GET("movie/{movie_id}")
    fun getMovieDetails(@Path("movie_id")id:Int): Single<Movie_Details>

}