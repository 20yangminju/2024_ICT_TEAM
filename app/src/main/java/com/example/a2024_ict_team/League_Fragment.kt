package com.example.a2024_ict_team

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.a2024_ict_team.databinding.FragmentLeagueBinding
import androidx.recyclerview.widget.LinearLayoutManager


class League_Fragment : Fragment() {
    private lateinit var leagueAdapter: LeagueAdapter
    lateinit var binding: FragmentLeagueBinding

    var Items = ArrayList <League_item>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Items.add(League_item("이유경", "11", "1280", "1"))
        Items.add(League_item("양민주", "9", "1000", "2"))
        Items.add(League_item("정윤석", "7", "980", "3"))
        Items.add(League_item("정혜윤", "4", "300", "4"))
        Items.add(League_item("김세훈", "5", "500", "5"))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLeagueBinding.inflate(layoutInflater)
        binding.rvLeague.layoutManager = LinearLayoutManager(context)
        leagueAdapter = LeagueAdapter(Items, this)
        binding.rvLeague.adapter = leagueAdapter

        return binding.root
    }


}