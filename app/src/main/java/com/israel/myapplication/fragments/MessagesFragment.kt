package com.israel.myapplication.fragments

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.israel.myapplication.MainActivity

import com.israel.myapplication.R
import com.israel.myapplication.WelcomeActivity
import com.israel.myapplication.modelclasses.Chat
import com.israel.myapplication.modelclasses.Users
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*

/**
 * A simple [Fragment] subclass.
 */
class MessagesFragment : Fragment() {
    var refUsers: DatabaseReference? = null
    var firebaseUser: FirebaseUser? = null
    //private lateinit var binding:FragmentMessagesBinding
    //private lateinit var mainBinding: ActivityMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ):
            View? {
        //ALWAYS ENSURE THAT ALL VIEWS IE findViewBy...are in the onCreateView function
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_messages, container, false)

        //MAKE TOOLBAR VISIBLE AS A VIEW
        val toolbar_main = view.findViewById<Toolbar>(R.id.toolbar_main)
       var userName = view.findViewById<TextView>(R.id.user_name)
        (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar_main)
        (requireActivity() as AppCompatActivity).supportActionBar!!.title =""


        //MAKE FRAGMENTS VISIBLE ON THE MESSAGES FRAGMENT
        val tabLayout:TabLayout =  view.findViewById(R.id.tab_layout)
        val viewPager:ViewPager =  view.findViewById(R.id.view_pager)
//        val viewPagerAdapter = ViewPagerAdapter( (requireActivity() as AppCompatActivity).supportFragmentManager)
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
                val viewPagerAdapter = ViewPagerAdapter( (requireActivity() as AppCompatActivity).supportFragmentManager)
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


        //DISPLAY USERNAME AND PROFILE FROM DB
        refUsers!!.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()){
                    val user:Users? = p0.getValue(Users::class.java)

                    userName.text = user!!.getUserName()
                    Picasso.get().load(user!!.getProfile()).placeholder(R.drawable.ic_profile).into(profile_image)
                }
            }

            override fun onCancelled(p0: DatabaseError) {

            }
        })

        //binding = FragmentMessagesBinding.inflate(layoutInflater)
        //binding.root
        return view

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        //GET PROFILE AND USERNAME FROM DB AND DISPLAY THEM

        firebaseUser = FirebaseAuth.getInstance().currentUser
        refUsers = FirebaseDatabase.getInstance().reference.child("users").child(firebaseUser!!.uid)


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

