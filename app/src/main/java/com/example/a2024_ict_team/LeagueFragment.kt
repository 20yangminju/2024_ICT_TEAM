package com.example.a2024_ict_team

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a2024_ict_team.databinding.FragmentLeagueBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class LeagueFragment : Fragment() {
    private lateinit var leagueAdapter: LeagueAdapter
    private lateinit var binding: FragmentLeagueBinding
    private lateinit var database: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLeagueBinding.inflate(inflater, container, false)

        database = FirebaseDatabase.getInstance().getReference("user")

        setupRecyclerView()
        league_listupdate()

        database.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                league_listupdate()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        return binding.root
    }
    fun setupRecyclerView(){
        binding.rvLeague.layoutManager = LinearLayoutManager(context)
        leagueAdapter = LeagueAdapter(emptyList())
        binding.rvLeague.adapter = leagueAdapter
    }

    fun league_listupdate(){
        var temp = mutableListOf<LeagueItem>()
        database.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (ds in snapshot.children){
                    val name = ds.key.toString()
                    val point = ds.child("point").value as Long

                    val item = LeagueItem(name.toString(), point.toString().toInt(), 0)

                    temp.add(item)
                }
                temp.sortByDescending { it.points }
                for(index in temp.indices){
                    temp[index].rank = index + 1
                }

                binding.rvLeague.adapter = LeagueAdapter(temp.toList())
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })


    }
}
