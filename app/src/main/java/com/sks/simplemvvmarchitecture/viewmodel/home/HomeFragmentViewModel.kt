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
class HomeFragmentViewModel(private val appRepository: AppRepository):ViewModel() {
     var getResult: MutableLiveData<Canada> = MutableLiveData()

     var errorResult: MutableLiveData<String> = MutableLiveData()

    fun getError(): LiveData<String> = errorResult
    fun getDetails(): LiveData<Canada> = getResult

    fun fetchDetails() {
        appRepository.run {
            fetchDetails()
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