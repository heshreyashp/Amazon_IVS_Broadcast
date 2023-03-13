package com.amazon.ivs.broadcast.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.amazon.ivs.broadcast.R
import com.amazon.ivs.broadcast.databinding.ActivityDashboardBinding
import com.amazon.ivs.broadcast.ui.activities.MainActivity
import com.amazon.ivs.broadcast.watchlive.activities.WatchMainActivity

class DashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.showLive.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, WatchMainActivity::class.java))
        })
        binding.startLive.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        })
    }
}