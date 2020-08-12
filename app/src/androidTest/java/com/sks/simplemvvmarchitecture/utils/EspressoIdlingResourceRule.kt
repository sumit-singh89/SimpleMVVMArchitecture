package com.sks.simplemvvmarchitecture.utils

import androidx.test.espresso.IdlingRegistry
import utils.EspressoIdlingResource
import org.junit.rules.TestWatcher
import org.junit.runner.Description


// This class is used in UI testing purpose of using EspressoIdlingResourceRule is to wait unit the network operation is performed

/**
 * @author  Sumit Singh on 8/12/2020.
 */

class EspressoIdlingResourceRule : TestWatcher() {

    private val idlingResource = EspressoIdlingResource.countingIdlingResource

    override fun finished(description: Description?) {
        IdlingRegistry.getInstance().unregister(idlingResource)
        super.finished(description)
    }

    override fun starting(description: Description?) {
        IdlingRegistry.getInstance().register(idlingResource)
        super.starting(description)
    }
}



