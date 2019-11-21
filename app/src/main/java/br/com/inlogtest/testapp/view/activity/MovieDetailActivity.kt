package br.com.inlogtest.testapp.view.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import br.com.inlogtest.testapp.InlogApplication
import br.com.inlogtest.testapp.R
import br.com.inlogtest.testapp.model.Movie
import br.com.inlogtest.testapp.util.*
import br.com.inlogtest.testapp.viewmodel.MoviesViewModel
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_movie_detail.*

class MovieDetailActivity : AppCompatActivity() {

    private var moviesViewModel = MoviesViewModel()

    private lateinit var movie: Movie

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setContentView(R.layout.activity_movie_detail)

        val movieId = intent.getIntExtra("movieId",0)

        prepareObservers()

        getMovieDetail(movieId)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    private fun prepareObservers(){
        observeMovie()
        observeError()
    }

    private fun observeMovie(){
        moviesViewModel.movie.observe(this, Observer {
            movie = it
            progress.visibility = View.INVISIBLE
            setupMovieInfo()
        })
    }

    private fun observeError() {
        moviesViewModel.error.observe(this, Observer {
            Log.e("Error", it)
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            progress.visibility = View.INVISIBLE
        })
    }

    private fun getMovieDetail(movieId: Int){
        moviesViewModel.getMovieDetail(this, movieId)
    }

    private fun setupMovieInfo(){
        supportActionBar!!.title = movie.title

        val posterUrl = buildBackdropBaseUrl((application as InlogApplication).configuration) + movie.backdropPath

        Glide
            .with(this)
            .load(posterUrl)
            .into(poster_image)

        genres.text = formatGenres(movie.genres)

        overview.text = movie.overview

        vote_average_value.text = movie.voteAverage.toString()
        release_date_value.text = formatDateString(movie.releaseDate)
        runtime_value.text = formatRuntime(movie.runtime)
        budget_value.text = formatCurrencyValue(movie.budget)
        revenue_value.text = formatCurrencyValue(movie.revenue)
    }

}