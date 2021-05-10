package com.gratus.adidasconfirmed.ui.view.fragment

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.gratus.adidasconfirmed.R
import com.gratus.adidasconfirmed.ui.view.activity.MainActivity
import com.gratus.adidasconfirmed.ui.view.adapter.ProductListAdapter
import junit.framework.TestCase
import org.hamcrest.Matcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ProductDetailsFragmentTest : TestCase() {
    @Rule
    @JvmField
    var activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun recyclerview_is_showing_on_item_click() {
        onView(withId(R.id.progress_bar))
            .check(matches(ViewMatchers.isDisplayed()))
        Thread.sleep(1000)
        onView(withId(R.id.product_list_rv))
            .check(matches(ViewMatchers.isDisplayed()))

        onView(withId(R.id.product_list_rv))
            .perform(
                RecyclerViewActions
                    .actionOnItemAtPosition<ProductListAdapter.ProductListViewHolder>(
                        0,
                        clickItemWithId(R.id.view_details_fab)
                    )
            )
        onView(withId(R.id.product_detail_layout))
            .check(matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun test_details_page() {
        onView(withId(R.id.progress_bar))
            .check(matches(ViewMatchers.isDisplayed()))
        Thread.sleep(1000)
        onView(withId(R.id.product_list_rv))
            .check(matches(ViewMatchers.isDisplayed()))

        onView(withId(R.id.product_list_rv))
            .perform(
                RecyclerViewActions
                    .actionOnItemAtPosition<ProductListAdapter.ProductListViewHolder>(
                        0,
                        clickItemWithId(R.id.view_details_fab)
                    )
            )
        onView(withId(R.id.product_detail_layout))
            .check(matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.description_text)).check(matches(ViewMatchers.withText("description")))
        onView(withId(R.id.review_rv)).check(matches(ViewMatchers.isDisplayed()))
    }

    private fun clickItemWithId(id: Int): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View>? {
                return null
            }

            override fun getDescription(): String {
                return "Click on a child view with specified id."
            }

            override fun perform(uiController: UiController, view: View) {
                val v = view.findViewById(id) as View
                v.performClick()
            }
        }
    }
}
