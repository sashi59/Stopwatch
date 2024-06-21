package com.example.stopwatchprodigy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.stopwatchprodigy.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private var isRunning = false
    private var timerSeconds = 0

    private var handler = Handler(Looper.getMainLooper())
    private val runable = object : Runnable{
        override fun run() {

            timerSeconds++
            val hours = timerSeconds / 3600
            val mins = (timerSeconds % 3600) / 60
            val secs = timerSeconds % 60

            val time = String.format("%02d:%02d:%02d", hours, mins, secs)

            binding.resultTime.text = time

            handler.postDelayed(this, 1000)

        }

    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.startBtn.setOnClickListener {
            startTimmer()
        }

        binding.stopBtn.setOnClickListener {
            stopTimmer()
        }

        binding.resetBtn.setOnClickListener {
            resetTimmer()
        }
    }

    private fun startTimmer() {
        if(!isRunning){
            handler.postDelayed(runable, 1000)
            isRunning = true

            binding.apply {
                startBtn.isEnabled = false
                stopBtn.isEnabled = true
                resetBtn.isEnabled = true
            }
        }

    }
    private fun stopTimmer() {
        if(isRunning){
            handler.removeCallbacks(runable)
            isRunning = false

            binding.apply {
                startBtn.isEnabled = true
                startBtn.text = "Resume"
                stopBtn.isEnabled = false
                resetBtn.isEnabled = true
            }
        }

    }
    private fun resetTimmer() {
        stopTimmer()
        timerSeconds = 0
        binding.apply {
        resultTime.text = "00:00:00"
            startBtn.text = "Start"
            startBtn.isEnabled = true
            resetBtn.isEnabled = false
            stopBtn.isEnabled = false
        }
    }



}