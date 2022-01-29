package com.example.justchat.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.justchat.R
import com.example.justchat.UserData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_signup.*

class SignupActivity : AppCompatActivity() {

    private lateinit var mauth : FirebaseAuth
    private lateinit var mDBref : DatabaseReference
    var email : String? = null
    var password : String? = null
    var name : String? = null
    val TAG : String = "Signup Activity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        supportActionBar?.hide()

        mauth = FirebaseAuth.getInstance()

        val btsignup : Button = findViewById(R.id.btSignupFromSignup)
        val etEmail : EditText = findViewById(R.id.etEmail)
        val etPassword : EditText = findViewById(R.id.etPassword)
        btsignup.setOnClickListener{
             email = etEmail.text.toString()
            password = etPassword.text.toString()
            name = etName.text.toString()


            //name?.let { it1 -> email?.let { it2-> password?.let { it3-> SignUp(it1,it2,it3) } }
            //} ?: Toast.makeText(this, "Sign Up Failed : Fill details Again", Toast.LENGTH_SHORT).show()
            if(!name.equals("") and !email.equals("") and !password.equals(""))
            {
                SignUp(name!!,email!!,password!!)
            }
            else
            {
                Toast.makeText(this, "Sign Up Failed : Fill details Again", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun SignUp (name : String, email : String, password : String)
    {
        mauth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's informationa
                    mauth.currentUser?.uid?.let { addusertoDatabase(name, email, it) }
                    val intent = Intent(this@SignupActivity, MainActivity::class.java)
                    startActivity(intent)

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "Sign Up Failed", task.exception)
                    Toast.makeText(this@SignupActivity, "Sign Up Failed",
                        Toast.LENGTH_SHORT).show()

                }
            }
    }

    private fun addusertoDatabase(name : String, email: String, uid : String)
    {
        mDBref = FirebaseDatabase.getInstance().getReference()
        mDBref.child("user").child(uid).setValue(UserData(name, email, uid))
    }


}