package com.sks.simplemvvmarchitecture.extension

import android.view.View

/**
 * @author  Sumit Singh on 8/11/2020.
 */

fun View.visible(isVisible: Boolean) {
    if (isVisible) {
        this.visibility = View.VISIBLE
    } else {
        this.visibility = View.GONE
    }
}
