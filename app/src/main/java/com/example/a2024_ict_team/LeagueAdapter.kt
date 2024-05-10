package com.example.a2024_ict_team

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.a2024_ict_team.databinding.ItemRecyclerLeagueBinding

class LeagueAdapter(
    private var items : ArrayList<League_item>,
    private var fragment: Fragment
) : RecyclerView.Adapter<LeagueAdapter.Holder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeagueAdapter.Holder {
        val binding = ItemRecyclerLeagueBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding, fragment)
    }

    override fun onBindViewHolder(holder: LeagueAdapter.Holder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    class Holder(val binding: ItemRecyclerLeagueBinding, val fragment: Fragment) :
        RecyclerView.ViewHolder(binding.root){
            fun bind(items: League_item){
                binding.name.text = items.name
                binding.times.text = items.times
                binding.point.text = items.point
                binding.lank.text = items.lank
            }
        }

}