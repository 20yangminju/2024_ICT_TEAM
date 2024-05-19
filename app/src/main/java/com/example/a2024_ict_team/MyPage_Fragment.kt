package com.example.a2024_ict_team

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.a2024_ict_team.databinding.FragmentMyPageBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.database
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue


class MyPage_Fragment : Fragment() {
    lateinit var binding: FragmentMyPageBinding
    private lateinit var database: DatabaseReference
    var userId = "ymj10003"


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMyPageBinding.inflate(layoutInflater)
        database = FirebaseDatabase.getInstance().getReference("user")
        return binding.root
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        database.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val Height = snapshot.child(userId).child("height").value as? Long
                val Weight = snapshot.child(userId).child("weight").value as? Long
                val Age = snapshot.child(userId).child("age").value as? Long
                val ImageURL = snapshot.child(userId).child("UserImage").value as? String

                binding.Username.text = userId
                binding.Height.text = Height.toString()
                binding.Weight.text = Weight.toString()
                binding.Age.text = Age.toString()
                Glide.with(binding.imageView3.context).load(ImageURL).override(300, 300).into(binding.imageView3)


            }
            override fun onCancelled(error: DatabaseError){

            }
        }
        )

    }


}
