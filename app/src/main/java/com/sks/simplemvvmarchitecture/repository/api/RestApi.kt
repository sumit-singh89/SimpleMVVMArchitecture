package com.sks.simplemvvmarchitecture.repository.api

import com.sks.simplemvvmarchitecture.model.Canada
import retrofit2.http.GET
import io.reactivex.Observable

/**
 * @author  Sumit Singh on 8/11/2020.
 */

interface RestApi {

     @GET("s/2iodh4vg0eortkl/facts.json")
     fun getCanadaDetails():Observable<Canada>

}
