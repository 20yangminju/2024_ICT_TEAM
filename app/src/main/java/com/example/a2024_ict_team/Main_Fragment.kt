package com.example.a2024_ict_team

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.Modifier
import com.example.a2024_ict_team.databinding.FragmentMainBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.time.LocalDate


class Main_Fragment : Fragment() {
    private val userID = "ymj10003"
    lateinit var binding: FragmentMainBinding
    private lateinit var database: DatabaseReference




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(layoutInflater)
        database = FirebaseDatabase.getInstance().getReference("user")
        database.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                update_point()
                update_todaypoint()
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        update_point()
        update_todaypoint()
        return binding.root

    }


    fun update_point() {
        var sum = 0
        database.child(userID).addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                var weight = snapshot.child("weight").value as Long
                for (ds in snapshot.child("recentwork").children){
                    val temp =  ds.child("duration").value as Long
                    val point = temp.toInt()
                    sum += point
                }
                var final = sum*7*weight.toInt()/3000
                database.child(userID).child("point").setValue(final)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    fun update_todaypoint() {
        var sum = 0
        database.child(userID).addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                var weight = snapshot.child("weight").value as Long
                for (ds in snapshot.child("recentwork").children){
                    var check = ds.child("Date").value as? Any?
                    var date = LocalDate.now().toString()
                    if(check.toString() == date) {
                        val temp = ds.child("duration").value as Long
                        val point = temp.toInt()
                        sum += point
                    }

                }
                var final = sum*7*weight.toInt()/3000
                binding.progressText.text = final.toString()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}

