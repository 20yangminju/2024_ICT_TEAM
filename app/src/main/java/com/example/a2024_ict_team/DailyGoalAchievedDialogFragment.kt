package com.example.a2024_ict_team

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.DialogFragment

class DailyGoalAchievedDialogFragment : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_daily_goal_achieved_dialog, container, false)

        val confirmButton: ImageView = view.findViewById(R.id.confirmButton)
        confirmButton.setOnClickListener {
            dismiss() // 팝업 닫기
        }

        return view
    }
}
