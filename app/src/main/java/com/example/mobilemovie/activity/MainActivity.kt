package com.example.mobilemovie.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.mobilemovie.*
import com.example.mobilemovie.adapter.MovieAdapter
import com.example.mobilemovie.adapter.SliderAdapter
import com.example.mobilemovie.db.RoomAppDb
import com.example.mobilemovie.fragment.ComingFragment
import com.example.mobilemovie.fragment.PopularFragment
import com.example.mobilemovie.fragment.RankFragment
import com.example.mobilemovie.models.Movie
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.pixelcarrot.base64image.Base64Image
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.header_nav.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList


class MainActivity() : AppCompatActivity() {


    lateinit var toggle: ActionBarDrawerToggle
    lateinit var preferences: SharedPreferences
    val db by lazy { RoomAppDb(this) }
    private var currentPage = 0
    private var numPages = 0
    private var itemAdapter : MovieAdapter? = null


//    private var itemAdapter : MovieAdapter? = null

    private var layoutManager : RecyclerView.LayoutManager? = null

    val arrayList = ArrayList<Movie>()
    val newList = ArrayList<Movie>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);


        supportActionBar?.apply {
            title = "HOME"

            setDisplayShowHomeEnabled(true)

            setDisplayUseLogoEnabled(false)
            setLogo(R.drawable.logo)
        }

        val IMAGE_BASE = "https://image.tmdb.org/t/p/w500/"
        val bundle: Bundle? = intent.extras


        val image = listOf(
            IMAGE_BASE + "wwemzKWzjKYJFfCeiB57q3r4Bcm.png",//netflix
            IMAGE_BASE + "/A3bsT0m1um6tvcmlIGxBwx9eAxn.jpg",
            IMAGE_BASE + "/egoyMDLqCxzjnSrWOz50uLlJWmD.jpg",
            IMAGE_BASE + "/aEGiJJP91HsKVTEPy1HhmN0wRLm.jpg"
//            IMAGE_BASE+gambar1,
//            IMAGE_BASE+gambar2,
//            IMAGE_BASE+gambar3
//


//            IMAGE_BASE+"/2n95p9isIi1LYTscTcGytlI4zYd.jpg",
//            IMAGE_BASE+"/tH1afdfqqrYTP3l2oqsHEsNN5ul.jpg"
        )

        val assets = listOf(
            R.drawable.logo,
            R.drawable.x,
            R.drawable.y,
            R.drawable.z
        )

//        createSlider(image) // use link

        createSlider(assets) // use draweble






        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.CAMERA),
                111)

        }else{
            val navHeader = findViewById<NavigationView>(R.id.nav_view)
            val headerView = navHeader.getHeaderView(0)
            val prof_pic = headerView.findViewById<ImageView>(R.id.prof_pic)
            val btnUse = headerView.findViewById<Button>(R.id.btn_usephoto)
            val imgclear = headerView.findViewById<ImageView>(R.id.img_clear)

            btnUse.visibility = View.INVISIBLE
            imgclear.visibility = View.INVISIBLE


            prof_pic.setOnClickListener {
                var i = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(i, 101)
            }

        }




        fragment()
        sidebarmenu()


        preferences = getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)

        val email = preferences.getString("EMAIL", "")
        val iduser = preferences.getString("IDUSER", "")
        val nama = preferences.getString("NAMA", "")
        val navHeader = findViewById<NavigationView>(R.id.nav_view)
        val headerView = navHeader.getHeaderView(0)
        val tv_c_watch = headerView.findViewById<TextView>(R.id.tv_count_watchlist)
        val tv_c_fav = headerView.findViewById<TextView>(R.id.tv_count_favorite)
        val prof_pic = headerView.findViewById<ImageView>(R.id.prof_pic)
        val clickUse = headerView.findViewById<Button>(R.id.btn_usephoto)
        val clearimg = headerView.findViewById<ImageView>(R.id.img_clear)


        if (email==""){
            tv_c_fav.visibility = View.GONE
            tv_c_watch.visibility = View.GONE
            prof_pic.visibility = View.GONE
            clickUse.visibility = View.GONE
            clearimg.visibility = View.GONE

        }else{
            val iduser = preferences.getString("IDUSER", "")
            CoroutineScope(Dispatchers.IO).launch {
                val hasil = db.watchlistDao().getStringImage(iduser.toString().toInt())
                Base64Image.decode(hasil.gambar) { bitmap ->
                    bitmap?.let {
                        test_IV.setImageBitmap(bitmap)
                        img_clear.visibility = View.VISIBLE
                        test_IV.visibility = View.VISIBLE
                        clearimg()
                    }
                }
            }

            tv_c_fav.setOnClickListener {
                val intent_fav = Intent(this, FavActivity::class.java)
                startActivity(intent_fav)
            }

            tv_c_watch.setOnClickListener {
                val intent_watch = Intent(this, WatchlistActivity::class.java)
                startActivity(intent_watch)
            }

        }
        val EView = headerView.findViewById<TextView>(R.id.email_view)
        EView.text = nama

        EView.setOnClickListener{
            val intent = Intent(applicationContext, UserActivity::class.java)
            startActivity(intent)
        }

        val default = "Anda Belum Login"
        if (email == ""){
            tv_email_view.text =default

        }
        else{
            tv_email_view.text = "Hai, " + nama
            tv_email_view.setOnClickListener{
                val intent_profile = Intent(this, UserActivity::class.java)
                startActivity(intent_profile)
            }

            CoroutineScope(Dispatchers.IO).launch {
                val getcountwa = db.watchlistDao().getUCW(iduser.toString().toInt())
                val tv_count_w = headerView.findViewById<TextView>(R.id.tv_count_watchlist)
                tv_count_w.text = "Watchlist : "+ getcountwa.toString()
                val getcountfa = db.watchlistDao().getUCF(iduser.toString().toInt())
                val tv_count_f = headerView.findViewById<TextView>(R.id.tv_count_favorite)
                tv_count_f.text = "Favorite : " + getcountfa.toString()
            }
        }

    }









//    private fun createSlider(string: List<String>) {
    private fun createSlider(string: List<Int>) {

        vpSlider.adapter = SliderAdapter(this, string)
        indicator.setViewPager(vpSlider)
        val density = resources.displayMetrics.density
        //Set circle indicator radius
        indicator.radius = 5 * density
        numPages = string.size
        // Auto getData of viewpager
        val update = Runnable {
            if (currentPage === numPages) {
                currentPage = 0
            }
            vpSlider.setCurrentItem(currentPage++, true)
        }
        val swipeTimer = Timer()
        swipeTimer.schedule(object : TimerTask() {
            override fun run() {
                Handler(Looper.getMainLooper()).post(update)
            }
        }, 2500, 2500)
        // Pager listener over indicator
        indicator.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageSelected(position: Int) {
                currentPage = position
            }

            override fun onPageScrolled(pos: Int, arg1: Float, arg2: Int) {}
            override fun onPageScrollStateChanged(pos: Int) {}
        })
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == 111 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
        {

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


        if (requestCode == 101){
            var pic = data?.getParcelableExtra<Bitmap>("data")
            var hasil = ""
            test_IV.setImageBitmap(pic)
            Base64Image.encode(pic) { base64 ->
                base64.let {
                    hasil = base64.toString()
                }

            }
            prof_pic.visibility = View.VISIBLE
            btn_usephoto.visibility = View.VISIBLE
            clearimg()

            test_IV.setOnClickListener {
                var i = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(i, 101)
            }

            btn_usephoto.setOnClickListener {
                if (hasil==null){
                    Toast.makeText(applicationContext, "NULL", Toast.LENGTH_SHORT).show()
                }else{
                    val iduser = preferences.getString("IDUSER", "")
                    CoroutineScope(Dispatchers.IO).launch {
                        if (iduser != null) {
                            db.watchlistDao().updUserImage(hasil, iduser.toInt())
                        }
                    }
                    Toast.makeText(applicationContext,
                        "Gambar Berhasil Di Gunakan",
                        Toast.LENGTH_SHORT).show()
                    btn_usephoto.visibility = View.INVISIBLE
                    img_clear.visibility = View.VISIBLE
                    clearimg()
                }

            }

        }

    }

    private fun clearimg(){
        img_clear.setOnClickListener {
            val iduser = preferences.getString("IDUSER", "")
            CoroutineScope(Dispatchers.IO).launch {
                if (iduser != null) {
                    db.watchlistDao().updUserImage("", iduser.toInt())
                }
            }
            Toast.makeText(applicationContext, "Gambar Berhasil Di Hapus", Toast.LENGTH_SHORT).show()
            prof_pic.visibility = View.VISIBLE
            test_IV.visibility = View.INVISIBLE
            img_clear.visibility = View.INVISIBLE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        preferences = getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
        val email = preferences.getString("EMAIL", "")
        menuInflater.inflate(R.menu.menu_actionbar, menu)

        if (email==""){
            menu?.findItem(R.id.ab_logout)?.isVisible = false
        }else{
            menu?.findItem(R.id.ab_login)?.isVisible = false
        }

        val searchItem = menu?.findItem(R.id.search)
        val searchView = searchItem?.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query!!.isNotEmpty()) {
                    Toast.makeText(applicationContext,
                        "Pencarian : "+query+" Ditemukan",
                        Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(applicationContext, "Pencarian Berakhir", Toast.LENGTH_SHORT).show()
                }
//                itemAdapter!!.getFilter).filter(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                if (newText!!.isNotEmpty()) {
                    Toast.makeText(applicationContext,
                        "Pencarian Sedang Berlangsung...",
                        Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(applicationContext, "Pencarian Berakhir", Toast.LENGTH_SHORT).show()
                }
//                itemAdapter!!.getFilter().filter(newText);
                return true
            }

        })

//        val manager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
//        val searchItem = menu?.findItem(R.id.search)
//        val searchView = searchItem?.actionView as SearchView
//
//        searchView.setSearchableInfo(manager.getSearchableInfo(componentName))
//
//        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                searchView.clearFocus()
//                searchView.setQuery("", false)
//                searchItem.collapseActionView()
//                Toast.makeText(this@MainActivity, "Looking for $query", Toast.LENGTH_SHORT).show()
//                return true
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//                if (newText!!.isNotEmpty()){
//
//                    newList.clear()
//                    val search = newText.toLowerCase(Locale.getDefault())
//                    newList.forEach {
//                        if(it.title.toLowerCase(Locale.getDefault()).contains(search)){
//                            newList.add(it)
//                        }
//                    }
//                    rv_movie.adapter!!.notifyDataSetChanged()
//                }else{
//                    newList.clear()
//                    newList.addAll(arrayList)
//                    rv_movie.adapter!!.notifyDataSetChanged()
//                }
//
//
//
//                return true
//            }
//
//        })



        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.rate -> {
                Toast.makeText(this, "CLICKED RATE APP", Toast.LENGTH_SHORT).show()
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
            R.id.ab_login -> {
                val intent_login = Intent(this, LoginActivity::class.java)
                startActivity(intent_login)
                return true
            }
            R.id.ab_logout -> {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Apakah Anda Ingin Logout?")
                //builder.setPositiveButton("OK", DialogInterface.OnClickListener(function = x))

                builder.setPositiveButton("YA") { dialog, which ->
                    val editor: SharedPreferences.Editor = preferences.edit()
                    preferences = getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
                    val intent_login = Intent(this, LoginActivity::class.java)
                    this.startActivity(intent_login); Toast.makeText(applicationContext,
                    "Logout Success!",
                    Toast.LENGTH_SHORT).show();editor.clear()
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





    private fun fragment(){
        val btm = findViewById<BottomNavigationView>(R.id.btm_nav)
        btm.setOnNavigationItemSelectedListener(onBottomNavListener)
        var fr =supportFragmentManager.beginTransaction()
        fr.add(R.id.fl_fragment, PopularFragment())
        fr.commit()
    }



    private val onBottomNavListener = BottomNavigationView.OnNavigationItemSelectedListener { i->

        var selectedFragment : Fragment = PopularFragment()

        when(i.itemId){
            R.id.item_popular -> {
                selectedFragment = PopularFragment()
            }

            R.id.item_rank -> {
                selectedFragment = RankFragment()
            }

            R.id.item_coming -> {
                selectedFragment = ComingFragment()
            }
        }

        var fr =supportFragmentManager.beginTransaction()
        fr.replace(R.id.fl_fragment, selectedFragment)
        fr.commit()

        true }

    private fun sidebarmenu(){
        val drawerLayout : DrawerLayout = findViewById(R.id.drawerLayout)
        val navView : NavigationView = findViewById(R.id.nav_view)



        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)




        navView.setNavigationItemSelectedListener {
            val intent_home = Intent(this, MainActivity::class.java)
            val intent_watchlist = Intent(this, WatchlistActivity::class.java)
            val intent_login = Intent(this, LoginActivity::class.java)
            val intent_fav = Intent(this, FavActivity::class.java)
            val intent_user = Intent(this, UserActivity::class.java)
            val intent_signup = Intent(this, DaftarActivity::class.java)


            val editor: SharedPreferences.Editor = preferences.edit()
            preferences = getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
            val email = preferences.getString("EMAIL", "")

            val navHeader = findViewById<NavigationView>(R.id.nav_view)
            val headerView = navHeader.getHeaderView(0)
            val EView = headerView.findViewById<TextView>(R.id.email_view)
            EView.text = email




            when(it.itemId){
                R.id.item_home -> this.startActivity(intent_home)
                R.id.item_profile -> {
                    if (email == "") {
                        Toast.makeText(this, "Silahkan Login", Toast.LENGTH_SHORT)
                            .show();startActivity(
                            intent_login)
                    } else {
                        this.startActivity(intent_user);
                    }
                }
                R.id.item_watchlist -> {
                    if (email == "") {
                        Toast.makeText(this,
                            "Anda Harus Login Terlebih Dahulu!",
                            Toast.LENGTH_SHORT).show()
                    } else {
                        this.startActivity(intent_watchlist);
                    }
                }
                R.id.item_favorite -> {
                    if (email == "") {
                        Toast.makeText(this,
                            "Anda Harus Login Terlebih Dahulu!",
                            Toast.LENGTH_SHORT).show()
                    } else {
                        this.startActivity(intent_fav);
                    }
                }
                R.id.item_versiapp -> Toast.makeText(applicationContext,
                    "Application Version 1.0",
                    Toast.LENGTH_SHORT).show()
                R.id.item_signup ->
                    if (email == "") {
                        this.startActivity(intent_signup)
                    } else {
                        Toast.makeText(this, "Anda Sudah Login", Toast.LENGTH_SHORT).show()
                    }
                R.id.item_login -> this.startActivity(intent_login)
                R.id.item_logout -> {
                    preferences = getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
                    val email = preferences.getString("EMAIL", "")
                    if (email == "") {
                        Toast.makeText(applicationContext, "Anda Belum Login!", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        val builder = AlertDialog.Builder(this)
                        builder.setTitle("Apakah Anda Ingin Logout?")
                        //builder.setPositiveButton("OK", DialogInterface.OnClickListener(function = x))

                        builder.setPositiveButton("YA") { dialog, which ->
                            val editor: SharedPreferences.Editor = preferences.edit()
                            preferences = getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
                            val intent_login = Intent(this, LoginActivity::class.java)
                            this.startActivity(intent_login); Toast.makeText(applicationContext,
                            "Logout Success!",
                            Toast.LENGTH_SHORT).show();editor.clear()
                            editor.apply()
                        }
                        builder.setNegativeButton("Tidak") { dialog, which ->
                        }
                        builder.show()
                    }

                }
            }
            true

        }

    }

}

