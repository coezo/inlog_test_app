package br.com.inlogtest.testapp.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Configuration(
    @SerializedName("images")
    var imagesConfig: ImageConfiguration
) : Serializable