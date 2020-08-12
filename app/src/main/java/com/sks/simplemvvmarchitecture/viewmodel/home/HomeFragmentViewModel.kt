package com.sks.simplemvvmarchitecture.viewmodel.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sks.simplemvvmarchitecture.model.Canada
import com.sks.simplemvvmarchitecture.repository.AppRepository
import com.sks.simplemvvmarchitecture.utils.UtilsMethods
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * @author  Sumit Singh on 8/12/2020.
 */
class HomeFragmentViewModel:ViewModel() {
    private var getResult: MutableLiveData<Canada> = MutableLiveData()

    private var errorResult: MutableLiveData<String> = MutableLiveData()

    fun getError(): LiveData<String> = errorResult
    fun getCanadaDetails(): LiveData<Canada> = getResult

    fun callCanadaDetailsApi() {
        AppRepository.run {
            getCanadaDetails()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result ->
                    getResult.value = result
                },
                    { error ->
                        error.printStackTrace()
                        errorResult.postValue(UtilsMethods.getErrorMessage(error))
                    }
                )
        }
    }

}