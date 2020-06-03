package com.israel.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_welcome.*

class WelcomeActivity : AppCompatActivity() {

    var firebaseUser:FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)


        //IMPLEMENTING LOGIN AND REGISTER LISTENERS

        welcome_register.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }

        welcome_login.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    //CHECK IF USER IS STILL SIGNED IN START MAIN ACTIVITY
    override fun onStart() {
        super.onStart()

        firebaseUser = FirebaseAuth.getInstance().currentUser

        if (firebaseUser!=null){

            val intent = Intent(this, MainMainActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}
