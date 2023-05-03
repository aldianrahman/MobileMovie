package com.example.mobilemovie.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.example.mobilemovie.R
import com.example.mobilemovie.db.RoomAppDb
import com.pixelcarrot.base64image.Base64Image
import kotlinx.android.synthetic.main.activity_user.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserActivity : AppCompatActivity() {

    lateinit var preferences: SharedPreferences
    val db by lazy { RoomAppDb(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)


        val tv_email = findViewById<TextView>(R.id.email_tampil)
        val tv_password = findViewById<TextView>(R.id.password_tampil)
        val logout = findViewById<Button>(R.id.logout)



        preferences = getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)

        val userid = preferences.getString("IDUSER", "")
        id_tampil.text = userid
        val email = preferences.getString("EMAIL", "")
        tv_email.text = email
        val password = preferences.getString("PASSWORD", "")
        tv_password.text = "" + password //concate penggabungan string

        CoroutineScope(Dispatchers.IO).launch {
        val hasil = db.watchlistDao().getStringImage(userid.toString().toInt())
//            id_tampil_string.text =  hasil.gambar
            Base64Image.decode(hasil.gambar) { bitmap ->
                bitmap?.let {
                    string_bitmap.setImageBitmap(bitmap)
                }
            }
        }







        btn_home.setOnClickListener {
            val inten_home = Intent(this, MainActivity::class.java)
            inten_home.putExtra("EMAIL", email)
            startActivity(inten_home)
        }

        logout.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Apakah Anda Ingin Logout?")
            //builder.setPositiveButton("OK", DialogInterface.OnClickListener(function = x))

            builder.setPositiveButton("YA") { dialog, which ->
                val editor: SharedPreferences.Editor = preferences.edit() //get preff
                preferences = getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE) //get preff
                val intent_login = Intent(this, LoginActivity::class.java)
                this.startActivity(intent_login) ; Toast.makeText(applicationContext, "Logout Success!", Toast.LENGTH_SHORT).show();editor.clear()
                editor.apply()
            }
            builder.setNegativeButton("Tidak") { dialog, which ->
            }
            builder.show()
        }
            }


        }

