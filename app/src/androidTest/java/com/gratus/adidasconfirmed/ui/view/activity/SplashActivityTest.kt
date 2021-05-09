package com.gratus.adidasconfirmed.ui.view.activity

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.gratus.adidasconfirmed.R
import junit.framework.TestCase
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SplashActivityTest : TestCase() {
    @get:Rule
    var activityRule = ActivityScenarioRule(SplashActivity::class.java)

    @Test
    fun testSplashActivityToMainActivity() {
        onView(withId(R.id.parent_splash))
            .check(matches(ViewMatchers.isDisplayed()))
        Thread.sleep(1000)
        onView(withId(R.id.parent_main))
            .check(matches(ViewMatchers.isDisplayed()))
    }
}
