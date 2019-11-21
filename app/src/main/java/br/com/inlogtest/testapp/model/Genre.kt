package br.com.inlogtest.testapp.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Genre(
    @SerializedName("id")
    var id: Int,
    @SerializedName("name")
    var name: String
) : Serializable