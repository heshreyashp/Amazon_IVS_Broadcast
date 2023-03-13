package com.amazon.ivs.broadcast.watchlive.activities

import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import android.widget.SeekBar
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.amazon.ivs.broadcast.App
import com.amazon.ivs.broadcast.R
import com.amazon.ivs.broadcast.databinding.ViewPlayerControlsBinding
import com.amazon.ivs.broadcast.databinding.WatchActivityMainBinding
import com.amazonaws.ivs.player.Player
import com.amazon.ivs.broadcast.watchlive.activities.dialogs.QualityDialog
import com.amazon.ivs.broadcast.watchlive.activities.dialogs.RateDialog
import com.amazon.ivs.broadcast.watchlive.activities.dialogs.SourceDialog
import com.amazon.ivs.broadcast.watchlive.common.Configuration.HIDE_CONTROLS_DELAY
import com.amazon.ivs.broadcast.watchlive.common.Configuration.TAG
import com.amazon.ivs.broadcast.watchlive.common.enums.PlayingState
import com.amazon.ivs.broadcast.watchlive.common.launchMain
import com.amazon.ivs.broadcast.watchlive.common.lazyViewModel
import com.amazon.ivs.broadcast.watchlive.common.showDialog
import com.amazon.ivs.broadcast.watchlive.data.LocalCacheProvider
import com.amazon.ivs.broadcast.watchlive.viewModel.MainViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import javax.inject.Inject


class WatchMainActivity : AppCompatActivity(), SurfaceHolder.Callback {

    @Inject
    lateinit var cacheProvider: LocalCacheProvider

    private val qualityDialog by lazy { QualityDialog(this, viewModel) }
    private val rateDialog by lazy { RateDialog(this, viewModel) }
    private val sourceDialog by lazy { SourceDialog(this, viewModel) }

    private val timerHandler = Handler(Looper.getMainLooper())
    private val timerRunnable = kotlinx.coroutines.Runnable {
        launchMain {
            Log.d(TAG, "Hiding controls")
           /* if (qualityDialog.isOpened() || rateDialog.isOpened() || sourceDialog.isOpened()) {
                return@launchMain
            }*/
            viewModel.toggleControls(false)
        }
    }

    private val viewModel: MainViewModel by lazyViewModel {
        MainViewModel(application, cacheProvider)
    }

    val sheetListener by lazy {
        object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                binding.sheetBackground.visibility = View.VISIBLE
                binding.sheetBackground.alpha = slideOffset
            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == BottomSheetBehavior.STATE_HIDDEN || newState == BottomSheetBehavior.STATE_COLLAPSED) {
                    binding.sheetBackground.visibility = View.GONE
                    binding.sheetBackground.alpha = 0f
                }
            }
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            playerBinding.playerControlsRoot.layoutParams.width =
                ConstraintLayout.LayoutParams.MATCH_PARENT
            //binding.rateSheet.layoutParams.width = ConstraintLayout.LayoutParams.MATCH_PARENT
            //binding.qualitySheet.layoutParams.width = ConstraintLayout.LayoutParams.MATCH_PARENT
        } else {
            playerBinding.playerControlsRoot.layoutParams.width =
                resources.getDimension(R.dimen.player_control_landscape_width).toInt()
            //binding.rateSheet.layoutParams.width =
            resources.getDimension(R.dimen.player_control_landscape_width).toInt()
            //binding.qualitySheet.layoutParams.width =
            resources.getDimension(R.dimen.player_control_landscape_width).toInt()
        }
    }

    private lateinit var binding: WatchActivityMainBinding
    private lateinit var playerBinding: ViewPlayerControlsBinding

    private lateinit var surfaceView: SurfaceView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = WatchActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        playerBinding = ViewPlayerControlsBinding.inflate(layoutInflater)
        val view1 = binding.root
        setContentView(view1)
        App.component.inject(this)
        DataBindingUtil.setContentView<WatchActivityMainBinding>(this, R.layout.watch_activity_main)
            .apply {
                data = viewModel
                lifecycleOwner = this@WatchMainActivity
            }
        surfaceView = findViewById(R.id.surface_view)
        // Surface view listener for rotation handling
        surfaceView.addOnLayoutChangeListener(object : View.OnLayoutChangeListener {
            override fun onLayoutChange(
                v: View?,
                left: Int,
                top: Int,
                right: Int,
                bottom: Int,
                oldLeft: Int,
                oldTop: Int,
                oldRight: Int,
                oldBottom: Int
            ) {
                if (left != oldLeft || top != oldTop || right != oldRight || bottom != oldBottom) {
                    val width = viewModel.playerParamsChanged.value?.first
                    val height = viewModel.playerParamsChanged.value?.second
                    if (width != null && height != null) {
                        surfaceView.post {
                            Log.d(
                                TAG, "On rotation player layout params changed $width $height"
                            )
                            fitSurfaceToView(surfaceView, width, height)
                        }
                    }
                }
            }
        })

        binding.sheetBackground.setOnClickListener {
            qualityDialog.dismiss()
            rateDialog.dismiss()
            sourceDialog.dismiss()
        }

        viewModel.playerState.observe(this, Observer { state ->
            when (state) {
                Player.State.BUFFERING -> {
                    // Indicates that the Player is buffering content
                    viewModel.buffering.value = true
                    viewModel.buttonState.value = PlayingState.PLAYING
                    playerBinding.statusText.setTextColor(Color.WHITE)
                    playerBinding.statusText.text = getString(R.string.buffering)
                }
                Player.State.IDLE -> {
                    // Indicates that the Player is idle
                    viewModel.buffering.value = false
                    viewModel.buttonState.value = PlayingState.PAUSED
                    playerBinding.statusText.setTextColor(Color.WHITE)
                    playerBinding.statusText.text = getString(R.string.paused)
                }
                Player.State.READY -> {
                    // Indicates that the Player is ready to play the loaded source
                    viewModel.buffering.value = false
                    viewModel.buttonState.value = PlayingState.PAUSED
                    playerBinding.statusText.setTextColor(Color.WHITE)
                    playerBinding.statusText.text = getString(R.string.paused)
                }
                Player.State.ENDED -> {
                    // Indicates that the Player reached the end of the stream
                    viewModel.buffering.value = false
                    viewModel.buttonState.value = PlayingState.PAUSED
                    playerBinding.statusText.setTextColor(Color.WHITE)
                    playerBinding.statusText.text = getString(R.string.ended)
                }
                Player.State.PLAYING -> {
                    // Indicates that the Player is playing
                    viewModel.buffering.value = false
                    viewModel.buttonState.value = PlayingState.PLAYING
                    playerBinding.statusText.setTextColor(
                        (if (viewModel.liveStream.value != null && viewModel.liveStream.value == true) Color.RED else Color.WHITE)
                    )
                    playerBinding.statusText.text =
                        if (viewModel.liveStream.value != null && viewModel.liveStream.value == true) getString(
                            R.string.live_status
                        ) else getString(R.string.vod_status)
                }
                else -> { /* Ignored */
                }
            }
        })

        viewModel.buttonState.observe(this, Observer { state ->
            viewModel.isPlaying.value = state == PlayingState.PLAYING
        })

        viewModel.playerParamsChanged.observe(this, Observer {
            Log.d(TAG, "Player layout params changed ${it.first} ${it.second}")
            fitSurfaceToView(surfaceView, it.first, it.second)
        })

        viewModel.errorHappened.observe(this, Observer {
            Log.d(TAG, "Error dialog is shown")
            showDialog(it.first, it.second)
        })

        initSurface()
        initButtons()
        viewModel.playerStart(surfaceView.holder.surface)

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                when {
                    //qualityDialog.isOpened() -> qualityDialog.dismiss()
                    //rateDialog.isOpened() -> rateDialog.dismiss()
                    //sourceDialog.isOpened() -> sourceDialog.dismiss()
                    else -> finish()
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        viewModel.play()
    }

    override fun onPause() {
        super.onPause()
        viewModel.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.playerRelease()
        qualityDialog.release()
        rateDialog.release()
        sourceDialog.release()
        surfaceView.holder.removeCallback(this)
    }


    private fun initSurface() {
       // binding.surfaceView.holder.addCallback(this)



        surfaceView.holder.addCallback(this)
        binding.playerRoot.setOnClickListener {
            Log.d(TAG, "Player screen clicked")
            when (playerBinding.playerControls.visibility) {
                View.VISIBLE -> {
                    viewModel.toggleControls(false)
                }
                View.GONE -> {
                    viewModel.toggleControls(true)
                    restartTimer()
                }
            }
        }

        playerBinding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}

            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                restartTimer()
                if (fromUser) {
                    viewModel.playerSeekTo(progress.toLong())
                }
            }
        })
    }

    private fun initButtons() {
        playerBinding.playButtonView.setOnClickListener {
            restartTimer()
            when (viewModel.buttonState.value) {
                PlayingState.PLAYING -> {
                    viewModel.buttonState.value = PlayingState.PAUSED
                    viewModel.pause()
                }
                else -> {
                    viewModel.buttonState.value = PlayingState.PLAYING
                    viewModel.play()
                }
            }
        }

        playerBinding.playbackRateButton.setOnClickListener {
            restartTimer()
            rateDialog.show()
        }

        playerBinding.qualityButton.setOnClickListener {
            restartTimer()
            viewModel.getPlayerQualities()
            qualityDialog.show()
        }

        binding.tvUrlSelectionButton.setOnClickListener {
            restartTimer()
            sourceDialog.show()
        }

        restartTimer()
    }

    private fun restartTimer() {
        timerHandler.removeCallbacks(timerRunnable)
        timerHandler.postDelayed(timerRunnable, HIDE_CONTROLS_DELAY)
    }

    private fun fitSurfaceToView(surfaceView: SurfaceView, width: Int, height: Int) {
        val parent = surfaceView.parent as View
        val oldWidth = parent.width
        val oldHeight = parent.height
        val newWidth: Int
        val newHeight: Int
        val ratio = height.toFloat() / width.toFloat()
        if (oldHeight.toFloat() > oldWidth.toFloat() * ratio) {
            newWidth = oldWidth
            newHeight = (oldWidth.toFloat() * ratio).toInt()
        } else {
            newWidth = (oldHeight.toFloat() / ratio).toInt()
            newHeight = oldHeight
        }
        val layoutParams = surfaceView.layoutParams
        layoutParams.width = newWidth
        layoutParams.height = newHeight
        surfaceView.layoutParams = layoutParams
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        /* Ignored */
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        Log.d(TAG, "Surface destroyed")
        viewModel.updateSurface(null)
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        Log.d(TAG, "Surface created")
        viewModel.updateSurface(holder.surface)
    }
}
