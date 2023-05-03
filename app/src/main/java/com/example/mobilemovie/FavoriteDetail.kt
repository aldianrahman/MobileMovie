package com.example.mobilemovie

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.mobilemovie.activity.FavActivity
import com.example.mobilemovie.adapter.WatchlistAdapter
import com.example.mobilemovie.db.RoomAppDb
import com.example.mobilemovie.db.entity.FavoriteEntity
import kotlinx.android.synthetic.main.activity_favorite_detail.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteDetail : AppCompatActivity() {

    val db by lazy { RoomAppDb(this) }
    lateinit var watchlistAdapter: WatchlistAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite_detail)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)


        val hapus : ImageView = findViewById(R.id.iv_hapus_favorite)
        val play : ImageView = findViewById(R.id.iv_play_favorite)


        val iv_gambar = findViewById<ImageView>(R.id.img_movie_favorite)
        val tv_judul = findViewById<TextView>(R.id.judul_favorite)
        val tv_desc = findViewById<TextView>(R.id.overviewitem_favorite)
        val tv_star = findViewById<TextView>(R.id.star_favorite)
        val bundle: Bundle? = intent.extras
        val gambar = bundle?.get("gambar").toString()
        val judul = bundle?.get("judul").toString()
        val desc = bundle?.get("desc").toString()
        val id_favorite = bundle?.get("id").toString()
        val star = bundle?.get("popular").toString()

        val IMAGE_BASE = "https://image.tmdb.org/t/p/w500/"
        Glide.with(iv_gambar).load(IMAGE_BASE+ gambar).into(iv_gambar)
        tv_judul.text = judul
        tv_desc.text = desc
        tv_star.text = star

        iv_hapus_favorite.setOnClickListener{
            CoroutineScope(Dispatchers.IO).launch {
                db.watchlistDao().delFavorite(FavoriteEntity(id_favorite.toInt(),"","",0,""))// BELUM KETEMU
                val intent = Intent(applicationContext, FavActivity::class.java)
                startActivity(intent)
            }
            Toast.makeText(this, "${judul.toString()} di Hapus dari Favorite", Toast.LENGTH_SHORT).show()

        }


    }
}