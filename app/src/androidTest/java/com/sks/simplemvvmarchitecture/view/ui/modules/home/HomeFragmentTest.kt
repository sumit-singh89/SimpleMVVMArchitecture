package com.sks.simplemvvmarchitecture.view.ui.modules.home


import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.sks.simplemvvmarchitecture.R
import com.sks.simplemvvmarchitecture.utils.EspressoIdlingResourceRule
import com.sks.simplemvvmarchitecture.utils.RecyclerViewMatcher
import com.sks.simplemvvmarchitecture.view.ui.modules.main.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * @author  Sumit Singh on 8/12/2020.
 */

@RunWith(AndroidJUnit4::class)
class HomeFragmentTest {


    @get:Rule
    val activityTestRule = ActivityTestRule(MainActivity::class.java)

    @get: Rule
    val espressoIdlingResourceRule = EspressoIdlingResourceRule()

    @Test
    fun testRecyclerItemViewVisibility() {
        // check recycler visibility
        onView(withId(R.id.recyclerView))
            .check(matches(isDisplayed()))

        // test recycler view 0th position item visibility
        val mRecyclerViewMatcher = RecyclerViewMatcher.withRecyclerView(R.id.recyclerView)
        onView(
            mRecyclerViewMatcher.withViewAtPosition(
                0,
                R.id.container
            )
        ).check(matches(isDisplayed()))

    }

    @Test
    fun testRecyclerItemClick() {
        // check recycler visibility
        onView(withId(R.id.recyclerView))
            .check(matches(isDisplayed()))

        // test recycler view 0 position item click
        onView(withId(R.id.recyclerView))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    0,
                    click()
                )
            )
    }
}
