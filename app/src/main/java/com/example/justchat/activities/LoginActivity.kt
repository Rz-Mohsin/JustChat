package com.example.justchat.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.widget.Toast
import com.example.justchat.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var mauth : FirebaseAuth
    var email: String? = null
    var password : String? = null
    val TAG = "Login Activity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        supportActionBar?.hide()

        mauth = FirebaseAuth.getInstance()

        btSignup.setOnClickListener{
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }

        btLogin.setOnClickListener {
            email = etEmail.text.toString()
            password = etPassword.text.toString()

            if(!email.equals(""))
            {
                login(email!!, password!!)
            }else{
                Toast.makeText(this, "Log In Failed : WRONG DETAILS", Toast.LENGTH_SHORT).show()
            }

        }


    }

    private fun login(email : String, password: String)
    {
        mauth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(intent)

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "Log In Failed", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }
}