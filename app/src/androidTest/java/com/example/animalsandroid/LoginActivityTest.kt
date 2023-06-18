package com.example.animalsandroid

import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
//import com.appsforlife.toasterview.Toaster.toast
//import com.appsforlife.toasterview.ToasterAction.Companion.onToast
//import com.appsforlife.toasterview.ToasterAction.Companion.verifyToastDisplayed

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import junit.framework.TestCase.assertEquals
import org.hamcrest.Description
import org.junit.Rule
import org.junit.Test

class LoginActivityTest {


    @get:Rule
    val activityRule = ActivityScenarioRule(LoginActivity::class.java)
    //var activity: LoginActivity? = null


    @Test
    fun testCorrectLogin() {

        val scenario = activityRule.scenario
        scenario.moveToState(Lifecycle.State.RESUMED)

        // Wprowadź dane logowania
        Espresso.onView(ViewMatchers.withId(R.id.login)).perform(ViewActions.typeText("login1"))
            .perform(ViewActions.closeSoftKeyboard())
        Espresso.onView(ViewMatchers.withId(R.id.password)).perform(ViewActions.typeText("password1"))
            .perform(ViewActions.closeSoftKeyboard())

        // Kliknij przycisk logowania
        Espresso.onView(ViewMatchers.withId(R.id.button3)).perform(ViewActions.click())

        // Poczekaj na zmianę wartości zmiennej logged w Activity
        scenario.onActivity { activity ->
            val logged = activity.logged
            assertEquals(1, logged)
        }

        // Zamknięcie scenariusza testowego
        scenario.close()
    }

    @Test
    fun testIncorrectLogin() {
        val scenario = activityRule.scenario
        scenario.moveToState(Lifecycle.State.RESUMED)

        // Wprowadź dane logowania
        Espresso.onView(ViewMatchers.withId(R.id.login)).perform(ViewActions.typeText("invalidLogin"))
            .perform(ViewActions.closeSoftKeyboard())
        Espresso.onView(ViewMatchers.withId(R.id.password)).perform(ViewActions.typeText("invalidPassword"))
            .perform(ViewActions.closeSoftKeyboard())

        // Kliknij przycisk logowania
        Espresso.onView(ViewMatchers.withId(R.id.button3)).perform(ViewActions.click())

        // Poczekaj na zmianę wartości zmiennej logged w Activity
        scenario.onActivity { activity ->
            val logged = activity.logged
            assertEquals(0, logged)
        }

        // Zamknięcie scenariusza testowego
        scenario.close()
    }
}