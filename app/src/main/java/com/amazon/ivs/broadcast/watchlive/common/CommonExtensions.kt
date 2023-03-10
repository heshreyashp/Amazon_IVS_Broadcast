package com.amazon.ivs.broadcast.watchlive.common

import android.app.Activity
import android.app.Dialog
import android.view.View
import android.view.Window
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.amazon.ivs.broadcast.R
import com.google.android.material.bottomsheet.BottomSheetBehavior

fun BottomSheetBehavior<View>.isOpened() =
    state == BottomSheetBehavior.STATE_EXPANDED || state == BottomSheetBehavior.STATE_HALF_EXPANDED

fun BottomSheetBehavior<View>.open() {
    if (!isOpened()) {
        state = BottomSheetBehavior.STATE_EXPANDED
    }
}

fun BottomSheetBehavior<View>.hide() {
    if (isOpened()) {
        state = BottomSheetBehavior.STATE_HIDDEN
    }
}

fun Activity.hideKeyboard() {
    val view = currentFocus ?: window.decorView
    val token = view.windowToken
    view.clearFocus()
    ContextCompat.getSystemService(this, InputMethodManager::class.java)
        ?.hideSoftInputFromWindow(token, 0)
}

fun Activity.showDialog(title: String, message: String) {
    val dialog = Dialog(this)
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialog.setCancelable(false)
    dialog.setContentView(R.layout.view_dialog)
    val title = dialog.findViewById<View>(R.id.title) as TextView
    val tvmessage = dialog.findViewById<View>(R.id.message) as TextView
    val dismiss_btn = dialog.findViewById<View>(R.id.dismiss_btn) as TextView
    title.text = getString(R.string.error_happened_template, title)
    tvmessage.text = message
    dismiss_btn.setOnClickListener {
        dialog.dismiss()
    }
    dialog.show()
}
