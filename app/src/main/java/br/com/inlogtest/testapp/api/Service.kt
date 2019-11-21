package br.com.inlogtest.testapp.api

import br.com.inlogtest.testapp.model.Configuration
import br.com.inlogtest.testapp.model.Movie
import br.com.inlogtest.testapp.model.MovieSearchInfo
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Service {

    @GET("configuration")
    fun getConfiguration(): Observable<Response<Configuration>>

    @GET("movie/upcoming")
    fun getUpcomingMovies(
        @Query("page") page: Int
    ): Observable<Response<MovieSearchInfo>>

    @GET("movie/{movieId}")
    fun getMovie(
        @Path("movieId") movieId: Int
    ): Observable<Response<Movie>>

    @GET("search/movie")
    fun searchMovies(
        @Query("query") query: String,
        @Query("page") page: Int
    ): Observable<Response<MovieSearchInfo>>
}