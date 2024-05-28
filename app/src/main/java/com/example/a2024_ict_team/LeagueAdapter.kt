package com.example.a2024_ict_team

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.a2024_ict_team.databinding.ItemRecyclerLeagueBinding

class LeagueAdapter(
    private var items: ArrayList<League_item>,
    private var fragment: Fragment
) : RecyclerView.Adapter<LeagueAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemRecyclerLeagueBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding, fragment)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    class Holder(private val binding: ItemRecyclerLeagueBinding, private val fragment: Fragment) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: League_item) {
            binding.lank.text = item.lank
            binding.name.text = item.name
            binding.point.text = item.point
            binding.times.text = item.times
        }
    }
}
