package com.example.a2024_ict_team

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.a2024_ict_team.databinding.ItemRecyclerLeagueBinding

class LeagueAdapter(
    private var items: ArrayList<LeagueItem>,
    private var fragment: Fragment
) : RecyclerView.Adapter<LeagueAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemRecyclerLeagueBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(items[position], position)
    }

    override fun getItemCount() = items.size

    class Holder(private val binding: ItemRecyclerLeagueBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: LeagueItem, position: Int) {
            binding.tvName.text = item.name
            binding.tvTimes.text = item.rank
            binding.tvPoints.text = item.point
            binding.profileImage.setImageResource(R.drawable.ic_profile_placeholder)

            // 순위에 따라 배경색 변경
            val backgroundColor = when (position) {
                0 -> ContextCompat.getColor(binding.root.context, R.color.gold)
                1 -> ContextCompat.getColor(binding.root.context, R.color.silver)
                2 -> ContextCompat.getColor(binding.root.context, R.color.bronze)
                else -> ContextCompat.getColor(binding.root.context, R.color.light_gray)
            }
            binding.root.setBackgroundColor(backgroundColor)
        }
    }
}
