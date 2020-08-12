package com.sks.simplemvvmarchitecture.repository

import com.sks.simplemvvmarchitecture.model.Canada
import com.sks.simplemvvmarchitecture.repository.api.RestApi
import com.sks.simplemvvmarchitecture.repository.api.RetrofitService
import io.reactivex.Observable

/**
 * @author  Sumit Singh on 8/11/2020.
 */


object AppRepository {

    private  var restApi: RestApi = RetrofitService.createService(RestApi::class.java)

    fun getCanadaDetails(): Observable<Canada> {
        return restApi.getCanadaDetails()
    }
}

