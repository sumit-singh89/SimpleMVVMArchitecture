package com.sks.simplemvvmarchitecture.viewmodel.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockito_kotlin.*
import com.sks.simplemvvmarchitecture.model.Canada
import com.sks.simplemvvmarchitecture.repository.AppRepository
import com.sks.simplemvvmarchitecture.utils.RxSchedulerRule
import io.reactivex.Observable
import junit.framework.Assert.assertNotNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner


/**
 * @author  Sumit Singh on 8/12/2020.
 */
@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class HomeFragmentViewModelTest {

    @Mock
    lateinit var repo:AppRepository
    @Rule
    @JvmField
    var testSchedulerRule = RxSchedulerRule()
    @Rule
    @JvmField
    val ruleForLivaData = InstantTaskExecutorRule()

    private lateinit var homeViewModel:HomeFragmentViewModel
    @Mock
    private lateinit var mockedObserver: Observer<Canada>


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        homeViewModel = HomeFragmentViewModel(repo)
        homeViewModel.getDetails().observeForever(mockedObserver)
    }


    @Test
    fun testFetchDetailsApiCall_shouldReturnSuccess() {
        // mock the response
        val response : Canada = mock()
        whenever(repo.fetchDetails())
            .thenReturn(Observable.just(response))
        // call fetch details api
        homeViewModel.fetchDetails()

        // verify mocked observer called one times
        verify(mockedObserver, times(1)).onChanged(any())

        // check data received is not null
       assertNotNull(homeViewModel.getResult.value)
        // check result is equal to our mocked response
       assertEquals(response,homeViewModel.getResult.value)
    }


    @Test
    fun testFetchDetailsApiCall_shouldReturnError() {
        val mockedResponse: Throwable = mock()
        Mockito.doReturn(Observable.just(mockedResponse))
            .`when`(repo)
            .fetchDetails()
        // call fetch details api
        homeViewModel.fetchDetails()
        // check data received is not null
        assertNotNull(homeViewModel.errorResult.value)
        // verify mocked observer is not called any times
        verify(mockedObserver, times(0)).onChanged(any())
    }
}