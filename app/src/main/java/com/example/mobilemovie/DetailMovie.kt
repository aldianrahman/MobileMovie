package com.example.mobilemovie

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.mobilemovie.activity.FavActivity
import com.example.mobilemovie.activity.WatchlistActivity
import com.example.mobilemovie.db.RoomAppDb
import com.example.mobilemovie.db.entity.FavoriteEntity
import com.example.mobilemovie.db.entity.WatchlistEntity
import kotlinx.android.synthetic.main.activity_detail_movie.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailMovie : AppCompatActivity() {

    lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_movie)
        val watchlist : Button = findViewById(R.id.btn_watchlist)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)




        val IMAGE_BASE = "https://image.tmdb.org/t/p/w500/"



        val iv_gambar = findViewById<ImageView>(R.id.img_movie)
        val tv_judul = findViewById<TextView>(R.id.judul)
        val tv_desc = findViewById<TextView>(R.id.overview)
        val tv_star = findViewById<TextView>(R.id.star)
        val bundle: Bundle? = intent.extras
        val gambar = bundle?.get("gambar").toString()
        val judul = bundle?.get("judul").toString()
        val desc = bundle?.get("desc")
        val star = bundle?.get("popular").toString()

        Glide.with(iv_gambar).load(IMAGE_BASE+ gambar).into(iv_gambar)
        tv_judul.text = judul.toString()
        tv_desc.text = desc.toString()
        tv_star.text = star.toString()


        preferences = getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)

        val email = preferences.getString("EMAIL", "")
        val iduser = preferences.getString("IDUSER", "")


        watchlist.setOnClickListener{
            if (email != ""){
                CoroutineScope(Dispatchers.IO).launch {
                    RoomAppDb(applicationContext).watchlistDao()
                        ?.addWatchlist(WatchlistEntity(0,judul,star,iduser?.toInt(),gambar))
                    finish()

                }

                Toast.makeText(this, "Watchlist ${judul.toString()} Tersimpan", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, WatchlistActivity::class.java)
//            intent.putExtra("judul","${judul}")
//            intent.putExtra("desc","${desc}")
//
                this.startActivity(intent)
            }else{
                Toast.makeText(this , "Anda Harus Login Terlebih Dahulu!", Toast.LENGTH_SHORT).show()
            }

        }

        btn_favorite.setOnClickListener{
            if (email != ""){
                CoroutineScope(Dispatchers.IO).launch {
                RoomAppDb(applicationContext).watchlistDao()
                    ?.addFavorite(FavoriteEntity(0,judul,star,iduser?.toInt(),gambar))
                finish()

            }
                Toast.makeText(this, "Favorite ${judul.toString()} Tersimpan", Toast.LENGTH_SHORT).show()
                val intent_fav = Intent(this, FavActivity::class.java)
                startActivity(intent_fav)}else{
                Toast.makeText(this , "Anda Harus Login Terlebih Dahulu!", Toast.LENGTH_SHORT).show()
            }

        }






    }
}