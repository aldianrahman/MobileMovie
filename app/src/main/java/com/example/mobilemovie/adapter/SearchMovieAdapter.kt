package com.example.mobilemovie.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mobilemovie.DetailMovie
import com.example.mobilemovie.R
import com.example.mobilemovie.activity.MainActivity
import com.example.mobilemovie.models.Movie
import kotlinx.android.synthetic.main.movie_item.view.*
import java.util.*
import kotlin.collections.ArrayList

class SearchMovieAdapter(

    private var movies: ArrayList<Movie>,
): RecyclerView.Adapter<SearchMovieAdapter.MovieViewHolder>(){


    inner class MovieViewHolder(view : View): RecyclerView.ViewHolder(view){
        init {
            itemView.setOnClickListener{
                val position:Int = adapterPosition
                Toast.makeText(itemView.context, "You Click Popular Movie #${movies[position].title}", Toast.LENGTH_SHORT).show()
                val intent = Intent (itemView.context, DetailMovie::class.java)
                val intent_main = Intent (itemView.context, MainActivity::class.java)
                intent.putExtra("gambar", movies[position].poster)
                intent.putExtra("judul", movies[position].title)
                intent.putExtra("desc", movies[position].desc)
                intent.putExtra("popular", "Popular Movie "+movies[position].star+" Watch")





                itemView.context.startActivity(intent)
            }

        }
        private val IMAGE_BASE = "https://image.tmdb.org/t/p/w500/"
        fun bindMovie(movie: Movie){
            itemView.movie_title.text = "INI SEARCH ! "+movie.title.toUpperCase()
            itemView.movie_desc.text = movie.star.toLong().toString() + " Watching"
            Glide.with(itemView).load(IMAGE_BASE+ movie.poster).into(itemView.movie_poster)


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {

        val v =  LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        return MovieViewHolder(v);
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bindMovie(movies.get(position))
    }

    override fun getItemCount(): Int {return movies.size}


    fun getFilter(): Filter {
        return cityFilter
    }

    private val cityFilter = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val filteredList: ArrayList<Movie> = ArrayList()
            if (constraint == null || constraint.isEmpty()) {
                movies.let { filteredList.addAll(it) }
            } else {
                val query = constraint.toString().trim().toLowerCase()
                movies.forEach {
                    if (it.title.toLowerCase(Locale.ROOT).contains(query)) {
                        filteredList.add(it)
                    }
                }
            }
            val results = FilterResults()
            results.values = filteredList
            return results
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            if (results?.values is ArrayList<*>) {
                movies.clear()
                movies.addAll(results.values as ArrayList<Movie>)
                notifyDataSetChanged()
            }
        }
    }
}





