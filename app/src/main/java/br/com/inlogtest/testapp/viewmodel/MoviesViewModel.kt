package br.com.inlogtest.testapp.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.inlogtest.testapp.InlogApplication
import br.com.inlogtest.testapp.model.Movie
import br.com.inlogtest.testapp.model.MovieSearchInfo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MoviesViewModel : ViewModel() {

    private var disposables = CompositeDisposable()

    private var status = MutableLiveData<Boolean>()
    var error = MutableLiveData<String>()

    var movies = MutableLiveData<MovieSearchInfo>()

    var movie = MutableLiveData<Movie>()

    fun getUpcomingMovies(context: Context){
        val page = (movies.value?.page ?: 0) + 1
        Log.d("Searching movies", "Loading page $page")

        disposables.add((context.applicationContext as InlogApplication).getService().getUpcomingMovies(page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe{ status.value = true }
            .doFinally { status.value = false }
            .subscribe({
                movies.value = it.body()
            }, {
                error.value = it.localizedMessage
            })
        )
    }

    fun getMovieDetail(context: Context, id: Int){
        disposables.add((context.applicationContext as InlogApplication).getService().getMovie(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe{ status.value = true }
            .doFinally { status.value = false }
            .subscribe({
                movie.value = it.body()
            }, {
                error.value = it.localizedMessage
            })
        )
    }

}