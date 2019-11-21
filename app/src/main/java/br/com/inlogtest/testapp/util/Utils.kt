package br.com.inlogtest.testapp.util

import br.com.inlogtest.testapp.model.Configuration
import br.com.inlogtest.testapp.model.Genre
import java.text.NumberFormat
import java.util.*

// Poster Size = "w154"
fun buildPosterBaseUrl(configuration: Configuration) = configuration.imagesConfig.baseUrl +
        configuration.imagesConfig.posterSizes[1]

// Backdrop Size = "w780"
fun buildBackdropBaseUrl(configuration: Configuration) = configuration.imagesConfig.baseUrl +
        configuration.imagesConfig.backdropSizes[1]

// Could use Date and SimpleDateFormat, but in this case, the format will always be like that
fun formatDateString(dateString: String?) : String{
    var formattedDate = ""
    if(dateString != null) {
        val dateParts = dateString.split("-").toTypedArray()
        formattedDate = dateParts[2] + "/" + dateParts[1] + "/" + dateParts[0]
    }
    return formattedDate
}

fun formatGenres(genres: List<Genre>?) = (genres?.map {it.name})?.toTypedArray()?.joinToString(", ") ?: ""

fun formatRuntime(runtime: Int) = "$runtime min."

fun formatCurrencyValue(value: Int) = formatCurrencyValue(value.toFloat())
fun formatCurrencyValue(value: Long) = formatCurrencyValue(value.toFloat())
fun formatCurrencyValue(value: Float) = NumberFormat.getCurrencyInstance(Locale.getDefault()).format(value)