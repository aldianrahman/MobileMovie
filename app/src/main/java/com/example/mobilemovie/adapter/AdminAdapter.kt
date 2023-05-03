package com.example.mobilemovie.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mobilemovie.R
import com.example.mobilemovie.activity.DetailUserActivity
import com.example.mobilemovie.db.RoomAppDb
import com.example.mobilemovie.db.entity.UserEntity
import com.pixelcarrot.base64image.Base64Image
import kotlinx.android.synthetic.main.user_item.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class AdminAdapter (private val admins:ArrayList<UserEntity>, private val listener : OnAdapterListener) : RecyclerView.Adapter<AdminAdapter.AdminViewHolder>() {


    inner class AdminViewHolder(val view: View) : RecyclerView.ViewHolder(view){init {
        itemView.setOnClickListener{
            val intent = Intent (itemView.context, DetailUserActivity::class.java)
            intent.putExtra("id", admins[position].id)
            intent.putExtra("nama", admins[position].name)
            intent.putExtra("email", admins[position].email)
            itemView.context.startActivity(intent)
        }
    }}





    fun setData(list:List<UserEntity>){
        admins.clear()
        admins.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdminViewHolder {
        return AdminViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.user_item,parent,false)
        )
    }

    override fun onBindViewHolder(holder: AdminViewHolder, position: Int) {
        val admin = admins[position]
        holder.view.id_user.text = admin.id.toString()
        holder.view.email_user.text = admin.email
        holder.view.nama_user.text = admin.name
        holder.view.password_user.text = admin.password
        holder.view.isi_detail_email.setText(admin.email)
        holder.view.isi_detail_nama.setText(admin.name)
        holder.view.isi_detail_password.setText(admin.password)





        if (admin.gambar!=null){
            Base64Image.decode(admin.gambar) { bitmap ->
                bitmap?.let {
                    holder.view.iv_image_string.setImageBitmap(bitmap)
                }
            }
        }else{
            Base64Image.decode("") { bitmap ->
                bitmap?.let {
                    holder.view.iv_image_string.setImageBitmap(bitmap)
                }
            }
        }


//        holder.view.watchlis_desc.text = watch.value
        holder.view.iv_close.visibility = View.INVISIBLE

        holder.view.iv_open.setOnClickListener{
            holder.view.iv_close.visibility = View.VISIBLE
            holder.view.iv_open.visibility = View.INVISIBLE
            holder.view.expand.visibility = View.VISIBLE
        }

        holder.view.iv_close.setOnClickListener {
            holder.view.iv_open.visibility = View.VISIBLE
            holder.view.iv_close.visibility =View.INVISIBLE
            holder.view.expand.visibility = View.GONE
            holder.view.expand_edit.visibility = View.GONE
        }

        holder.view.hapus_user.setOnClickListener {
            listener.onDel(admin)
        }

        holder.view.edit_user.setOnClickListener {
            holder.view.expand_edit.visibility = View.VISIBLE

        }

//        holder.view.edit_user.setOnClickListener {
//            holder.view.expand_edit.visibility = View.GONE // gimana cara ulang ke atas
//        }


        holder.view.tv_edit_tutup.setOnClickListener {
            holder.view.expand_edit.visibility = View.GONE
        }

        holder.view.tv_edit_update.setOnClickListener {
            val db by lazy { RoomAppDb(holder.view.context) }

            CoroutineScope(Dispatchers.IO).launch {
                db.watchlistDao().updUsersEmail(
                    holder.view.isi_detail_email.text.toString(),admin.id
                )
            }
            CoroutineScope(Dispatchers.IO).launch {
                db.watchlistDao().updUsersName(
                    holder.view.isi_detail_nama.text.toString(),admin.id
                )
            }
            CoroutineScope(Dispatchers.IO).launch {
                db.watchlistDao().updUsersPass(
                    holder.view.isi_detail_password.text.toString(),admin.id
                )
            }
            listener.onUpd(admin)
        }




    }

    override fun getItemCount() = admins.size

    interface OnAdapterListener{
        fun onDel (user: UserEntity)
        fun onUpd (user: UserEntity)

    }



}


