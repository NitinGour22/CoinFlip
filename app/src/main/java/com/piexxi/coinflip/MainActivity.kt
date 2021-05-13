package com.piexxi.coinflip

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.widget.SwitchCompat
import kotlin.math.round

class MainActivity : AppCompatActivity() {

    private lateinit var iv_Coin: ImageView
    private lateinit var sw_Simulate: SwitchCompat
    private lateinit var bt_Flip: Button
    private lateinit var bt_Reset: Button
    private lateinit var tv_TotalFlips: TextView
    private lateinit var tv_TotalHeads: TextView
    private lateinit var tv_TotalTails: TextView
    private lateinit var pb_HeadPercent: ProgressBar
    private lateinit var pb_TailPercent: ProgressBar
    private lateinit var tv_HeadPercent: TextView
    private lateinit var tv_TailPercent: TextView
    private lateinit var et_NumberOfFlips: EditText
    private lateinit var bt_Simulate: Button

    private var heads = 0
    private var tails = 0
    private var total = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
    }

    private fun init() {
        initView()
        initListners()
    }

    private fun initView() {
        iv_Coin = findViewById(R.id.iv_coin)
        sw_Simulate = findViewById(R.id.sw_simulate)
        bt_Flip = findViewById(R.id.bt_flip)
        bt_Reset = findViewById(R.id.bt_reset)
        tv_TotalFlips = findViewById(R.id.tv_total_flips)
        tv_TotalHeads = findViewById(R.id.tv_total_heads)
        tv_TotalTails = findViewById(R.id.tv_total_tails)
        pb_HeadPercent = findViewById(R.id.pb_heads_percent)
        pb_TailPercent = findViewById(R.id.pb_tails_percent)
        tv_HeadPercent = findViewById(R.id.tv_head_percent)
        tv_TailPercent = findViewById(R.id.tv_tails_percent)
        et_NumberOfFlips = findViewById(R.id.et_numberof_flips)
        bt_Simulate = findViewById(R.id.bt_simulate)
    }

    private fun initListners() {
        sw_Simulate.setOnCheckedChangeListener { buttonView, isChecked -> enableSim(isChecked) }
        bt_Flip.setOnClickListener { flip() }
        bt_Reset.setOnClickListener { reset() }
        bt_Simulate.setOnClickListener { simulate() }
    }

    private fun enableSim(checked: Boolean) {

        if (checked) {
            //Log.i("test","switch is On")
            et_NumberOfFlips.visibility = View.VISIBLE
            bt_Simulate.visibility = View.VISIBLE
        } else {
            // Log.i("test","switch is Off")
            et_NumberOfFlips.visibility = View.INVISIBLE
            bt_Simulate.visibility = View.INVISIBLE
        }

    }

    private fun flip() {
        val randomNumber = (0..1).random()
        if (randomNumber == 0) {
            updateFlip("heads")
        } else {
            updateFlip("tails")
        }
    }

    private fun updateFlip(coinValue: String) {
        if (coinValue == "heads") {
            heads++
            iv_Coin.setImageResource(R.drawable.ic_heads_icon)
        } else {
            tails++
            iv_Coin.setImageResource(R.drawable.ic_tails_icon)
        }
        total++

        tv_TotalFlips.text = "Total Flips : $total"
        tv_TotalHeads.text = "Total Heads : $heads"
        tv_TotalTails.text = "Total Tails : $tails"

        updateStatistics()
    }

    private fun updateStatistics() {
        var headPercentResult = 0.0
        var tailPercentResult = 0.0

        if (total != 0) {
            headPercentResult = round((heads.toDouble() / total) * 100)
            tailPercentResult = round((tails.toDouble() / total) * 100)
        }

        tv_HeadPercent.text = "Heads : $headPercentResult %"
        tv_TailPercent.text = "Tails : $tailPercentResult %"

        pb_HeadPercent.progress = headPercentResult.toInt()
        pb_TailPercent.progress = tailPercentResult.toInt()
    }

    private fun reset() {

        iv_Coin.setImageResource(R.drawable.ic_flip_icon)

        heads = 0
        tails = 0
        total = 0

        tv_TotalFlips.text = "Total Flips : $total"
        tv_TotalHeads.text = "Total Heads : $heads"
        tv_TotalTails.text = "Total Tails : $tails"

        updateStatistics()

    }

    private fun simulate() {
        val enterNumber = et_NumberOfFlips.text.toString().toInt()
        et_NumberOfFlips.setText("")
        hideKeyboard()

        for (i in 1..enterNumber) {
            flip()
        }
    }

    private fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(et_NumberOfFlips.windowToken, 0)

    }


}