package com.example.a2024_ict_team

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a2024_ict_team.databinding.FragmentRecordBinding
import com.google.firebase.database.DatabaseReference
import android.graphics.Typeface
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.Calendar


class RecordFragment : Fragment() {
    private lateinit var binding: FragmentRecordBinding
    private lateinit var recordAdapter: WorkoutRecordAdapter
    private lateinit var database: DatabaseReference

    val cal: Calendar = Calendar.getInstance()
    private val userId = "ymj10003"
    private lateinit var dayTextViews: Array<TextView>
    val nWeek: Int = cal.get(Calendar.DAY_OF_WEEK) -1
    private var selectedDayIndex = nWeek // Initial selected day is Friday (index 4)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRecordBinding.inflate(inflater, container, false)

        // Setting up the RecyclerView with dummy data
        database = FirebaseDatabase.getInstance().getReference("user")

        setupDayTextViews()
        setupButtonListeners()
        setupRecyclerView()

        return binding.root
    }

    private fun setupRecyclerView() {

        val temp  = mutableListOf<WorkoutRecord>()
        if(selectedDayIndex == 0) {
            database.child(userId).child("recentwork").addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (ds in snapshot.children){
                        if (ds.child("dayofweek").value as String == "MONDAY"){
                            val date = ds.child("Date").value as Any
                            val duration = ds.child("duration").value as Any
                            val location = ds.child("location").value as Any
                            val timerange = ds.child("timerange").value as Any

                            val duration_int = duration.toString().toInt()
                            val duration_string = "${duration_int / 60}분 ${duration_int % 60}초"
                            val work = WorkoutRecord(date.toString(), location.toString(),timerange.toString(), duration_string)
                            temp.add(work)
                            binding.rvRecord.adapter = WorkoutRecordAdapter (temp.toList())
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })

            binding.tvDate.text = "2024년 6월 3일 월요일"
        }
        else if (selectedDayIndex == 1){
            database.child(userId).child("recentwork").addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (ds in snapshot.children){
                        if (ds.child("dayofweek").value as String == "TUESDAY"){
                            val date = ds.child("Date").value as Any
                            val duration = ds.child("duration").value as Any
                            val location = ds.child("location").value as Any
                            val timerange = ds.child("timerange").value as Any

                            val duration_int = duration.toString().toInt()
                            val duration_string = "${duration_int / 60}분 ${duration_int % 60}초"
                            val work = WorkoutRecord(date.toString(), location.toString(),timerange.toString(), duration_string)
                            temp.add(work)
                            binding.rvRecord.adapter = WorkoutRecordAdapter (temp.toList())
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
            binding.tvDate.text = "2024년 6월 4일 화요일"
        }
        else if (selectedDayIndex == 2) {
            database.child(userId).child("recentwork").addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (ds in snapshot.children){
                        if (ds.child("dayofweek").value as String == "WEDNESDAY"){
                            val date = ds.child("Date").value as Any
                            val duration = ds.child("duration").value as Any
                            val location = ds.child("location").value as Any
                            val timerange = ds.child("timerange").value as Any

                            val duration_int = duration.toString().toInt()
                            val duration_string = "${duration_int / 60}분 ${duration_int % 60}초"
                            val work = WorkoutRecord(date.toString(), location.toString(),timerange.toString(), duration_string)
                            temp.add(work)
                            binding.rvRecord.adapter = WorkoutRecordAdapter (temp.toList())
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
            binding.tvDate.text = "2024년 6월 5일 수요일"
        }
        else if (selectedDayIndex == 3){
            database.child(userId).child("recentwork").addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (ds in snapshot.children){
                        if (ds.child("dayofweek").value as String == "THURSDAY"){
                            val date = ds.child("Date").value as Any
                            val duration = ds.child("duration").value as Any
                            val location = ds.child("location").value as Any
                            val timerange = ds.child("timerange").value as Any

                            val duration_int = duration.toString().toInt()
                            val duration_string = "${duration_int / 60}분 ${duration_int % 60}초"
                            val work = WorkoutRecord(date.toString(), location.toString(),timerange.toString(), duration_string)
                            temp.add(work)
                            binding.rvRecord.adapter = WorkoutRecordAdapter (temp.toList())
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })

            binding.tvDate.text = "2024년 6월 6일 목요일"
        }
        else if (selectedDayIndex == 4){
            database.child(userId).child("recentwork").addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (ds in snapshot.children){
                        if (ds.child("dayofweek").value as String == "FRIDAY"){
                            val date = ds.child("Date").value as Any
                            val duration = ds.child("duration").value as Any
                            val location = ds.child("location").value as Any
                            val timerange = ds.child("timerange").value as Any

                            val duration_int = duration.toString().toInt()
                            val duration_string = "${duration_int / 60}분 ${duration_int % 60}초"
                            val work = WorkoutRecord(date.toString(), location.toString(),timerange.toString(), duration_string)
                            temp.add(work)
                            binding.rvRecord.adapter = WorkoutRecordAdapter (temp.toList())
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
            binding.tvDate.text = "2024년 6월 7일 금요일"
        }
        else if (selectedDayIndex == 5){
            database.child(userId).child("recentwork").addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (ds in snapshot.children){
                        if (ds.child("dayofweek").value as String == "SATURDAY"){
                            val date = ds.child("Date").value as Any
                            val duration = ds.child("duration").value as Any
                            val location = ds.child("location").value as Any
                            val timerange = ds.child("timerange").value as Any

                            val duration_int = duration.toString().toInt()
                            val duration_string = "${duration_int / 60}분 ${duration_int % 60}초"
                            val work = WorkoutRecord(date.toString(), location.toString(),timerange.toString(), duration_string)
                            temp.add(work)
                            binding.rvRecord.adapter = WorkoutRecordAdapter (temp.toList())
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
            binding.tvDate.text = "2024년 6월 8일 토요일"
        }
        else if (selectedDayIndex == 6){
            database.child(userId).child("recentwork").addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (ds in snapshot.children){
                        if (ds.child("dayofweek").value as String == "SUNDAY"){
                            val date = ds.child("Date").value as Any
                            val duration = ds.child("duration").value as Any
                            val location = ds.child("location").value as Any
                            val timerange = ds.child("timerange").value as Any

                            val duration_int = duration.toString().toInt()
                            val duration_string = "${duration_int / 60}분 ${duration_int % 60}초"
                            val work = WorkoutRecord(date.toString(), location.toString(),timerange.toString(), duration_string)
                            temp.add(work)
                            binding.rvRecord.adapter = WorkoutRecordAdapter (temp.toList())
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
            binding.tvDate.text = "2024년 6월 9일 일요일"
        }

        if(temp == emptyList<WorkoutRecord>()){
            binding.dailycheck.visibility = View.GONE

        }
        else {
            binding.dailycheck.visibility = View.VISIBLE
        }
        val workoutList = temp.toList()

        binding.rvRecord.layoutManager = LinearLayoutManager(context)
        recordAdapter = WorkoutRecordAdapter(workoutList)
        binding.rvRecord.adapter = recordAdapter

    }

    private fun setupDayTextViews() {
        dayTextViews = arrayOf(
            binding.textMon,
            binding.textTue,
            binding.textWed,
            binding.textThu,
            binding.textFri,
            binding.textSat,
            binding.textSun
        )

        updateSelectedDay()
    }

    private fun setupButtonListeners() {
        binding.buttonPrev.setOnClickListener {
            if (selectedDayIndex > 0) {
                selectedDayIndex--
                updateSelectedDay()
                setupRecyclerView()
            }
        }

        binding.buttonNext.setOnClickListener {
            if (selectedDayIndex < dayTextViews.size - 1) {
                selectedDayIndex++
                updateSelectedDay()
                setupRecyclerView()
            }
        }
    }

    private fun updateSelectedDay() {
        for (i in dayTextViews.indices) {
            val textView = dayTextViews[i]
            if (i == selectedDayIndex) {
                textView.setBackgroundResource(R.drawable.background_selected_day)
                textView.setTypeface(null, Typeface.BOLD)
            } else {
                textView.setBackgroundResource(0)
                textView.setTypeface(null, Typeface.NORMAL)
            }
        }
    }
}
