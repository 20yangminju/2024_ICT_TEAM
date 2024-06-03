package com.example.a2024_ict_team

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.a2024_ict_team.databinding.FragmentRecordBinding
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
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

        // Setting up the RecyclerView with dummy data
        setupRecyclerView()
        return binding.root
    }

    private fun setupRecyclerView() {
        val workoutList = listOf(
            WorkoutRecord("2024년 06월 07일", "항공대입구역 1번 계단", "08:54 AM ~ 08:58 AM", "4분 32초"),
            WorkoutRecord("2024년 06월 07일", "한국항공대학교 학생회관 정문 계단", "09:10 AM ~ 09:12 AM", "2분 01초"),
            WorkoutRecord("2024년 06월 07일", "항공대입구역 1번 계단", "15:39 PM ~ 15:42 PM", "3분 49초")
        )
        val workoutAdapter = WorkoutRecordAdapter(workoutList)
        //binding.rvWorkoutRecords.layoutManager = LinearLayoutManager(requireContext())
        //binding.rvWorkoutRecords.adapter = workoutAdapter
    }
}
