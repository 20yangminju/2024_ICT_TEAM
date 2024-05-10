package com.example.a2024_ict_team

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.a2024_ict_team.databinding.ActivityNaviBinding


private const val TAG_MAIN = "Main_Fragment"
private const val TAG_RECORD = "RecordFragment"
private const val TAG_MY_PAGE = "MyPage_Fragment"
private const val TAG_LEAGEUE = "League_Fragment"
class NaviActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNaviBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNaviBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setFragment(TAG_MAIN, Main_Fragment())

    }

    private fun setFragment(tag: String, fragment: Fragment){
        val manager: FragmentManager = supportFragmentManager
        val fragTransaction = manager.beginTransaction()

        if (manager.findFragmentByTag(tag)==null){
            fragTransaction.add(R.id.mainFrameLayout, fragment, tag)
        }

        val main = manager.findFragmentByTag(TAG_MAIN)
        val record = manager.findFragmentByTag(TAG_RECORD)
        val mypage = manager.findFragmentByTag(TAG_MY_PAGE)
        val league = manager.findFragmentByTag(TAG_LEAGEUE)

        if(main != null)
            fragTransaction.hide(main)
        if(record != null)
            fragTransaction.hide(record)
        if (mypage != null)
            fragTransaction.hide(mypage)
        if (league != null)
            fragTransaction.hide(league)

        if (tag == TAG_MAIN){
            if(main != null)
                fragTransaction.show(main)
        }
        else if (tag == TAG_RECORD){
            if (record != null)
                fragTransaction.show(record)
        }
        else if (tag == TAG_MY_PAGE){
            if (mypage != null)
                fragTransaction.show(mypage)
        }
        else if (tag == TAG_LEAGEUE){
            if (league != null)
                fragTransaction.show(league)
        }




        fragTransaction.commitAllowingStateLoss()
    }
}