package com.example.mobilemovie.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobilemovie.R
import com.example.mobilemovie.adapter.ComingAdapter
import com.example.mobilemovie.models.Coming
import com.example.mobilemovie.models.ComingResponse
import com.example.mobilemovie.services.MovieApiInterface
import com.example.mobilemovie.services.MovieApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ComingFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_coming, container, false)
        val rv_coming = view.findViewById(R.id.rv_coming) as RecyclerView // deklar
        rv_coming.apply {
            layoutManager = GridLayoutManager(activity, 2, LinearLayoutManager.VERTICAL,false)
            rv_coming.setHasFixedSize(true)
            getComingData { comings : List<Coming> ->
                rv_coming.adapter = ComingAdapter(comings)
            }
            return view
        }
    }

    private fun getComingData(callback:(List<Coming>)->Unit){
        val apiService = MovieApiService.getInstance().create(MovieApiInterface::class.java)
        apiService.getComingList().enqueue(object : Callback<ComingResponse> {
            override fun onResponse(call: Call<ComingResponse>, response: Response<ComingResponse>) {
                return callback(response.body()!!.coming)
            }

            override fun onFailure(call: Call<ComingResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }


        })
    }

}