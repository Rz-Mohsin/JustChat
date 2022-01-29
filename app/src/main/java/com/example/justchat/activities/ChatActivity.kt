package com.example.justchat.activities


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.justchat.Message
import com.example.justchat.R
import com.example.justchat.adapter.MessageAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_chat.*

class ChatActivity : AppCompatActivity() {

    private lateinit var messageList : ArrayList<Message>
    private lateinit var messageadapter : MessageAdapter
    private lateinit var mDbref : DatabaseReference

    private var recieverRoom : String? = null
    private var senderRoom : String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)



        val name = intent.getStringExtra("name")
        val recieveruid = intent.getStringExtra("uid")
        val senderuid = FirebaseAuth.getInstance().currentUser?.uid
        mDbref = FirebaseDatabase.getInstance().getReference()


        senderRoom = recieveruid + senderuid
        recieverRoom = senderuid + recieveruid

        supportActionBar?.title = name
        messageList = ArrayList()
        messageadapter = MessageAdapter(messageList)

        rvChatMessages.layoutManager = LinearLayoutManager(this)
        rvChatMessages.adapter = messageadapter

        //adding data to recycler view
        mDbref.child("chats").child(senderRoom!!).child("messages")
            .addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {

                    messageList.clear()
                    for (postSnapshot in snapshot.children)
                    {
                        val message = postSnapshot.getValue(Message::class.java)
                        messageList.add(message!!)
                    }
                    messageadapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })

        sendbutton.setOnClickListener{
                //adding message to database
            val message = edt_type_message.text.toString()
            if(message.isNotEmpty()) {
                val messageObject = Message(message, senderuid)
                mDbref.child("chats").child(senderRoom!!).child("messages").push()
                    .setValue(messageObject).addOnSuccessListener {
                        mDbref.child("chats").child(recieverRoom!!).child("messages").push()
                            .setValue(messageObject)
                    }
                edt_type_message.setText("")
            }
            else
            {
                Toast.makeText(this,"Message can not be sent empty", Toast.LENGTH_SHORT).show()
            }
        }



    }
}