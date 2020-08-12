package com.sks.simplemvvmarchitecture.view.ui.modules.base

import android.content.Context

import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.sks.simplemvvmarchitecture.utils.CustomProgress


abstract class BaseFragment : Fragment()  {

    private var mActivity: BaseActivity? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BaseActivity) {
            val activity = context as BaseActivity?
            this.mActivity = activity
            //   activity!!.onFragmentAttached()
        }
    }

    private fun getBaseActivity(): BaseActivity? {
        return mActivity
    }

    fun showLoading() {
        getBaseActivity()?.let { CustomProgress.getInstance()?.showProgress(it) }
    }

    fun hideLoading() {
        CustomProgress.getInstance()?.hideProgress()
    }

    fun hideKeyboard() {
        val view = activity?.currentFocus
        if (view != null) {
            val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    /* show short toast message*/
    fun showToast(message: String) {
        Toast.makeText(mActivity, message, Toast.LENGTH_SHORT).show()
    }

    /* show long toast message*/
    fun showLongToast(message: String) {
        Toast.makeText(mActivity, message, Toast.LENGTH_LONG).show()
    }

    /*initialize all observers*/
    abstract fun initObservers()

}