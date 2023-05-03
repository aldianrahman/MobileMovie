package com.example.mobilemovie

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.mobilemovie.activity.WatchlistActivity
import com.example.mobilemovie.adapter.WatchlistAdapter
import com.example.mobilemovie.db.RoomAppDb
import com.example.mobilemovie.db.entity.WatchlistEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ItemDetail : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val db by lazy { RoomAppDb(this) }
        lateinit var watchlistAdapter: WatchlistAdapter
        lateinit var preferences: SharedPreferences




        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_detail)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)


        val hapus : ImageView = findViewById(R.id.iv_hapus)
        val play : ImageView = findViewById(R.id.iv_play)


        val iv_gambar = findViewById<ImageView>(R.id.img_item_movie)
        val tv_judul = findViewById<TextView>(R.id.judul_item)
        val tv_desc = findViewById<TextView>(R.id.overview_item)
        val tv_star = findViewById<TextView>(R.id.star_item)
        val bundle: Bundle? = intent.extras
        val gambar = bundle?.get("gambar").toString()
        val judul = bundle?.get("judul").toString()
        val desc = bundle?.get("desc").toString()
        val id_watchlist = bundle?.get("id").toString()
        val star = bundle?.get("popular").toString()

        val IMAGE_BASE = "https://image.tmdb.org/t/p/w500/"
        Glide.with(iv_gambar).load(IMAGE_BASE+ gambar).into(iv_gambar)
        tv_judul.text = judul
        tv_desc.text = desc
        tv_star.text = star



        hapus.setOnClickListener{
            CoroutineScope(Dispatchers.IO).launch {
                db.watchlistDao().delWatchlist(WatchlistEntity(id_watchlist.toInt(),"","",0,""))
                val intent = Intent(applicationContext, WatchlistActivity::class.java)
                startActivity(intent)
            }
            Toast.makeText(this, "${judul.toString()} di Hapus dari Watchlist", Toast.LENGTH_SHORT).show()

        }

    }
}


