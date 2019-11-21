package br.com.inlogtest.testapp.view.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import br.com.inlogtest.testapp.R
import br.com.inlogtest.testapp.model.Movie
import br.com.inlogtest.testapp.util.formatDateString
import br.com.inlogtest.testapp.view.activity.MovieDetailActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.movie_item.view.*
import kotlin.properties.Delegates

class MovieAdapter(private val context: Context, private val basePosterUrl: String) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>(),
    Filterable {

    private var movies by Delegates.observable(ArrayList<Movie>()) { _, _, _ ->
        this.notifyDataSetChanged()
    }
    private var filteredMovies = ArrayList<Movie>()

    private var mFilter = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val modConstraint = constraint.toString().toLowerCase()

            val filterResults = FilterResults()

            if(modConstraint.isNotEmpty()) {
                val auxList = ArrayList<Movie>()
                for(movie: Movie in movies) {
                    if(movie.title.toLowerCase().contains(modConstraint)) {
                        auxList.add(movie)
                    }
                }

                filterResults.count = auxList.size
                filterResults.values = auxList
            } else {
                filterResults.count = movies.size
                filterResults.values = movies
            }
            return filterResults
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            filteredMovies = results?.values as ArrayList<Movie>
            notifyDataSetChanged()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        return MovieViewHolder(view)
    }

    override fun getItemCount() = filteredMovies.size

    override fun getItemId(position: Int) = position.toLong()

    override fun getItemViewType(position: Int) = position

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = filteredMovies[position]

        holder.title.text = movie.title
        holder.voteAverage.text = movie.voteAverage.toString()
        holder.releaseDate.text = formatDateString(movie.releaseDate)

        val posterUrl = basePosterUrl + movie.posterPath

        Glide
            .with(context)
            .load(posterUrl)
            .error(R.drawable.missing_poster)
            .into(holder.poster)

    }

    override fun getFilter(): Filter {
        return mFilter
    }

    fun addItems(items: List<Movie>) {
        if(items.isNotEmpty()) {
            val position = itemCount
            movies.addAll(items)
            filteredMovies.addAll(items)
            for(i in position until movies.size - 1){
                notifyItemInserted(i)
            }
        }
    }

    fun resetFilter(){
        mFilter.filter("")
    }

    inner class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val poster: AppCompatImageView = view.poster_image
        val title: AppCompatTextView = view.movie_title
        val voteAverage: AppCompatTextView = view.vote_average_value
        val releaseDate: AppCompatTextView = view.release_date_value

        init {
            view.setOnClickListener {
                val intent = Intent(context, MovieDetailActivity::class.java)
                intent.putExtra("movieId", filteredMovies[adapterPosition].id)
                context.startActivity(intent)
            }
        }

    }

}