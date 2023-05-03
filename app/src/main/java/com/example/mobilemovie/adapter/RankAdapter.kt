package com.example.mobilemovie.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mobilemovie.DetailMovie
import com.example.mobilemovie.R
import com.example.mobilemovie.models.Rank
import kotlinx.android.synthetic.main.rank_item.view.*

class RankAdapter(
    private val ranks: List<Rank>
): RecyclerView.Adapter<RankAdapter.RankViewHolder>(){

    inner class RankViewHolder(view : View): RecyclerView.ViewHolder(view){
        init {
            itemView.setOnClickListener{v:View->
                val position:Int = adapterPosition
                Toast.makeText(itemView.context, "You Click Top Rated #${ranks[position].title}", Toast.LENGTH_SHORT).show()
                val intent = Intent (itemView.context, DetailMovie::class.java)
                intent.putExtra("gambar", ranks[position].poster)
                intent.putExtra("judul", ranks[position].title)
                intent.putExtra("desc", ranks[position].desc)
                intent.putExtra("popular", "Rating Movie "+ranks[position].star+" /10")
                itemView.context.startActivity(intent)

            }

        }
        private val IMAGE_BASE = "https://image.tmdb.org/t/p/w500/"
        fun bindRank(rank: Rank){
            itemView.rank_title.text = rank.title.toUpperCase()
            itemView.rank_star.text = rank.star
            Glide.with(itemView).load(IMAGE_BASE+ rank.poster).into(itemView.rank_poster)
//
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RankViewHolder {
        return RankViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.rank_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RankViewHolder, position: Int) {
       holder.bindRank(ranks.get(position))
    }

    override fun getItemCount(): Int = ranks.size

}