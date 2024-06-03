package com.example.a2024_ict_team

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.a2024_ict_team.databinding.ItemWorkoutRecordBinding

class WorkoutRecordAdapter(private val workoutList: List<WorkoutRecord>) :
    RecyclerView.Adapter<WorkoutRecordAdapter.WorkoutRecordViewHolder>() {

    inner class WorkoutRecordViewHolder(val binding: ItemWorkoutRecordBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutRecordViewHolder {
        val binding = ItemWorkoutRecordBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WorkoutRecordViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WorkoutRecordViewHolder, position: Int) {
        val workoutRecord = workoutList[position]
        holder.binding.tvDate.text = workoutRecord.date
        holder.binding.tvLocation.text = workoutRecord.location
        holder.binding.tvTimeRange.text = workoutRecord.timeRange
        holder.binding.tvDuration.text = workoutRecord.duration
    }

    override fun getItemCount() = workoutList.size
}
