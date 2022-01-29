package com.example.justchat.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.justchat.R
import com.example.justchat.UserData
import com.example.justchat.activities.ChatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.user_layout.view.*


class UserAdapter(
    val context : Context,
    val list : ArrayList<UserData>
) : RecyclerView.Adapter<UserAdapter.userViewholder>() {

    inner class userViewholder(itemView : View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): userViewholder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_layout,parent,false)
        return userViewholder(view)
    }
    override fun onBindViewHolder(holder: userViewholder, position: Int) {
        holder.itemView.apply {
            tvUserName.text = list[position].name
        }
        holder.itemView.setOnClickListener{
            val intent = Intent(context, ChatActivity::class.java)
            intent.putExtra("name",list[position].name)
            intent.putExtra("uid", list[position].uid)
            context.startActivity(intent)
        }
    }
    override fun getItemCount(): Int {
        return list.size
    }
}