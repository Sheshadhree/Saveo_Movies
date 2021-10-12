package com.example.saveomovies.data.api

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val API_KEY= "2bcb3e43d30f0fdaf4ab7cb5b994a6c4"
const val BASE_URL = "https://api.themoviedb.org/3/"
const val POSTER_BASE_URL = "https://image.tmdb.org/t/p/w342"

const val FIRST_PAGE = 1;
const val POST_PER_PAGE = 20

//https://api.themoviedb.org/3/movie/popular?api_key=2bcb3e43d30f0fdaf4ab7cb5b994a6c4
//https://api.themoviedb.org/3/movie/4700?api_key=2bcb3e43d30f0fdaf4ab7cb5b994a6c4&language=en-US
//"https://image.tmdb.org/t/p/w342/477utDa8japbfsluXB2ZK2EZvUx.jpg"

object TheMovieDBClient {

    fun getClient():TheMovieDBInterface
    {
        val requestInterceptor = Interceptor{ chain ->

            val url = chain.request()
                .url()
                .newBuilder()
                .addQueryParameter("api_key", API_KEY)
                .build()

            val request = chain.request()
                .newBuilder()
                .url(url)
                .build()
            return@Interceptor chain.proceed(request)

        }

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(requestInterceptor)
            .connectTimeout(60,TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TheMovieDBInterface::class.java)
    }
}