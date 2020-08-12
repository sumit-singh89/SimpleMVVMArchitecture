package com.sks.simplemvvmarchitecture.utils

import android.content.res.Resources;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;


/**
 * @author  Sumit Singh on 8/12/2020.
 */

class RecyclerViewMatcher(private val recyclerViewId: Int) {

    fun atPosition(position: Int): Matcher<View> {
        return withViewAtPosition(position, -1)
    }

    fun withViewAtPosition(position: Int, targetViewId: Int): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            internal var resources: Resources? = null
            internal var childView: View? = null

            override fun describeTo(description: Description) {
                var idDescription = recyclerViewId.toString()
                if (this.resources != null) {
                    try {
                        idDescription = this.resources!!.getResourceName(recyclerViewId)
                    } catch (var4: Resources.NotFoundException) {
                        idDescription = String.format("%s (resource name not found)",
                                *arrayOf<Any>(Integer.valueOf(recyclerViewId)))
                    }

                }

                description.appendText("with id: $idDescription")
            }

            override fun matchesSafely(view: View): Boolean {

                this.resources = view.getResources()

                if (childView == null) {
                    val recyclerView = view.rootView.findViewById(recyclerViewId) as RecyclerView
                    if (recyclerView != null && recyclerView.id == recyclerViewId) {
                        childView = recyclerView.getChildAt(position)
                    } else {
                        return false
                    }
                }

                if (targetViewId == -1) {
                    return view === childView
                } else {
                    val targetView = childView!!.findViewById(targetViewId) as View
                    return view === targetView
                }

            }
        }
    }

    companion object {

        fun withRecyclerView(recyclerViewId: Int): RecyclerViewMatcher {

            return RecyclerViewMatcher(recyclerViewId)
        }
    }
}