package com.israel.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*

class LoginActivity : AppCompatActivity() {

    //CONNECTING TO FIREBASE AND AUTHENTIFYING

    private lateinit var mAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        //SETTING BACK BUTTON ON TOOLBAR AND RENAMING APP TITLE
        val toolbar: Toolbar = findViewById(R.id.toolbar_login)
        setSupportActionBar(toolbar)
        supportActionBar!!.title ="Login"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            val intent = Intent(this, WelcomeActivity::class.java)
            startActivity(intent)
            finish()
        }

        //LOGIN USERS
        mAuth = FirebaseAuth.getInstance()

        login_btn.setOnClickListener {
            loginUser()
        }
    }

    private fun loginUser() {
        val email:String = email_login.text.toString()
        val password:String = password_login.text.toString()

                if (email == "") {
                    Toast.makeText(this, "Please write your email", Toast.LENGTH_LONG).show()
                }else if (password == ""){
                    Toast.makeText(this, "Please input your password", Toast.LENGTH_LONG).show()
                }else{
                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                        task ->
                        if(task.isSuccessful){
                            val intent = Intent(this, MainMainActivity::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                            finish()
                        }else{
                            Toast.makeText(this, "Error Message" + task.exception!!.message.toString(), Toast.LENGTH_LONG).show()
                        }
                    }
                }
    }
}
