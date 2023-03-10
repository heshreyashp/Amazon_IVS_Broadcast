package com.amazon.ivs.broadcast.watchlive.activities.dialogs

import androidx.lifecycle.Observer
import com.amazon.ivs.broadcast.watchlive.activities.WatchMainActivity
import com.amazon.ivs.broadcast.watchlive.activities.adapters.PlayerOptionAdapter
import com.amazon.ivs.broadcast.watchlive.common.Configuration
import com.amazon.ivs.broadcast.watchlive.common.hide
import com.amazon.ivs.broadcast.watchlive.common.isOpened
import com.amazon.ivs.broadcast.watchlive.common.open
import com.amazon.ivs.broadcast.watchlive.viewModel.MainViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior

class QualityDialog(
    private val activity: WatchMainActivity,
    private val viewModel: MainViewModel
) : PlayerOptionAdapter.PlayerOptionCallback {

    //private val qualityMenu by lazy { BottomSheetBehavior.from(activity.quality_sheet) }
    private val qualityAdapter by lazy { PlayerOptionAdapter(this) }

    init {
        initViews()
    }

    private fun initViews() {

        viewModel.selectedQualityValue.observe(activity, Observer {
            viewModel.getPlayerQualities()
        })
//
    }

    fun show() {
       // qualityMenu.open()
    }

    fun dismiss() {
       // qualityMenu.hide()
    }

    //fun isOpened() = qualityMenu.isOpened()

    fun release() {
     //   qualityMenu.removeBottomSheetCallback(activity.sheetListener)
    }

    override fun onOptionClicked(position: Int) {
        val option = qualityAdapter.items[position].option
        if (option == Configuration.AUTO) {
            viewModel.selectAuto()
        } else {
            viewModel.selectQuality(option)
        }
        dismiss()
    }
}
