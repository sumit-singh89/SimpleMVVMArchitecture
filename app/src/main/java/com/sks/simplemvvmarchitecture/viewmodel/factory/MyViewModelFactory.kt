package com.sks.simplemvvmarchitecture.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sks.simplemvvmarchitecture.repository.AppRepository
import com.sks.simplemvvmarchitecture.viewmodel.home.HomeFragmentViewModel

/**
 * @author  Sumit Singh on 8/12/2020.
 */
class MyViewModelFactory(
    private val appRepository: AppRepository
): ViewModelProvider.NewInstanceFactory() {
    override fun <T: ViewModel> create(modelClass:Class<T>): T {
        return HomeFragmentViewModel(appRepository) as T
    }
}