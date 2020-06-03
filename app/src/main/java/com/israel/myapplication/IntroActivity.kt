package com.israel.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView

class IntroActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        //ANIMATION CODE
        //Declare Animation
        val topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation)
        val bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation)
        val appearAnim = AnimationUtils.loadAnimation(this, R.anim.scale_animation)

        val drop = findViewById<View>(R.id.drop) as ImageView
        val smile = findViewById<View>(R.id.smile) as ImageView
        val alience = findViewById<View>(R.id.alience) as ImageView


        //Set Animation
        drop.startAnimation(appearAnim)
        smile.startAnimation(appearAnim)
        alience.startAnimation(appearAnim)


        Handler().postDelayed({
            val intent = Intent(this, WelcomeActivity::class.java)
            startActivity(intent)
            finish()
        },4000)

    }
}
