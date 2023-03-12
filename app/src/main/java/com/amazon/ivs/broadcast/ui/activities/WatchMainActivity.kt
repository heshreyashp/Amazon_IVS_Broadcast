package com.amazon.ivs.broadcast.ui.activities

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import com.amazon.ivs.broadcast.App
import com.amazon.ivs.broadcast.R
import com.amazon.ivs.broadcast.databinding.ActivityWatchMainBinding
import com.amazonaws.ivs.player.MediaPlayer

class WatchMainActivity : AppCompatActivity(), SurfaceHolder.Callback {

    private lateinit var binding: ActivityWatchMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.component.inject(this)
        binding = ActivityWatchMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setContentView(R.layout.activity_watch_main)
        initSurface()
    }


    private fun initSurface() {
        binding.surfaceView.holder.addCallback(this)

    }

    override fun surfaceCreated(p0: SurfaceHolder) {
        TODO("Not yet implemented")
    }

    override fun surfaceChanged(p0: SurfaceHolder, p1: Int, p2: Int, p3: Int) {
        TODO("Not yet implemented")
    }

    override fun surfaceDestroyed(p0: SurfaceHolder) {
        TODO("Not yet implemented")
    }


    fun startPlayer(){

    }
    fun releasePlayer(){

    }
}