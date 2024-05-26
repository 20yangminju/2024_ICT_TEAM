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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class RecordFragment : Fragment() {
    private lateinit var binding: FragmentRecordBinding
    private lateinit var database: DatabaseReference

    private val userId = "ymj10003"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRecordBinding.inflate(inflater, container, false)
        database = FirebaseDatabase.getInstance().reference
        database = database.child("user").child(userId)

        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var total = 0
                val weight = snapshot.child("weight").value as Long
                for (ds in snapshot.child("recentwork").children) {
                    val cal = ds.child("time").value as Long
                    val up = ds.child("Up").value as Long
                    total += if (up.toInt() == 1) {
                        cal.toInt() * 8 * weight.toInt()
                    } else {
                        cal.toInt() * 4 * weight.toInt()
                    }
                }
                binding.textView4.text = total.toString()
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle database error
            }
        })

        setupRecyclerView()
        return binding.root
    }

    private fun setupRecyclerView() {
        val workoutList = listOf(
            WorkoutRecord("2024년 05월 26일", "1번 계단 2분"),
            WorkoutRecord("2024년 05월 26일", "2번 계단 5분"),
            WorkoutRecord("2024년 05월 26일", "3번 계단 3분")
        )
        val workoutAdapter = WorkoutRecordAdapter(workoutList)
        binding.rvWorkoutRecords.layoutManager = LinearLayoutManager(requireContext())
        binding.rvWorkoutRecords.adapter = workoutAdapter
    }
}
