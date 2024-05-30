package com.example.a2024_ict_team

import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.nfc.NdefMessage
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.MifareClassic
import android.nfc.tech.Ndef
import android.nfc.tech.NfcA
import android.nfc.tech.NfcB
import android.nfc.tech.NfcF
import android.nfc.tech.NfcV
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.a2024_ict_team.databinding.ActivityNaviBinding

private const val TAG_MAIN = "Main_Fragment"
private const val TAG_RECORD = "RecordFragment"
private const val TAG_MY_PAGE = "MyPage_Fragment"
private const val TAG_LEAGUE = "League_Fragment"
private const val TAG_TIMER = "Timer_Fragment"

class NaviActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNaviBinding
    private var nfcAdapter: NfcAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNaviBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setFragment(TAG_MAIN, Main_Fragment())

        binding.navigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.Main_Fragment -> setFragment(TAG_MAIN, Main_Fragment())
                R.id.RecordFragment -> setFragment(TAG_RECORD, RecordFragment())
                R.id.League_Fragment -> setFragment(TAG_LEAGUE, League_Fragment())
                R.id.MyPage_Fragment -> setFragment(TAG_MY_PAGE, MyPage_Fragment())
                //R.id.Timer_Fragment -> setFragment(TAG_TIMER, Timer_Fragment())
            }
            true
        }

        binding.timer.text = "NFC태깅없음"

        nfcAdapter = NfcAdapter.getDefaultAdapter(this)
    }

    override fun onResume() {
        super.onResume()
        val intent = Intent(this, javaClass).apply {
            addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        }
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
        )
        val filters = arrayOf(
            IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED).apply {
                try {
                    addDataType("*/*")
                } catch (e: IntentFilter.MalformedMimeTypeException) {
                    throw RuntimeException("Failed to add MIME type.", e)
                }
            },
            IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED),
            IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED)
        )
        val techListsArray = arrayOf(
            arrayOf(Ndef::class.java.name),
            arrayOf(MifareClassic::class.java.name),
            arrayOf(NfcA::class.java.name),
            arrayOf(NfcB::class.java.name),
            arrayOf(NfcF::class.java.name),
            arrayOf(NfcV::class.java.name)
        )

        nfcAdapter?.enableForegroundDispatch(this, pendingIntent, filters, techListsArray)
    }

    override fun onPause() {
        super.onPause()
        nfcAdapter?.disableForegroundDispatch(this)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        if (intent == null) return

        val action = intent.action
        val tag = intent.getParcelableExtra<Tag>(NfcAdapter.EXTRA_TAG)

        when (action) {
            NfcAdapter.ACTION_NDEF_DISCOVERED -> handleNdefDiscovered(intent)
            NfcAdapter.ACTION_TAG_DISCOVERED -> handleTagDiscovered(tag)
            NfcAdapter.ACTION_TECH_DISCOVERED -> handleTechDiscovered(tag)
            else -> binding.timer.text = "알 수 없는 NFC 액션"
        }
    }

    private fun handleNdefDiscovered(intent: Intent) {
        val rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES)
        if (rawMsgs != null) {
            val msgs = rawMsgs.map { it as NdefMessage }.toTypedArray()
            val ndefMessage = msgs[0]
            val record = ndefMessage.records[0]
            val payload = record.payload

            if (payload != null) {
                val text = String(payload)
                binding.timer.text = text
            } else {
                binding.timer.text = "NDEF 메시지 없음"
            }
        } else {
            binding.timer.text = "NDEF 메시지 없음"
        }
    }

    private fun handleTagDiscovered(tag: Tag?) {
        if (tag != null) {
            val techList = tag.techList.joinToString(", ")
            binding.timer.text = "TAG 발견: $techList"
        } else {
            binding.timer.text = "TAG 정보 없음"
        }
    }

    private fun handleTechDiscovered(tag: Tag?) {
        if (tag != null) {
            val techList = tag.techList.joinToString(", ")
            binding.timer.text = "TECH 발견: $techList"

            for (tech in tag.techList) {
                when (tech) {
                    MifareClassic::class.java.name -> handleMifareClassic(tag)
                    Ndef::class.java.name -> handleNdef(tag)
                    NfcA::class.java.name -> handleNfcA(tag)
                    NfcB::class.java.name -> handleNfcB(tag)
                    NfcF::class.java.name -> handleNfcF(tag)
                    NfcV::class.java.name -> handleNfcV(tag)
                    else -> binding.timer.text = "지원하지 않는 TECH: $tech"
                }
            }
        } else {
            binding.timer.text = "TECH 정보 없음"
        }
    }

    private fun handleMifareClassic(tag: Tag) {
        val mifareClassic = MifareClassic.get(tag)
        binding.timer.text = "Mifare Classic 태그 처리"
        setFragment(TAG_TIMER, Timer_Fragment())
        // Mifare Classic 처리 로직 추가
    }

    private fun handleNdef(tag: Tag) {
        val ndef = Ndef.get(tag)
        binding.timer.text = "NDEF 태그 처리"
        setFragment(TAG_TIMER, Timer_Fragment())
    }

    private fun handleNfcA(tag: Tag) {
        val nfcA = NfcA.get(tag)
        binding.timer.text = "NfcA 태그 처리"
        setFragment(TAG_TIMER, Timer_Fragment())
        // NfcA 처리 로직 추가
    }

    private fun handleNfcB(tag: Tag) {
        val nfcB = NfcB.get(tag)
        binding.timer.text = "NfcB 태그 처리"
        setFragment(TAG_TIMER, Timer_Fragment())
        // NfcB 처리 로직 추가
    }

    private fun handleNfcF(tag: Tag) {
        val nfcF = NfcF.get(tag)
        binding.timer.text = "NfcF 태그 처리"
        setFragment(TAG_TIMER, Timer_Fragment())
        // NfcF 처리 로직 추가
    }

    private fun handleNfcV(tag: Tag) {
        val nfcV = NfcV.get(tag)
        binding.timer.text = "NfcV 태그 처리"
        setFragment(TAG_TIMER, Timer_Fragment())
        // NfcV 처리 로직 추가
    }

    private fun setFragment(tag: String, fragment: Fragment) {
        val manager: FragmentManager = supportFragmentManager
        val fragTransaction = manager.beginTransaction()

        if (manager.findFragmentByTag(tag) == null) {
            fragTransaction.add(R.id.mainFrameLayout, fragment, tag)
        }

        val main = manager.findFragmentByTag(TAG_MAIN)
        val record = manager.findFragmentByTag(TAG_RECORD)
        val mypage = manager.findFragmentByTag(TAG_MY_PAGE)
        val league = manager.findFragmentByTag(TAG_LEAGUE)
        val timer = manager.findFragmentByTag(TAG_TIMER)

        if (main != null) fragTransaction.hide(main)
        if (record != null) fragTransaction.hide(record)
        if (mypage != null) fragTransaction.hide(mypage)
        if (league != null) fragTransaction.hide(league)
        if (timer != null) fragTransaction.hide(timer)

        when (tag) {
            TAG_MAIN -> if (main != null) fragTransaction.show(main)
            TAG_RECORD -> if (record != null) fragTransaction.show(record)
            TAG_MY_PAGE -> if (mypage != null) fragTransaction.show(mypage)
            TAG_LEAGUE -> if (league != null) fragTransaction.show(league)
            TAG_TIMER -> if (timer != null) fragTransaction.show(timer)
        }

        fragTransaction.commitAllowingStateLoss()
    }
}
