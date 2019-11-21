package br.com.inlogtest.testapp.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class ImageConfiguration(
    @SerializedName("base_url")
    var baseUrl: String,
    @SerializedName("poster_sizes")
    var posterSizes: List<String>,
    @SerializedName("backdrop_sizes")
    var backdropSizes: List<String>
) : Serializable