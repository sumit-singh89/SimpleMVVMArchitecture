package com.sks.simplemvvmarchitecture.repository

import com.sks.simplemvvmarchitecture.repository.api.RestApi
import com.sks.simplemvvmarchitecture.repository.api.RetrofitService

/**
 * @author  Sumit Singh on 8/11/2020.
 */


object AppRepository {

    private  var restApi: RestApi = RetrofitService.createService(RestApi::class.java)

}

