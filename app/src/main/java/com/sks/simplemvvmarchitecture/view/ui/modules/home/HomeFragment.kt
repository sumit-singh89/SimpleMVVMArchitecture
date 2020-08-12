package com.sks.simplemvvmarchitecture.view.ui.modules.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.sks.simplemvvmarchitecture.R
import com.sks.simplemvvmarchitecture.model.Canada
import com.sks.simplemvvmarchitecture.utils.NetworkUtils
import com.sks.simplemvvmarchitecture.view.ui.modules.base.BaseFragment
import com.sks.simplemvvmarchitecture.view.ui.modules.main.adapter.RecyclerViewAdapter
import com.sks.simplemvvmarchitecture.viewmodel.home.HomeFragmentViewModel
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * @author  Sumit Singh on 8/12/2020.
 */
class HomeFragment : BaseFragment(), SwipeRefreshLayout.OnRefreshListener {
    companion object {
        private val TAG = HomeFragment::class.qualifiedName
    }

    private lateinit var mRootView: View
    private lateinit var viewModel: HomeFragmentViewModel
    private lateinit var swipeLayout: SwipeRefreshLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mRootView = inflater.inflate(R.layout.fragment_home, container, false)
        swipeLayout = mRootView.findViewById(R.id.swipeLayout)
        swipeLayout.setOnRefreshListener(this)
        initObservers()
        callGetCanadaDetailsAPI()
        return mRootView
    }


    override fun initObservers() {
        viewModel = ViewModelProvider(this).get(HomeFragmentViewModel::class.java)

        // observer to handle the success response of api
        viewModel.getCanadaDetails().observe(this@HomeFragment.activity!!, Observer {
            if (swipeLayout.isRefreshing) {
                swipeLayout.isRefreshing = false
            } else {
                hideLoading()
            }
            it ?: return@Observer
            updateUI(it)
        })

        // observer to handle the error response of api
        viewModel.getError().observe(this@HomeFragment.activity!!, Observer {
            if (swipeLayout.isRefreshing) {
                swipeLayout.isRefreshing = false
            } else {
                hideLoading()
            }
            showToast(it)
        })
    }

    // make a api call here
    private fun callGetCanadaDetailsAPI() {
        if (NetworkUtils.isNetworkConnected(activity!!)) {
            if (!swipeLayout.isRefreshing)
                showLoading()// do not show custom loader if swipe to refresh called
            viewModel.callCanadaDetailsApi()
        } else {
            showToast(getString(R.string.error_something_went_wrong))// if no internet found show toast
        }
    }

    // update your ui elements here
    private fun updateUI(response: Canada) = if (!response.rows.isNullOrEmpty()) {
        activity!!.title = response.title
        val linearLayoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = linearLayoutManager
        val mAdapter = RecyclerViewAdapter(activity!!, response.rows)
        recyclerView.adapter = mAdapter
    } else {
        showToast(getString(R.string.error_no_data))
    }

    // handle swipe to refresh
    override fun onRefresh() {
        swipeLayout.isRefreshing = true
        callGetCanadaDetailsAPI()
    }
}