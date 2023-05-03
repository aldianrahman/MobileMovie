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
import com.example.mobilemovie.adapter.RankAdapter
import com.example.mobilemovie.models.Rank
import com.example.mobilemovie.models.RankResponse
import com.example.mobilemovie.services.MovieApiInterface
import com.example.mobilemovie.services.MovieApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RankFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_rank, container, false)
        val rv_rank = view.findViewById(R.id.rv_rank) as RecyclerView // deklar
        rv_rank.apply {
            layoutManager = GridLayoutManager(activity, 2, LinearLayoutManager.VERTICAL,false)
            rv_rank.setHasFixedSize(true)
            getRankData { ranks : List<Rank> ->
                rv_rank.adapter = RankAdapter(ranks)
            }
            return view
        }
    }

    private fun getRankData(callback:(List<Rank>)->Unit){
        val apiService = MovieApiService.getInstance().create(MovieApiInterface::class.java)
        apiService.getRankList().enqueue(object : Callback<RankResponse> {
            override fun onResponse(call: Call<RankResponse>, response: Response<RankResponse>) {
                return callback(response.body()!!.rank)
            }

            override fun onFailure(call: Call<RankResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }


        })
    }

}