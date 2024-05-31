package com.example.a2024_ict_team

import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.Ndef
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import java.nio.charset.Charset

class Timer_Fragment : Fragment() {

    private lateinit var timerTextView: TextView
    private lateinit var nfcDataTextView: TextView
    private var secondsElapsed = 0
    private val handler = Handler(Looper.getMainLooper())
    private var isTimerRunning = false

    private val runnable = object : Runnable {
        override fun run() {
            secondsElapsed++
            timerTextView.text = "Seconds elapsed: $secondsElapsed"
            handler.postDelayed(this, 1000)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_timer_, container, false)
        timerTextView = view.findViewById(R.id.timerTextView)
        nfcDataTextView = view.findViewById(R.id.nfcDataTextView)

        // Initialize NFC Adapter
        val nfcAdapter = NfcAdapter.getDefaultAdapter(activity)
        if (nfcAdapter == null) {
            Toast.makeText(activity, "NFC is not available on this device.", Toast.LENGTH_SHORT).show()
        } else {
            nfcAdapter.enableReaderMode(activity, { tag -> handleNfcTag(tag) }, NfcAdapter.FLAG_READER_NFC_A or NfcAdapter.FLAG_READER_NFC_B, null)
        }

        return view
    }

    // Handle NFC tag detection
    private fun handleNfcTag(tag: Tag?) {
        if (tag != null) {
            val ndef = Ndef.get(tag)
            if (ndef != null) {
                ndef.connect()
                val ndefMessage = ndef.ndefMessage
                ndef.close()
                if (ndefMessage != null) {
                    val text = ndefMessage.records[0].payload.toString(Charset.forName("UTF-8"))
                    activity?.runOnUiThread {
                        nfcDataTextView.text = "NFC Data: $text"
                        if (text.isNotEmpty()) {
                            val lastChar = text.last()
                            if (lastChar == '1') {
                                startTimer()
                            } else if (lastChar == '0') {
                                stopTimer()
                            }
                        }
                    }
                }
            }
        }
    }

    // Start the timer
    private fun startTimer() {
        if (!isTimerRunning) {
            secondsElapsed = 0
            handler.post(runnable)
            isTimerRunning = true
        }
    }

    // Stop the timer
    private fun stopTimer() {
        if (isTimerRunning) {
            handler.removeCallbacks(runnable)
            isTimerRunning = false
            timerTextView.text = "Timer stopped"
        }
    }
}
