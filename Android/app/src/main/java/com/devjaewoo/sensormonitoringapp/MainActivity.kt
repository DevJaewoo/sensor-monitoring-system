package com.devjaewoo.sensormonitoringapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.android.volley.Request
import com.devjaewoo.sensormonitoringapp.databinding.ActivityMainBinding
import com.devjaewoo.sensormonitoringapp.request.RequestHandler

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)

        binding.button.setOnClickListener {
            RequestHandler.request("/api/1/sensor", null, {jsonObject -> Log.d(TAG, "onCreate: $jsonObject")}, null, false, Request.Method.GET)
        }

        setContentView(binding.root)
    }
}