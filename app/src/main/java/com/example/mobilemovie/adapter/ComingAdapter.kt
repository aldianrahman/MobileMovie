package com.example.mobilemovie.adapter

import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mobilemovie.DetailMovie
import com.example.mobilemovie.R
import com.example.mobilemovie.models.Coming
import kotlinx.android.synthetic.main.coming_item.view.*
import java.text.SimpleDateFormat
import java.util.*

class ComingAdapter(
    private val comings: List<Coming>,
): RecyclerView.Adapter<ComingAdapter.ComingViewHolder>(){

    inner class ComingViewHolder(view: View): RecyclerView.ViewHolder(view){
        init {
            itemView.setOnClickListener{ v: View->
                val position:Int = adapterPosition
                Toast.makeText(itemView.context,
                    "You Click Coming Soon Movie #${comings[position].title}",
                    Toast.LENGTH_SHORT).show()
                val intent = Intent(itemView.context, DetailMovie::class.java)
                intent.putExtra("gambar", comings[position].poster)
                intent.putExtra("judul", comings[position].title)
                intent.putExtra("desc", comings[position].desc)
                intent.putExtra("popular", "Date Launch " + comings[position].star)
                itemView.context.startActivity(intent)
            }

        }
        private val IMAGE_BASE = "https://image.tmdb.org/t/p/w500/"
        @RequiresApi(Build.VERSION_CODES.O)
        fun bindComing(coming: Coming){
            itemView.coming_title.text = coming.title.toUpperCase()
            val outputFormat = SimpleDateFormat("dd MMM yyyy", Locale.US)
            val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
            val inputText = coming.star
            val date: Date = inputFormat.parse(inputText)
            val outputText: String = outputFormat.format(date)
            itemView.coming_star.text = " "+outputText

            Glide.with(itemView).load(IMAGE_BASE + coming.poster).into(itemView.coming_poster)
//
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComingViewHolder {
        return ComingViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.coming_item, parent, false)
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ComingViewHolder, position: Int) {
       holder.bindComing(comings.get(position))
    }

    override fun getItemCount(): Int = comings.size

}