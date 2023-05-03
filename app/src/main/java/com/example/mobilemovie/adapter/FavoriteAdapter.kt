package com.example.mobilemovie.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mobilemovie.FavoriteDetail
import com.example.mobilemovie.R
import com.example.mobilemovie.db.entity.FavoriteEntity
import kotlinx.android.synthetic.main.favorite_item.view.*
import kotlinx.android.synthetic.main.watchlist_item.view.iv_delete

class FavoriteAdapter (private val favs:ArrayList<FavoriteEntity>, private val listener_fav : OnAdapterListener) : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {


    inner class FavoriteViewHolder(val view: View) : RecyclerView.ViewHolder(view){init {
        itemView.setOnClickListener{
            Toast.makeText(itemView.context, "You Click Popular Movie #${favs[position].id}", Toast.LENGTH_SHORT).show()
            val intent = Intent (itemView.context, FavoriteDetail::class.java)
            intent.putExtra("judul", favs[position].title)
            intent.putExtra("id", favs[position].id)
            intent.putExtra("gambar", favs[position].gambar)
            intent.putExtra("desc", "Favorite Id : "+favs[position].id)
            intent.putExtra("popular", "Favorite "+favs[position].value)
            itemView.context.startActivity(intent)
        }
    }}





    fun setDatafav(list:List<FavoriteEntity>){
        favs.clear()
        favs.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        return FavoriteViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.favorite_item,parent,false)
        )
    }
    private val IMAGE_BASE = "https://image.tmdb.org/t/p/w500/"

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {

        val fav = favs[position]
        holder.view.favorite_title.text = fav.title
        holder.view.favorite_desc.text = fav.value
        Glide.with(holder.view).load(IMAGE_BASE+ fav.gambar).into(holder.view.iv_poster_fav)
        holder.view.iv_delete.setOnClickListener{
            listener_fav.onClick(fav)
        }
    }

    override fun getItemCount() = favs.size

    interface OnAdapterListener{
        fun onClick (fav: FavoriteEntity){

        }

    }


}