package com.amazon.ivs.broadcast.watchlive.activities.dialogs

import android.net.Uri
import android.util.Log
import android.view.inputmethod.EditorInfo
import androidx.lifecycle.Observer
import com.amazon.ivs.broadcast.watchlive.activities.WatchMainActivity
import com.amazon.ivs.broadcast.watchlive.activities.adapters.SourceOptionAdapter
import com.amazon.ivs.broadcast.watchlive.common.*
import com.amazon.ivs.broadcast.watchlive.data.entity.SourceDataItem
import com.amazon.ivs.broadcast.watchlive.viewModel.MainViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior

class SourceDialog(
    private val activity: WatchMainActivity,
    private val viewModel: MainViewModel
) : SourceOptionAdapter.PlayerOptionCallback {

   // private val sourceMenu by lazy { BottomSheetBehavior.from(activity.source_sheet) }
    private val optionsAdapter by lazy { SourceOptionAdapter(this) }

    init {
        //sourceMenu.isFitToContents = false
        initViews()
    }

    private fun initViews() {

       //
        initAdapter()

        //sourceMenu.addBottomSheetCallback(activity.sheetListener)
    }

    fun show() {
       // sourceMenu.open()
    }

    fun dismiss() {
       // sourceMenu.hide()
        activity.hideKeyboard()
    }

   // fun isOpened() = sourceMenu.isOpened()

    fun release() {
     //   sourceMenu.removeBottomSheetCallback(activity.sheetListener)
    }

    private fun initAdapter() {
        // Default option list
      //

        viewModel.sources.observe(activity, Observer {
            optionsAdapter.items = it
        })
    }

    override fun onOptionClicked(url: String) {
        Log.d(Configuration.TAG, "Url selected $url")
        viewModel.playerLoadStream(Uri.parse(url))
        viewModel.play()
        dismiss()
    }

    override fun onOptionDelete(url: String) {
        Log.d(Configuration.TAG, "Url deleted $url")
        viewModel.deleteSource(url)
    }
}
