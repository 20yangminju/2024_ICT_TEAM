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

        setupDayTextViews()
        setupButtonListeners()
        setupRecyclerView()

        return binding.root
    }

    private fun setupRecyclerView() {

        val temp  = mutableListOf<WorkoutRecord>()
        if(selectedDayIndex == 0) {
            temp.add(WorkoutRecord("2024년 06월 03일", "항공대입구역 1번 계단", "08:50 AM ~ 08:54 AM", "3분 48초"))
            temp.add(WorkoutRecord("2024년 06월 03일", "항공대 전자관 1번 계단", "08:57 AM ~ 09:00 AM", "2분 12초"))
            binding.tvDate.text = "2024년 6월 3일 월요일"
        }
        else if (selectedDayIndex == 1){
            temp.add(WorkoutRecord("2024년 06월 04일", "항공대입구역 1번 계단", "11:54 AM ~ 11 : 59 AM", "4분 32초"))
            temp.add(WorkoutRecord("2024년 06월 04일", "항공대 과학관 1번 계단", "12:00 AM ~ 12:02 AM", "1분 30초"))
            temp.add(WorkoutRecord("2024년 06월 04일", "항공대 강의동 3번 계단", "02:50 PM ~ 02:56 PM", "5분 27초"))
            binding.tvDate.text = "2024년 6월 4일 화요일"
        }
        else if (selectedDayIndex == 2) {
            temp.add(WorkoutRecord("2024년 06월 05일", "항공대입구역 1번 계단", "09:54 AM ~ 09:58 AM", "4분 32초"))
            temp.add(WorkoutRecord("2024년 06월 05일", "항공대 학생회관 2번 계단", "10:05 AM ~ 10:08 AM", "2분 56초"))
            temp.add(WorkoutRecord("2024년 06월 05일", "항공대 도서관 1번 계단", "13:26 PM ~ 13:28 AM", "1분 23초"))
            temp.add(WorkoutRecord("2024년 06월 05일", "항공대 강의동 1번 계단", "15:22 PM ~ 15:25 AM", "2분 18초"))
            binding.tvDate.text = "2024년 6월 5일 수요일"
        }
        else if (selectedDayIndex == 3){
            temp.add(WorkoutRecord("2024년 06월 06일", "항공대입구역 1번 계단", "08:54 AM ~ 08:58 AM", "3분 56초"))
            temp.add(WorkoutRecord("2024년 06월 06일", "항공대 과학관 1번 계단", "09:00 AM ~ 09:02 AM", "1분 8초"))
            binding.tvDate.text = "2024년 6월 6일 목요일"
        }
        else if (selectedDayIndex == 4){
            temp.add(WorkoutRecord("2024년 06월 07일", "항공대입구역 1번 계단", "01:40 PM ~ 01:45 PM", "5분 12초"))
            temp.add(WorkoutRecord("2024년 06월 07일", "항공대 과학관 3번 계단", "01:50 PM ~ 01:54 AM", "3분 46초"))
            temp.add(WorkoutRecord("2024년 06월 07일", "항공대 강의동 2번 계단", "03:54 AM ~ 03:56 AM", "2분 3초"))
            binding.tvDate.text = "2024년 6월 7일 금요일"
        }
        else if (selectedDayIndex == 5){
            binding.tvDate.text = "2024년 6월 8일 토요일"
        }
        else if (selectedDayIndex == 6){
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
