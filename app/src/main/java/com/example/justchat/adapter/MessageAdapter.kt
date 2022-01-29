package com.example.justchat.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.justchat.Message
import com.example.justchat.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.recieved_messsage.view.*
import kotlinx.android.synthetic.main.send_message.view.*

class MessageAdapter(
    val messageList : ArrayList<Message>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    inner class SentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {}
    inner class RecieveViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {}

    val ITEM_SENT = 1
    val ITEM_RECIEVE = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        if(viewType == 1)
        {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.send_message,parent,false)
            return SentViewHolder(view)
        }
        else
        {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.recieved_messsage,parent,false)
            return RecieveViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder.javaClass == SentViewHolder::class.java) {
            val viewHolder = holder as SentViewHolder
            holder.itemView.apply {
                sent_message.text = messageList[position].message
            }
        } else {
            val viewHolder = holder as RecieveViewHolder
            holder.itemView.apply {
                recieve_message.text = messageList[position].message
            }
        }
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    override fun getItemViewType(position: Int): Int {
        if (FirebaseAuth.getInstance().currentUser?.uid.equals(messageList[position].senderId))
            return ITEM_SENT
        else
            return ITEM_RECIEVE

    }
}