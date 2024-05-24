package com.example.a2024_ict_team

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.a2024_ict_team.databinding.FragmentRecordBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class RecordFragment : Fragment() {
    lateinit var binding: FragmentRecordBinding
    private lateinit var database: DatabaseReference

    var  userId = "ymj10003"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRecordBinding.inflate(layoutInflater)
        database = FirebaseDatabase.getInstance().reference
        database = database.child("user").child(userId).child("recentwork")

        database.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                var total = 0
                for(ds in snapshot.children){
                    val cal = ds.child("time").value as Long
                    val Up = ds.child("Up").value as Long
                    if(Up.toInt() == 1){
                        total += cal.toInt() * 8
                    }
                    else
                        total +=cal.toInt() * 4

                }
                binding.textView4.text = total.toString()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        return binding.root
    }



}