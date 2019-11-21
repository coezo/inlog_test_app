package br.com.inlogtest.testapp.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Movie (
    @SerializedName("id")
    var id: Int,
    @SerializedName("title")
    var title: String,
    @SerializedName("overview")
    var overview: String?,
    @SerializedName("genres")
    var genres: List<Genre>?,
    @SerializedName("vote_average")
    var voteAverage: Float = 0.0f,
    @SerializedName("release_date")
    var releaseDate: String?,
    @SerializedName("runtime")
    var runtime: Int = 0,
    @SerializedName("budget")
    var budget: Long = 0L,
    @SerializedName("revenue")
    var revenue: Long = 0L,
    @SerializedName("poster_path")
    var posterPath: String,
    @SerializedName("backdrop_path")
    var backdropPath: String
) : Serializable