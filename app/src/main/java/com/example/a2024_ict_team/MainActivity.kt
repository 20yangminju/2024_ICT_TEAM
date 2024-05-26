package com.example.a2024_ict_team

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var todayActivityData: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        todayActivityData = findViewById(R.id.today_activity_data)

        // 예시로 오늘의 활동량 데이터를 설정합니다.
        // 실제 데이터는 필요에 따라 변경할 수 있습니다.
        updateTodayActivityData(5000) // 예시로 5000걸음 설정
    }

    private fun updateTodayActivityData(steps: Int) {
        val activityText = "$steps steps"
        todayActivityData.text = activityText
    }
}
