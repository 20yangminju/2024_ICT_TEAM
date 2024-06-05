package com.example.a2024_ict_team

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.DialogFragment

class CompletionDialogFragment(private val showDailyGoalAchieved: Boolean) : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_completion_dialog, container, false)

        val confirmButton: ImageView = view.findViewById(R.id.confirmButton)
        confirmButton.setOnClickListener {
            dismiss() // 팝업 닫기
            if (showDailyGoalAchieved) {
                showDailyGoalAchievedDialog()
            }
        }

        return view
    }

    private fun showDailyGoalAchievedDialog() {
        val dialog = DailyGoalAchievedDialogFragment()
        dialog.show(parentFragmentManager, "DailyGoalAchievedDialog")
    }
}
