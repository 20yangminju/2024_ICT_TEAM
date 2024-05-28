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

    private var items = ArrayList<League_item>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        items.add(League_item("이유경", "11", "1280", "1"))
        items.add(League_item("양민주", "9", "1000", "2"))
        items.add(League_item("정윤석", "7", "980", "3"))
        items.add(League_item("정혜윤", "4", "300", "4"))
        items.add(League_item("김세훈", "5", "500", "5"))
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
