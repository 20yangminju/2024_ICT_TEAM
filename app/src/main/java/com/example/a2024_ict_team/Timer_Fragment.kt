package com.example.a2024_ict_team

import android.app.AlertDialog
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
    private lateinit var exerciseMessageTextView: TextView
    private lateinit var nfcTagMessageTextView: TextView
    private var secondsElapsed = 0
    private val handler = Handler(Looper.getMainLooper())
    private var isTimerRunning = false

    private val runnable = object : Runnable {
        override fun run() {
            secondsElapsed++
            timerTextView.text = String.format("%d:%02d", secondsElapsed / 60, secondsElapsed % 60)
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
        exerciseMessageTextView = view.findViewById(R.id.exerciseMessageTextView)
        nfcTagMessageTextView = view.findViewById(R.id.nfcTagMessageTextView)

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
                            val secondLastChar = if (text.length > 1) text[text.length - 2] else '1'
                            exerciseMessageTextView.text = "\n한국항공대역의 ${secondLastChar}번 출구 방향 계단을 오르고 있어요 🔥\n\n계단 오르기 완료 후\n계단 벽면의 NFC에 태깅을 하면 운동 측정이 완료됩니다."
                            val lastChar = text.last()
                            if (lastChar == '1') {
                                startTimer()
                            } else if (lastChar == '0') {
                                stopTimer()
                                showCompletionDialog()
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
            nfcTagMessageTextView.text = "계단을 오르는 중입니다"
            exerciseMessageTextView.visibility = View.VISIBLE
        }
    }

    // Stop the timer
    private fun stopTimer() {
        if (isTimerRunning) {
            handler.removeCallbacks(runnable)
            isTimerRunning = false
            timerTextView.text = "0:00"
            nfcTagMessageTextView.text = "계단 벽면의 NFC를 태깅하세요"
            exerciseMessageTextView.visibility = View.INVISIBLE
        }
    }

    // Show completion dialog
    private fun showCompletionDialog() {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle("운동 측정 완료")
        builder.setMessage("운동 측정이 완료되었습니다.")
        builder.setPositiveButton("확인") { dialog, _ ->
            dialog.dismiss()
        }
        builder.show()
    }
}
