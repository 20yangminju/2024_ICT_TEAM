package com.example.a2024_ict_team

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a2024_ict_team.databinding.FragmentLeagueBinding

class League_Fragment : Fragment() {
    private lateinit var leagueAdapter: LeagueAdapter
    private lateinit var binding: FragmentLeagueBinding

    private var items = ArrayList<LeagueItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        items.add(LeagueItem("이유경", "1280 K", "1"))
        items.add(LeagueItem("양민주", "1000 K", "2"))
        items.add(LeagueItem("정윤석", "980 K", "3"))
        items.add(LeagueItem("정혜윤", "300 K", "4"))
        items.add(LeagueItem("김세훈", "500 K", "5"))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLeagueBinding.inflate(inflater, container, false)
        binding.rvLeague.layoutManager = LinearLayoutManager(context)
        leagueAdapter = LeagueAdapter(items, this)
        binding.rvLeague.adapter = leagueAdapter

        return binding.root
    }
}
