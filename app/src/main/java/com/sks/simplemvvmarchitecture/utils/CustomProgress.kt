package com.sks.simplemvvmarchitecture.utils

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.Window
import com.sks.simplemvvmarchitecture.R
import kotlinx.android.synthetic.main.progress_dialog.view.*


/**
 * @author  Sumit Singh on 8/11/2020.
 */
class CustomProgress private constructor() {

    private var mDialog: Dialog? = null

    companion object {
        private var mCustomProgress: CustomProgress? = null
        fun getInstance(): CustomProgress? {
            if (mCustomProgress == null) {
                mCustomProgress = CustomProgress()
            }
            return mCustomProgress
        }
    }

    fun showProgress(context: Context, message: CharSequence?) {
        val inflater = (context as Activity).layoutInflater
        val view = inflater.inflate(R.layout.progress_dialog, null)
        if (message != null) {
            view.pb_textView_message.visibility = View.VISIBLE
            view.pb_textView_message.text = message
        }
        mDialog = Dialog(context)
        mDialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        mDialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        mDialog?.setContentView(view)
        mDialog?.setCancelable(false)
        mDialog?.setCanceledOnTouchOutside(false)
        mDialog?.show()
    }

    fun showProgress(context: Context) {
        showProgress(context, null)
    }

    fun hideProgress() {
        if (mDialog != null && mDialog?.isShowing == true) {
            mDialog?.dismiss()
        }
    }

    fun dismissProgress() {
        if (mDialog != null && mDialog?.isShowing == true) {
            mDialog?.dismiss()
        }
    }
}