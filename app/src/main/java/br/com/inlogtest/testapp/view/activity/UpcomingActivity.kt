package br.com.inlogtest.testapp.view.activity

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.inlogtest.testapp.InlogApplication
import br.com.inlogtest.testapp.R
import br.com.inlogtest.testapp.util.buildPosterBaseUrl
import br.com.inlogtest.testapp.view.adapter.MovieAdapter
import br.com.inlogtest.testapp.viewmodel.ConfigurationViewModel
import br.com.inlogtest.testapp.viewmodel.MoviesViewModel
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_upcoming.*

class UpcomingActivity : AppCompatActivity() {

    private var configurationViewModel = ConfigurationViewModel()
    private var moviesViewModel = MoviesViewModel()

    private lateinit var adapter: MovieAdapter

    private var canLoadPage = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_upcoming)

        prepareObservers()

        getConfiguration()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)

        (menu!!.findItem(R.id.action_search).actionView as SearchView).setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                canLoadPage = newText.isNullOrEmpty()
                adapter.filter.filter(newText)
                return true
            }
        })

        return true
    }

    private fun prepareRecycler() {
        recycler.layoutManager = GridLayoutManager(this, 2)
        recycler.adapter = adapter

        recycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if(!recyclerView.canScrollVertically(1) && canLoadPage) {
                    adapter.resetFilter()
                    getUpcomingMovies()
                }
            }
        })

        recycler.setRecyclerListener {
            val holder = it as MovieAdapter.MovieViewHolder
            Glide.with(holder.poster.context).clear(holder.poster)
        }
    }

    private fun prepareObservers(){
        observeConfiguration()
        observeMovieSearch()
        observeError()
    }

    private fun observeConfiguration(){
        configurationViewModel.configuration.observe(this, Observer {
            (application as InlogApplication).configuration = it
            getUpcomingMovies()
        })
    }

    private fun observeMovieSearch(){
        moviesViewModel.movies.observe(this, Observer {
            val movies = ArrayList(it.movies)
            if(movies.isNotEmpty()) {
                adapter = MovieAdapter(
                    this,
                    buildPosterBaseUrl((application as InlogApplication).configuration)
                )
                adapter.addItems(movies)
                prepareRecycler()
            }
            canLoadPage = true
            progress.visibility = View.INVISIBLE
        })
    }

    private fun observeError() {
        configurationViewModel.error.observe(this, Observer {
            Log.e("Error", it)
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            progress.visibility = View.INVISIBLE
        })

        moviesViewModel.error.observe(this, Observer {
            Log.e("Error", it)
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            progress.visibility = View.INVISIBLE
        })
    }

    private fun getConfiguration(){
        configurationViewModel.getConfiguration(applicationContext)
    }

    private fun getUpcomingMovies(){
        progress.visibility = View.VISIBLE
        canLoadPage = false
        moviesViewModel.getUpcomingMovies(this)
    }

}