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
import com.example.mobilemovie.R
import com.example.mobilemovie.adapter.WatchlistAdapter
import com.example.mobilemovie.db.RoomAppDb
import com.example.mobilemovie.db.entity.WatchlistEntity
import kotlinx.android.synthetic.main.activity_watchlist.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WatchlistActivity : AppCompatActivity() {


    val db by lazy { RoomAppDb(this) }
    lateinit var watchlistAdapter: WatchlistAdapter
    lateinit var preferences: SharedPreferences



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_watchlist)

        supportActionBar!!.title= ("WATCHLIST")


        preferences = getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
        val iduser = preferences.getString("IDUSER", "")


        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

            setupRV()


//        CoroutineScope(Dispatchers.IO).launch {
//            val getcountwa = db.watchlistDao().getUCW(iduser.toString().toInt())
//        if (getcountwa==0){
//            findViewById<FloatingActionButton>(R.id.btn_add_watchlist).setOnClickListener {
//                val intent = Intent(applicationContext, MainActivity::class.java)
//                startActivity(intent)
//            }
//        }else{
//            btn_add_watchlist.visibility =View.GONE
//        }
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
            val watchlists = db.watchlistDao().getUserWatchlist(iduser.toString().toInt())
            withContext(Dispatchers.Main){
                if (watchlists != null) {
                    watchlistAdapter.setData(watchlists)
                }
            }
        }
    }

    private fun setupRV() {
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
        rv_watchlist.apply {
            layoutManager=LinearLayoutManager(applicationContext)
            adapter= watchlistAdapter
        }
    }








}



