package com.sks.simplemvvmarchitecture.utils

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.text.Html
import android.text.Spanned
import android.widget.Toast
import com.sks.simplemvvmarchitecture.MyApplication
import com.sks.simplemvvmarchitecture.R
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeUnit

class UtilsMethods {

    companion object {

        fun showToast(context: Context, message: String){
            if(context!=null){
                Toast.makeText(context,message,Toast.LENGTH_SHORT).show()
            }
        }
        fun showErrorMessage(context: Context?, throwable: Throwable) {
            if (context != null ) {
                if (throwable is SocketTimeoutException) {
                    showToast(context,context.getString(R.string.error_timeout))
                } else if (throwable is UnknownHostException) {
                    showToast(context,context.getString(R.string.error_unknown_host))
                } else if (throwable is Exception) {
                    val message: String? = throwable.message
                    if (message != null) {
                        showToast(context,message)
                    } else {
                        showToast(context,context.getString(R.string.error_something_went_wrong))
                    }
                }

            }
        }

        fun getErrorMessage(throwable: Throwable):String{
                if (throwable is SocketTimeoutException) {
                    return MyApplication.getResources().getString(R.string.error_timeout)
                } else if (throwable is UnknownHostException) {
                    return MyApplication.getResources().getString(R.string.error_unknown_host)
                } else if (throwable is Exception) {
                    return throwable.message ?: MyApplication.getResources().getString(R.string.error_something_went_wrong)
                }
            return MyApplication.getResources().getString(R.string.error_something_went_wrong)
        }


/*
        fun replaceFragment(getApplication: FragmentActivity, fragment: Fragment) {
            val backStateName = fragment::class.java.simpleName
            val fragmentTag = backStateName
            val manager = getApplication.supportFragmentManager
            val fragmentPopped = manager.popBackStackImmediate(backStateName, 0)

            if (!fragmentPopped && manager.findFragmentByTag(fragmentTag) == null) { //fragment not in back stack, create it.
                val ft = manager.beginTransaction()
                ft.add(R.id.frame_container, fragment, fragmentTag)
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                ft.addToBackStack(backStateName)
                ft.commit()
            } else if (!fragmentPopped && manager.findFragmentByTag(fragmentTag) != null) {
                val ft = manager.beginTransaction()
                ft.detach(manager.findFragmentByTag(fragmentTag))
                ft.attach(manager.findFragmentByTag(fragmentTag))
                ft.commit()
            }

        }
*/


        fun fromHtml(source: String): Spanned {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Html.fromHtml(source, Html.FROM_HTML_MODE_LEGACY)
            } else {
                Html.fromHtml(source)
            }
        }


        fun convertMillisToHHMmSs(millis: Long): String? {
           return String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
                TimeUnit.MILLISECONDS.toMinutes(millis) % TimeUnit.HOURS.toMinutes(1),
                TimeUnit.MILLISECONDS.toSeconds(millis) % TimeUnit.MINUTES.toSeconds(1));
        }


    }
}