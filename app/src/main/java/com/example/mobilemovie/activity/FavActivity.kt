package com.example.mobilemovie.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mobilemovie.adapter.FavoriteAdapter
import com.example.mobilemovie.R
import com.example.mobilemovie.db.entity.FavoriteEntity
import com.example.mobilemovie.db.RoomAppDb
import kotlinx.android.synthetic.main.activity_fav.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavActivity : AppCompatActivity() {
    val db by lazy { RoomAppDb(this) }
    lateinit var favoriteAdapter: FavoriteAdapter
    lateinit var preferences: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fav)

        supportActionBar!!.title= ("FAVORITE")


        preferences = getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)




        supportActionBar!!.setDisplayHomeAsUpEnabled(true)


        setupRV()

//        findViewById<FloatingActionButton>(R.id.btn_add_Favorite).setOnClickListener {
//            val intent = Intent(this, MainActivity::class.java)
//            this.startActivity(intent)
//        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_watchlist,menu)
        return true

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when(item.itemId){
            R.id.add_watchlist ->{
                val intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
                return true
            }
            else ->{
                val intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
                return true
            }
        }
    }

    override fun onStart() {
        super.onStart()
        CoroutineScope(Dispatchers.IO).launch {
            val iduser = preferences.getString("IDUSER", "")
            val fav = db.watchlistDao().getUserFavorite(iduser.toString().toInt())
            withContext(Dispatchers.Main){
                if (fav != null) {
                    favoriteAdapter.setDatafav(fav)
                }
            }
        }
    }

    private fun setupRV() {
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
        rv_Favorite.apply {
            layoutManager= LinearLayoutManager(applicationContext)
            adapter= favoriteAdapter
        }
    }
}


