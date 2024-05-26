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
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MyPage_Fragment : Fragment() {
    private lateinit var binding: FragmentMyPageBinding
    private lateinit var database: DatabaseReference
    private val userId = "ymj10003"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyPageBinding.inflate(inflater, container, false)
        database = FirebaseDatabase.getInstance().getReference("user")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        database.child(userId).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val height = snapshot.child("height").value as? Long ?: 0L
                val weight = snapshot.child("weight").value as? Long ?: 0L
                val age = snapshot.child("age").value as? Long ?: 0L
                val imageURL = snapshot.child("UserImage").value as? String

                binding.Username.text = userId
                binding.Height.text = height.toString()
                binding.Weight.text = weight.toString()
                binding.Age.text = age.toString()

                Glide.with(binding.imageView3.context)
                    .load(imageURL)
                    .override(300, 300)
                    .circleCrop()
                    .into(binding.imageView3)
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle possible errors.
            }
        })
    }
}
