package com.example.a2024_ict_team

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.a2024_ict_team.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}