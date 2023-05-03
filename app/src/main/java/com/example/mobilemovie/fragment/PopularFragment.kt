package com.example.mobilemovie.fragment

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobilemovie.R
import com.example.mobilemovie.adapter.MovieAdapter
import com.example.mobilemovie.models.Movie
import com.example.mobilemovie.models.MovieResponse
import com.example.mobilemovie.services.MovieApiInterface
import com.example.mobilemovie.services.MovieApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@Suppress("UNREACHABLE_CODE")
class PopularFragment : Fragment() {

    private val FIRST_PAGE = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_popular, container, false)
        val rv_movie = view.findViewById(R.id.rv_movie) as RecyclerView // deklar
        rv_movie.apply {
        layoutManager = GridLayoutManager(activity, 2, LinearLayoutManager.VERTICAL, false)
        rv_movie.setHasFixedSize(true)

            getMovieData { movies: ArrayList<Movie> ->
            rv_movie.adapter = MovieAdapter(movies)
                rv_movie.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        Log.e("HASIL", "dy = $dy")
                        Log.e("HASIL", "dx = $dx")
                        super.onScrolled(recyclerView, dx, dy)
                    }
                })
        }
        return view
        }

    }

    fun addMoreData(){

        Handler().postDelayed({
        //parameter page

        },5000)



    }



    private fun getMovieData(callback: (ArrayList<Movie>) -> Unit){
        val apiService = MovieApiService.getInstance().create(MovieApiInterface::class.java)

        apiService.getMovieList(FIRST_PAGE).enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                return callback(response.body()!!.movie as ArrayList<Movie>)
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                Toast.makeText(view?.context, "Tidak Terhubung Ke Internet!", Toast.LENGTH_SHORT)
                    .show()
            }

        })
    }



}
