package com.sks.simplemvvmarchitecture.utils

import android.content.Context
import android.widget.Toast
import com.sks.simplemvvmarchitecture.MyApplication
import com.sks.simplemvvmarchitecture.R
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class UtilsMethods {

    companion object {


        /** this fun is used to display toast message
         * @param context pass application context
         * @param message pass message*/
        fun showToast(context: Context, message: String) {
            if (context != null) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            }
        }

       /** fun is used to convert throwable into a text message and display message to user in form of toast
        * @param context
        * @param throwable
        * */
        fun showErrorMessage(context: Context?, throwable: Throwable) {
            if (context != null) {
                if (throwable is SocketTimeoutException) {
                    showToast(context, context.getString(R.string.error_timeout))
                } else if (throwable is UnknownHostException) {
                    showToast(context, context.getString(R.string.error_unknown_host))
                } else if (throwable is Exception) {
                    val message: String? = throwable.message
                    if (message != null) {
                        showToast(context, message)
                    } else {
                        showToast(context, context.getString(R.string.error_something_went_wrong))
                    }
                }

            }
        }

        /** fun is used to convert throwable into string message
         * @param throwable pass throwable
         * @return meaningful message from throwable */
        fun getErrorMessage(throwable: Throwable): String {
            return when (throwable) {
                is SocketTimeoutException -> {
                    MyApplication.getResources().getString(R.string.error_timeout)
                }
                is UnknownHostException -> {
                    MyApplication.getResources().getString(R.string.error_unknown_host)
                }
                is Exception -> {
                    throwable.message ?: MyApplication.getResources()
                        .getString(R.string.error_something_went_wrong)
                }
                else -> MyApplication.getResources()
                    .getString(R.string.error_something_went_wrong)
            }
        }

    }
}