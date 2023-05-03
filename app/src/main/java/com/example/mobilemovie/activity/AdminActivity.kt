package com.example.mobilemovie.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mobilemovie.adapter.AdminAdapter
import com.example.mobilemovie.R
import com.example.mobilemovie.db.RoomAppDb
import com.example.mobilemovie.db.entity.UserEntity
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_admin.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AdminActivity : AppCompatActivity() {


    val db by lazy { RoomAppDb(this) }
    lateinit var adminAdapter: AdminAdapter
    lateinit var preferences: SharedPreferences
    lateinit var toggle: ActionBarDrawerToggle


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)
        preferences = getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
        supportActionBar!!.title= ("ADMIN ACTIVITY")

        setupRV()
        sidebarmenu()



        val navHeader = findViewById<NavigationView>(R.id.nav_view1)
        val headerView = navHeader.getHeaderView(0)
        val btnUse = headerView.findViewById<Button>(R.id.btn_usephoto)
        btnUse.visibility = View.GONE
        headerView.findViewById<TextView>(R.id.email_view).text = "ADMIN"

        headerView.findViewById<ImageView>(R.id.img_clear) .visibility = View.GONE
    }

    override fun onStart() {
        super.onStart()
        CoroutineScope(Dispatchers.IO).launch {
            val users = db.watchlistDao().getUser()
            withContext(Dispatchers.Main){
                if (users != null) {
                    adminAdapter.setData(users)
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_actionbar,menu)
            menu?.findItem(R.id.ab_logout)?.isVisible = false
            menu?.findItem(R.id.ab_login)?.isVisible = false
            menu?.findItem(R.id.rate)?.isVisible = false
            menu?.findItem(R.id.share)?.isVisible = false
            menu?.findItem(R.id.search)?.isVisible = false
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.rate -> {
                Toast.makeText(this , "CLICKED RATE APP", Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.share -> {
                Toast.makeText(this, "CLICKED SHARE APP", Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.search -> {
                Toast.makeText(this, "CLICKED SEARCH", Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.ab_login ->{
                val intent_login = Intent(this, LoginActivity::class.java)
                startActivity(intent_login)
                return true
            }
            R.id.ab_logout ->{
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Apakah Anda Ingin Logout?")
                //builder.setPositiveButton("OK", DialogInterface.OnClickListener(function = x))

                builder.setPositiveButton("YA") { dialog, which ->
                    val editor: SharedPreferences.Editor = preferences.edit()
                    preferences = getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
                    val intent_login = Intent(this, LoginActivity::class.java)
                    this.startActivity(intent_login) ; Toast.makeText(applicationContext, "Logout Success!", Toast.LENGTH_SHORT).show();editor.clear()
                    editor.apply()
                }

                builder.setNegativeButton("Tidak") { dialog, which ->
                }
                builder.show()
                return true
            }
            else-> toggle.onOptionsItemSelected(item)
        }
//        return toggle.onOptionsItemSelected(item)


    }

    private fun sidebarmenu(){
        val drawerLayout : DrawerLayout = findViewById(R.id.drawerLayout1)
        val navView : NavigationView = findViewById(R.id.nav_view1)



        toggle = ActionBarDrawerToggle(this,drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)



        navView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.item_home_admin -> Toast.makeText(applicationContext, "CLICKED HOME", Toast.LENGTH_SHORT).show()
                R.id.item_list_user -> Toast.makeText(applicationContext, "CLICKED LIST USER", Toast.LENGTH_SHORT).show()
                R.id.item_versiapp_admin -> Toast.makeText(applicationContext, "Application Version 1.0", Toast.LENGTH_SHORT).show()
                R.id.item_login_admin -> {
                    val i = Intent(applicationContext, LoginActivity::class.java)
                    startActivity(i)
                }
                R.id.item_logout_admin -> {
                    val builder = AlertDialog.Builder(this)
                    builder.setTitle("Apakah Admin Ingin Logout?")
                    //builder.setPositiveButton("OK", DialogInterface.OnClickListener(function = x))

                    builder.setPositiveButton("YA") { dialog, which ->
                        val editor: SharedPreferences.Editor = preferences.edit()
                        preferences = getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
                        val intent_login = Intent(this, MainActivity::class.java)
                        this.startActivity(intent_login); Toast.makeText(applicationContext,
                        "Logout Admin Success!",
                        Toast.LENGTH_SHORT).show();editor.clear()
                        editor.apply()
                    }
                    builder.setNegativeButton("Tidak") { dialog, which ->
                    }
                    builder.show()
                }
            }
            true

        }

    }


    private fun setupRV() {
        adminAdapter = AdminAdapter(arrayListOf(),
            object : AdminAdapter.OnAdapterListener {
                override fun onDel(user: UserEntity) {
                    CoroutineScope(Dispatchers.IO).launch {
                        db.watchlistDao().delUser(user)
                        val intent = Intent(applicationContext, AdminActivity::class.java)
                        startActivity(intent)
                    }
                }

                override fun onUpd(user: UserEntity) {
                    val intent = Intent(applicationContext, AdminActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(applicationContext, "Data Berhasil di Update", Toast.LENGTH_SHORT).show()
                }


            })
        rv_user.apply {
            layoutManager=LinearLayoutManager(applicationContext)
            adapter= adminAdapter
        }
    }








}





