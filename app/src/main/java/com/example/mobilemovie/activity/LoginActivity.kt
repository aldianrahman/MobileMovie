package com.example.mobilemovie.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.example.mobilemovie.R
import com.example.mobilemovie.db.RoomAppDb
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    lateinit var sharedPreferences: SharedPreferences
    var isRemembered = false
    val db by lazy { RoomAppDb(this) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)



        supportActionBar!!.setDisplayHomeAsUpEnabled(true)


        val button = findViewById<Button>(R.id.masuk_login)
        val et_email = findViewById<EditText>(R.id.email_login)
        val et_password = findViewById<EditText>(R.id.password_login)
        val checkBox = findViewById<CheckBox>(R.id.checkBox)



        sharedPreferences = getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)

        isRemembered = sharedPreferences.getBoolean("CHECKBOX", false) // kenapa ini false??
        val email = sharedPreferences.getString("EMAIL", "")





        if (isRemembered) {
            val intent = Intent (this, UserActivity::class.java)
            startActivity(intent)
            finish()
        }

        button.setOnClickListener{
            val email: String = et_email.text.toString()
            val pass: String = et_password.text.toString()
            val checked: Boolean = checkBox.isChecked



            when{
                email=="admin"&&pass=="rahasia"->{
                    val intent_admin = Intent(this, AdminActivity::class.java)
                    startActivity(intent_admin)
                }
                email.isEmpty()->{
                    et_email.error = "Masukan Email"
                }
                pass.isEmpty()->{
                    et_password.error = "Masukan Password"
                }
                !email.isEmpty() && !pass.isEmpty()->{
                    CoroutineScope(Dispatchers.IO).launch {
                        val getLogin = db.watchlistDao().getUserLogin(email,pass) //deklarasi
                        if (getLogin!=null){
                            val editor: SharedPreferences.Editor = sharedPreferences.edit()
                            editor.putString("IDUSER",getLogin.id.toString())
                            editor.putString("NAMA", getLogin.name)
                            editor.putString("EMAIL", email)
                            editor.putString("PASSWORD", pass)
                            editor.putBoolean("CHECKBOX", checked)
                            editor.apply()
                            val intent = Intent(applicationContext, UserActivity::class.java)
                            startActivity(intent)
                            finish()
                        }else{
                            CoroutineScope(Dispatchers.Main).launch {
                                Toast.makeText(applicationContext, "Email atau Password Anda Salah!", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            }

        }
    }
}
