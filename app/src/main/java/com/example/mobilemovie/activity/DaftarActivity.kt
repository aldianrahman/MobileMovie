package com.example.mobilemovie.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import com.example.mobilemovie.R
import com.example.mobilemovie.db.RoomAppDb
import com.example.mobilemovie.db.entity.UserEntity
import kotlinx.android.synthetic.main.activity_daftar.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DaftarActivity : AppCompatActivity() {
    val db by lazy { RoomAppDb(this) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daftar)

        val et_nama = findViewById<EditText>(R.id.nama_daftar)
        val et_email = findViewById<EditText>(R.id.email_daftar)
        val et_password = findViewById<EditText>(R.id.password_daftar)

        
        btn_daftar.setOnClickListener {
            val nama : String = et_nama.text.toString()
            val email :String = et_email.text.toString()
            val password :String = et_password.text.toString()

            when{
                email.isEmpty() -> {et_email.error = "Masukan Email"}
                nama.isEmpty() -> {et_nama.error = "Masukan Nama"}
                password.isEmpty()->{et_password.error = "Masukan Password"}
                nama.isNotEmpty() && password.isNotEmpty() && email.isNotEmpty()->{
                    CoroutineScope(Dispatchers.IO).launch {
                        val getSignup = db.watchlistDao().getSignup(email)
                        if (getSignup!=null){
                            CoroutineScope(Dispatchers.Main).launch {
                                et_email.error = "Email Telah Terdaftar"
                                Toast.makeText(applicationContext, "EMAIL TELAH TERDAFTAR", Toast.LENGTH_SHORT).show()
                            }
                        }else{
                            CoroutineScope(Dispatchers.Main).launch {
                                Toast.makeText(applicationContext, "Pendaftaran Berhasil Silahkan Login", Toast.LENGTH_SHORT).show()
                            }
                            val intent_daftar = Intent(applicationContext, LoginActivity::class.java)
                                db.watchlistDao().addUser(UserEntity(0, email,nama,password,""))
                                finish()
                                startActivity(intent_daftar)

                        }
                    }
                }
            }
        }
        
    }
}




