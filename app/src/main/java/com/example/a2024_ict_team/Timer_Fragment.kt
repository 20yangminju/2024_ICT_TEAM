package com.example.a2024_ict_team

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.a2024_ict_team.databinding.FragmentTimerBinding
import java.util.Timer
import kotlin.concurrent.timer


class Timer_Fragment : Fragment() {

    lateinit var binding: FragmentTimerBinding
    var time = 0
    var timerTask : Timer? = null

    private fun startTimer() {
        timerTask = timer(period = 10){
            time++

            var sec = time / 100
            var milli = time % 100

            requireActivity().runOnUiThread {
                binding.timer.text = "${sec} : ${milli}"
            }
        }
    }

    private fun restTimer() {
        timerTask?.cancel()
        time = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTimerBinding.inflate(layoutInflater)
        restTimer()
        startTimer()
        // Inflate the layout for this fragment
        return binding.root
    }
}