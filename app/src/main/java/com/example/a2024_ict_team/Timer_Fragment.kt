package com.example.a2024_ict_team

import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.Ndef
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.nio.charset.Charset
import java.time.LocalDateTime


class Timer_Fragment : Fragment() {

    private val userID = "ymj10003"
    private lateinit var timerTextView: TextView
    private lateinit var nfcDataTextView: TextView
    private lateinit var exerciseMessageTextView: TextView
    private lateinit var nfcTagMessageTextView: TextView
    private var secondsElapsed = 0
    private val handler = Handler(Looper.getMainLooper())
    private var isTimerRunning = false
    private lateinit var workstart : LocalDateTime
    private lateinit var workend : LocalDateTime
    private lateinit var location : String
    private lateinit var firbaseDatabase: FirebaseDatabase
    private lateinit var databasereference: DatabaseReference
    private lateinit var worknum : Any





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
        val view = inflater.inflate(R.layout.fragment_timer_, container, false)
        timerTextView = view.findViewById(R.id.timerTextView)
        nfcDataTextView = view.findViewById(R.id.nfcDataTextView)
        exerciseMessageTextView = view.findViewById(R.id.exerciseMessageTextView)
        nfcTagMessageTextView = view.findViewById(R.id.nfcTagMessageTextView)

        firbaseDatabase = FirebaseDatabase.getInstance()
        databasereference = firbaseDatabase.getReference("user")

        databasereference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                worknum = snapshot.child(userID).child("worknum").value as Long
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })


        val nfcAdapter = NfcAdapter.getDefaultAdapter(activity)
        if (nfcAdapter == null) {
            Toast.makeText(activity, "NFC is not available on this device.", Toast.LENGTH_SHORT).show()
        } else {
            nfcAdapter.enableReaderMode(activity, { tag -> handleNfcTag(tag) }, NfcAdapter.FLAG_READER_NFC_A or NfcAdapter.FLAG_READER_NFC_B, null)
        }



        return view
    }

    private fun handleNfcTag(tag: Tag?) {
        if (tag != null) {
            val ndef = Ndef.get(tag)
            if (ndef != null) {
                ndef.connect()
                val ndefMessage = ndef.ndefMessage
                ndef.close()
                if (ndefMessage != null) {
                    val text = ndefMessage.records[0].payload.toString(Charset.forName("UTF-8"))
                    var text2 = ndefMessage.records[1].payload.toString(Charset.forName("UTF-8"))
                    location = text2.replace("[a-zA-Z]".toRegex(), "")
                    activity?.runOnUiThread {
                        nfcDataTextView.text = "NFC Data: $text"
                        if (text.isNotEmpty()) {
                            val secondLastChar = if (text.length > 1) text[text.length - 2] else '1'
                            exerciseMessageTextView.text = "\n${location} ${secondLastChar}번 출구 방향 계단을 오르고 있어요 🔥\n\n계단 오르기 완료 후\n계단 벽면의 NFC에 태깅을 하면 운동 측정이 완료됩니다."
                            val lastChar = text.last()
                            if (lastChar == '1') {

                                startTimer()
                            } else if (lastChar == '0') {
                                stopTimer()
                                val showDailyGoalAchieved = (secondLastChar == '1')
                                showCompletionDialog(showDailyGoalAchieved)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun startTimer() {
        if (!isTimerRunning) {
            workstart = LocalDateTime.now()
            secondsElapsed = 0
            handler.post(runnable)
            isTimerRunning = true
            nfcTagMessageTextView.text = "계단을 오르는 중입니다"

            exerciseMessageTextView.visibility = View.VISIBLE
        }
    }

    private fun stopTimer() {
        if (isTimerRunning) {
            workend = LocalDateTime.now()
            handler.removeCallbacks(runnable)
            isTimerRunning = false
            timerTextView.text = "0:00"
            nfcTagMessageTextView.text = "계단 벽면의 NFC를 태깅하세요"
            exerciseMessageTextView.visibility = View.INVISIBLE
            updatefirebase()


        }
    }

    private fun showCompletionDialog(showDailyGoalAchieved: Boolean) {
        val dialog = CompletionDialogFragment(showDailyGoalAchieved)
        dialog.show(parentFragmentManager, "CompletionDialog")
    }

    private fun updatefirebase() {
        val simpleDate = workend.toLocalDate().toString()
        var start_time = workstart.toLocalTime().toString()
        var end_time = workend.toLocalTime().toString()

        var worknum_int = worknum.toString().toInt() + 1

        databasereference.child(userID).child("worknum").setValue(worknum_int)

        start_time = start_time.substring(0 until 5)
        end_time = end_time.substring(0 until 5)
        val Date = simpleDate + end_time + worknum_int.toString()
        val timerange = simpleDate + " " + start_time + " ~ " + end_time
        databasereference.child(userID).child("recentwork").child(Date).child("location").setValue(location)
        databasereference.child(userID).child("recentwork").child(Date).child("duration").setValue(secondsElapsed)
        databasereference.child(userID).child("recentwork").child(Date).child("Date").setValue(simpleDate)
        databasereference.child(userID).child("recentwork").child(Date).child("timerange").setValue(timerange)
    }
}
