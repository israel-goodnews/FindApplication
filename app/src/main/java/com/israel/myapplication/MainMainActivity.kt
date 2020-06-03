package com.israel.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import androidx.fragment.app.FragmentTransaction
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomnavigation.BottomNavigationMenu
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.israel.myapplication.fragments.*

class MainMainActivity : AppCompatActivity() {

    //OBJECT FOR THE FRAGMENTS
    lateinit var homeFragment: HomeFragment
    lateinit var profileFragment: ProfileFragment
    lateinit var messagesFragment: MessagesFragment
    lateinit var findFragment: FindFragment
    lateinit var addFragment: AddFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_main)

        //CREATING FRAME LAYOUT AND BOTTOM NAV VARIABLES
        var bottomnav = findViewById<BottomNavigationView>(R.id.bottomNavMenu)
        var frame = findViewById<FrameLayout>(R.id.frameLayout)

        //ADD MENU EVENT LISTENERS
        bottomnav.setOnNavigationItemSelectedListener {item ->
            //SELECT EVERY MENU ITEM AND ADD AN EVENT WHEN IT IS SELECTED
         when(item.itemId){
             //HOME
           R.id.home_icon -> {
               homeFragment = HomeFragment()
               supportFragmentManager
                   .beginTransaction()
                   .replace(R.id.frameLayout,homeFragment)
                   .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                   .commit()
           }

             //PROFILE
             R.id.profile_icon -> {
                 profileFragment = ProfileFragment()
                 supportFragmentManager
                     .beginTransaction()
                     .replace(R.id.frameLayout,profileFragment)
                     .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                     .commit()
             }

           //SEARCH
             R.id.search_icon -> {
                 findFragment = FindFragment()
                 supportFragmentManager
                     .beginTransaction()
                     .replace(R.id.frameLayout,findFragment)
                     .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                     .commit()
             }

             //ADD
             R.id.add_icon -> {
                 addFragment = AddFragment()
                 supportFragmentManager
                     .beginTransaction()
                     .replace(R.id.frameLayout,addFragment)
                     .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                     .commit()
             }

             //MESSAGES
             R.id.chat_icon -> {
                 messagesFragment = MessagesFragment()
                 supportFragmentManager
                     .beginTransaction()
                     .replace(R.id.frameLayout,messagesFragment)
                     .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                     .commit()
             }
         }

            true
        }


    }
}
