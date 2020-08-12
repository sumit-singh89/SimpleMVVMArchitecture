package com.sks.simplemvvmarchitecture.view.ui.modules.main

import android.os.Bundle
import com.sks.simplemvvmarchitecture.R
import com.sks.simplemvvmarchitecture.view.ui.modules.base.BaseActivity


/**
 * @author  Sumit Singh on 8/11/2020.
 */
class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}