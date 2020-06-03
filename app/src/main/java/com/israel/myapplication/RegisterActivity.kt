package com.israel.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_register.*
import java.util.HashMap

class RegisterActivity : AppCompatActivity() {

    //CONNECTING TO FIREBASE AND AUTHENTIFYING

    private lateinit var mAuth: FirebaseAuth
    private lateinit var refUsers:DatabaseReference
    private var firebaseUserID:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

      //SETTING BACK BUTTON ON TOOLBAR AND RENAMING APP TITLE
        val toolbar: Toolbar = findViewById(R.id.toolbar_register)
        setSupportActionBar(toolbar)
        supportActionBar!!.title ="Register"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            val intent = Intent(this, WelcomeActivity::class.java)
            startActivity(intent)
            finish()
        }


        //REGISTERING USERS
        mAuth = FirebaseAuth.getInstance()

        register_btn.setOnClickListener {
            registerUser()
        }
    }


    //REGISTERING USERS
    private fun registerUser() {
        val username:String = username_register.text.toString()
        val email:String = email_register.text.toString()
        val password:String = password_register.text.toString()

        if(username == ""){
            Toast.makeText(this, "Please write username", Toast.LENGTH_LONG).show()
        }else if (email == "") {
            Toast.makeText(this, "Please write your email", Toast.LENGTH_LONG).show()
        }else if (password == ""){
            Toast.makeText(this, "Please input your password", Toast.LENGTH_LONG).show()
        }else{
             mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                 task ->
                 if (task.isSuccessful){
                      firebaseUserID = mAuth.currentUser!!.uid
                     refUsers = FirebaseDatabase.getInstance().reference.child("users").child(firebaseUserID)

                    val userHashMap = HashMap<String, Any>()
                     //uid = Key     firebaseUserID = Value
                       userHashMap["uid"] = firebaseUserID
                     userHashMap["username"] = username
                     userHashMap["profile"] = "https://firebasestorage.googleapis.com/v0/b/messenger-app-30872.appspot.com/o/profile_image.png?alt=media&token=021f4ddb-9499-414a-b10a-49ef092e0ac3"
                     userHashMap["cover"] = "https://firebasestorage.googleapis.com/v0/b/messenger-app-30872.appspot.com/o/cover.png?alt=media&token=b484b529-b164-4743-9710-a240b8ad9746"
                     userHashMap["status"] = "offline"
                     userHashMap["search"] = username.toLowerCase()
                     userHashMap["facebook"] = "https://m.faceook.com"
                     userHashMap["instagram"] = "https://m.instagram.com"
                     userHashMap["website"] = "https://www.google.com"

                     //ADD THE ABOVE DETAILS TO A USER
                     refUsers.updateChildren(userHashMap).addOnCompleteListener {
                         task -> if (task.isSuccessful) {
                         val intent = Intent(this, MainMainActivity::class.java)
                         intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                         startActivity(intent)
                         finish()
                     }
                     }

                 }else{
                     Toast.makeText(this, "Error Message" + task.exception!!.message.toString(), Toast.LENGTH_LONG).show()
                 }
             }
        }
    }
}
