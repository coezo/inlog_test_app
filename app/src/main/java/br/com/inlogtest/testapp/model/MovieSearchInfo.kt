package br.com.inlogtest.testapp.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class MovieSearchInfo(
    @SerializedName("page")
    var page: Int,
    @SerializedName("total_pages")
    var pages: Int,
    @SerializedName("total_results")
    var total: Int,
    @SerializedName("results")
    var movies: List<Movie>
) : Serializable