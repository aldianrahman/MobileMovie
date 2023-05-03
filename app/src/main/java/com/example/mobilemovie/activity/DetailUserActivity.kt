package com.example.mobilemovie.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mobilemovie.*
import com.example.mobilemovie.adapter.FavoriteAdapter
import com.example.mobilemovie.adapter.WatchlistAdapter
import com.example.mobilemovie.db.RoomAppDb
import com.example.mobilemovie.db.entity.FavoriteEntity
import com.example.mobilemovie.db.entity.WatchlistEntity
import com.pixelcarrot.base64image.Base64Image
import kotlinx.android.synthetic.main.activity_detail_user.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailUserActivity : AppCompatActivity() {

    val db by lazy { RoomAppDb(this) }
    lateinit var favoriteAdapter: FavoriteAdapter
    lateinit var watchlistAdapter: WatchlistAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_user)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)



        val bundle: Bundle? = intent.extras
        val iduser = bundle?.get("id").toString()
        val nama = bundle?.get("nama").toString()
        val email = bundle?.get("nama").toString()
        prof_detail_email.text = email
        prof_detail_nama.text = nama

        CoroutineScope(Dispatchers.IO).launch {
            val gambar = db.watchlistDao().getStringImage(iduser.toInt())
            Base64Image.decode(gambar.gambar) { bitmap ->
                bitmap?.let {
                    prof_pic_detail.setImageBitmap(bitmap)
                }
            }
        }


        setupRVfav()
        setupRVwatch()
        setupCount()





    }

    private fun setupCount(){
        CoroutineScope(Dispatchers.IO).launch {
            val bundle: Bundle? = intent.extras
            val iduser = bundle?.get("id").toString()
            val getcountwa = db.watchlistDao().getUCW(iduser.toInt())
            val tv_count_w = findViewById<TextView>(R.id.rv_watchlist_detail)
            tv_count_w.text = "Watchlist : "+ getcountwa.toString()


            val getcountfa = db.watchlistDao().getUCF(iduser.toInt())
            val tv_count_f = findViewById<TextView>(R.id.rv_favorite_detail)
            tv_count_f.text = "Favorite : " + getcountfa.toString()
        }
    }

    override fun onStart() {
        super.onStart()
        CoroutineScope(Dispatchers.IO).launch {
            val bundle: Bundle? = intent.extras
            val iduser = bundle?.get("id").toString()
            val fav = db.watchlistDao().getUserFavorite(iduser.toString().toInt())
            withContext(Dispatchers.Main){
                if (fav != null) {
                    favoriteAdapter.setDatafav(fav)
                }
            }
        }

        CoroutineScope(Dispatchers.IO).launch {
            val bundle: Bundle? = intent.extras
            val iduser = bundle?.get("id").toString()
            val watchlists = db.watchlistDao().getUserWatchlist(iduser.toString().toInt())
            withContext(Dispatchers.Main){
                if (watchlists != null) {
                    watchlistAdapter.setData(watchlists)
                }
            }
        }
    }

    private fun setupRVwatch() {
        watchlistAdapter = WatchlistAdapter(arrayListOf(),
            object : WatchlistAdapter.OnAdapterListener {
                override fun onClick(watch: WatchlistEntity) {
                    CoroutineScope(Dispatchers.IO).launch {
                        db.watchlistDao().delWatchlist(watch)
                        val intent = Intent(applicationContext, WatchlistActivity::class.java)
                        startActivity(intent)
                    }
                    Toast.makeText(applicationContext, "Watchlist di Hapus", Toast.LENGTH_SHORT)
                        .show()
                }
            })
        rv_detail_watch.apply {
            layoutManager=LinearLayoutManager(applicationContext)
            adapter= watchlistAdapter
        }
    }


    private fun setupRVfav() {
        favoriteAdapter = FavoriteAdapter(arrayListOf(),
            object : FavoriteAdapter.OnAdapterListener {
                override fun onClick(fav: FavoriteEntity) {
                    CoroutineScope(Dispatchers.IO).launch {
                        db.watchlistDao().delFavorite(fav)
                        val intent = Intent(applicationContext, FavActivity::class.java)
                        startActivity(intent)
                    }
                    Toast.makeText(applicationContext, "Favorite di Hapus", Toast.LENGTH_SHORT)
                        .show()
                }
            })
        rv_detail_fav.apply {
            layoutManager= LinearLayoutManager(applicationContext)
            adapter= favoriteAdapter
        }
    }

}