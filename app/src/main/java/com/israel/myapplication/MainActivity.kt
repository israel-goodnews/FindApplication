package com.israel.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.israel.myapplication.modelclasses.Users
import com.israel.myapplication.fragments.ChatsFragment
import com.israel.myapplication.fragments.SearchFragment
import com.israel.myapplication.fragments.SettingsFragment
import com.israel.myapplication.modelclasses.Chat
import com.squareup.picasso.Picasso

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var refUsers:DatabaseReference? = null
    var firebaseUser:FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar_main)

        //GET PROFILE AND USERNAME FROM DB AND DISPLAY THEM

        firebaseUser = FirebaseAuth.getInstance().currentUser
        refUsers = FirebaseDatabase.getInstance().reference.child("users").child(firebaseUser!!.uid)

        //MAKE TOOLBAR VISIBLE ON UI

      val toolbar:Toolbar = findViewById(R.id.toolbar_main)
        setSupportActionBar(toolbar)
        supportActionBar!!.title =""

        val tabLayout:TabLayout = findViewById(R.id.tab_layout)
        val viewPager:ViewPager = findViewById(R.id.view_pager)
//        val viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)
//
//        viewPagerAdapter.addFragment(ChatsFragment(),"Chats")
//        viewPagerAdapter.addFragment(SearchFragment(),"Search")
//        viewPagerAdapter.addFragment(SettingsFragment(),"Settings")
//
//
//        viewPager.adapter = viewPagerAdapter
//        tabLayout.setupWithViewPager(viewPager)

        val ref = FirebaseDatabase.getInstance().reference.child("Chats")
        ref!!.addValueEventListener(object:ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {
                val viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)
                var countUnreadMessages = 0

                for(dataSnapshot in p0.children){
                    val chat = dataSnapshot.getValue(Chat::class.java)
                    if (chat!!.getReceiver().equals(firebaseUser!!.uid) && !chat.isIsseen()!!){
                        countUnreadMessages += 1
                    }
                }
              if(countUnreadMessages == 0){
                  viewPagerAdapter.addFragment(ChatsFragment(),"Chats")
              }

                else{
                  viewPagerAdapter.addFragment(ChatsFragment(),"($countUnreadMessages)Chats")
              }
                viewPagerAdapter.addFragment(SearchFragment(),"Search")
                viewPagerAdapter.addFragment(SettingsFragment(),"Settings")
                viewPager.adapter = viewPagerAdapter
                tabLayout.setupWithViewPager(viewPager)

            }

            override fun onCancelled(p0: DatabaseError) {
                TODO("Not yet implemented")
            }
        })


        //DISPLAY USERNAME AND PROFILE

        var userName = findViewById<View>(R.id.user_name) as TextView

        refUsers!!.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()){
                val user:Users? = p0.getValue(Users::class.java)

                    userName.text = user!!.getUserName()
                    Picasso.get().load(user.getProfile()).placeholder(R.drawable.ic_profile).into(profile_image)
                }
            }

            override fun onCancelled(p0: DatabaseError) {

            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_back -> {
                FirebaseAuth.getInstance().signOut()
                val intent = Intent(this, WelcomeActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                finish()
                return true
            }
        }
        return false
    }

    //ADOPTER FOR FRAGMENTS
    internal class ViewPagerAdapter(fragmentManager: FragmentManager):FragmentPagerAdapter(fragmentManager){

        private val fragments:ArrayList<Fragment>
        private val titles:ArrayList<String>

        init {
            fragments = ArrayList<Fragment>()
            titles = ArrayList<String>()
        }

        override fun getItem(position: Int): Fragment {
            return fragments[position]
        }

        override fun getCount(): Int {
            return fragments.size
        }

        fun addFragment(fragment:Fragment, title:String){
            fragments.add(fragment)
            titles.add(title)
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return titles[position]
        }
    }

    private fun updateStatus(status:String)
    {
       val ref = FirebaseDatabase.getInstance().reference.child("users").child(firebaseUser!!.uid)

        val hashMap = HashMap<String, Any>()
        hashMap["status"] = status
        ref!!.updateChildren(hashMap)
    }

    override fun onResume() {
        super.onResume()

        updateStatus("online")
    }

    override fun onPause() {
        super.onPause()

        updateStatus("offline")
    }
}
