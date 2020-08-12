package com.sks.simplemvvmarchitecture.view.ui.modules.base

import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.sks.simplemvvmarchitecture.utils.CustomProgress

/**
 * @author  Sumit Singh on 8/11/2020.
 */
abstract class BaseActivity : AppCompatActivity() {

    fun showLoading() {
        CustomProgress.getInstance()?.showProgress(this)
    }

    fun hideLoading() {
        CustomProgress.getInstance()?.hideProgress()
    }

    fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    /* show short toast message*/
    fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    /* show long toast message*/
    fun showLongToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

}