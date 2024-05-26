package com.example.a2024_ict_team

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

data class WorkoutRecord(val date: String, val detail: String)

class WorkoutRecordAdapter(private val workoutList: List<WorkoutRecord>) : RecyclerView.Adapter<WorkoutRecordAdapter.WorkoutViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_workout_record, parent, false)
        return WorkoutViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: WorkoutViewHolder, position: Int) {
        val currentItem = workoutList[position]
        holder.tvWorkoutRecord.text = "${currentItem.date} ${currentItem.detail}"
    }

    override fun getItemCount() = workoutList.size

    class WorkoutViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvWorkoutRecord: TextView = itemView.findViewById(R.id.tvWorkoutRecord)
    }
}
