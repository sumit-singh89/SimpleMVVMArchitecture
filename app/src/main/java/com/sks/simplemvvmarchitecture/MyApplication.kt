package com.sks.simplemvvmarchitecture

import android.app.Application
import android.content.res.Resources

/**
 * @author  Sumit Singh on 4/25/2020.
 */
class MyApplication : Application() {


    override fun onCreate() {
        super.onCreate()
        mContext = this

    }

    companion object {
        lateinit var mContext: MyApplication
        fun getResources(): Resources {
            return mContext.resources
        }
    }

}