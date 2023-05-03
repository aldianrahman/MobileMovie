package com.example.mobilemovie.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mobilemovie.ItemDetail
import com.example.mobilemovie.R
import com.example.mobilemovie.db.entity.WatchlistEntity
import kotlinx.android.synthetic.main.watchlist_item.view.*

class WatchlistAdapter (private val watchlist:ArrayList<WatchlistEntity>, private val listener : OnAdapterListener) : RecyclerView.Adapter<WatchlistAdapter.WatchlistViewHolder>() {


    inner class WatchlistViewHolder(val view: View) : RecyclerView.ViewHolder(view){init {
        itemView.setOnClickListener{
            Toast.makeText(itemView.context, "You Click Popular Movie #${watchlist[position].id}", Toast.LENGTH_SHORT).show()
            val intent = Intent (itemView.context, ItemDetail::class.java)
            intent.putExtra("judul", watchlist[position].title)
            intent.putExtra("id", watchlist[position].id)
            intent.putExtra("gambar", watchlist[position].gambar)
            intent.putExtra("desc", "Watchlist Id : "+watchlist[position].id)
            intent.putExtra("popular", "Watchlist "+watchlist[position].value)
            itemView.context.startActivity(intent)
        }
    }}





    fun setData(list:List<WatchlistEntity>){
        watchlist.clear()
        watchlist.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WatchlistViewHolder {
        return WatchlistViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.watchlist_item,parent,false)
        )
    }

    private val IMAGE_BASE = "https://image.tmdb.org/t/p/w500/"

    override fun onBindViewHolder(holder: WatchlistViewHolder, position: Int) {
        val watch = watchlist[position]
        holder.view.watchlist_title.text = watch.title
        holder.view.watchlis_desc.text = watch.value
        Glide.with(holder.view).load(IMAGE_BASE+ watch.gambar).into(holder.view.iv_poster)

        holder.view.iv_delete.setOnClickListener{
            listener.onClick(watch)
        }
    }

    override fun getItemCount() = watchlist.size

    interface OnAdapterListener{
        fun onClick (watch: WatchlistEntity){

        }

    }


}