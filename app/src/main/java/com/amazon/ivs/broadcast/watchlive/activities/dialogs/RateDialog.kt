package com.amazon.ivs.broadcast.watchlive.activities.dialogs

import androidx.lifecycle.Observer
import com.amazon.ivs.broadcast.watchlive.activities.WatchMainActivity
import com.amazon.ivs.broadcast.watchlive.activities.adapters.PlayerOptionAdapter
import com.amazon.ivs.broadcast.watchlive.common.hide
import com.amazon.ivs.broadcast.watchlive.common.isOpened
import com.amazon.ivs.broadcast.watchlive.common.open
import com.amazon.ivs.broadcast.watchlive.viewModel.MainViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior

class RateDialog(
    private val activity: WatchMainActivity,
    private val viewModel: MainViewModel
) : PlayerOptionAdapter.PlayerOptionCallback {

   // private val rateMenu by lazy { BottomSheetBehavior.from(activity.rate_sheet) }
    private val rateAdapter by lazy { PlayerOptionAdapter(this) }

    init {
        initViews()
    }

    private fun initViews() {

        viewModel.selectedRateValue.observe(activity, Observer {
            rateAdapter.items = viewModel.getPlayBackRates().toMutableList()
        })

        //
    }

    fun show() {
        //rateMenu.open()
    }

    fun dismiss() {
        //rateMenu.hide()
    }

   // fun isOpened() = rateMenu.isOpened()

    fun release() {
    //    rateMenu.removeBottomSheetCallback(activity.sheetListener)
    }

    override fun onOptionClicked(position: Int) {
        viewModel.setPlaybackRate(rateAdapter.items[position].option)
        dismiss()
    }
}
