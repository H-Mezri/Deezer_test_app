package com.mezri.deezertestapp.ui.main

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.mezri.deezertestapp.R
import com.mezri.deezertestapp.ui.MainActivity
import com.mezri.deezertestapp.ui.utils.RecyclerViewItemAssertion
import com.mezri.deezertestapp.utils.idling.EspressoIdlingResource
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainFragmentTest {

    @get:Rule
    private val activityRule = ActivityTestRule(MainActivity::class.java, false, false)

    @Before
    fun setup() {
        activityRule.launchActivity(null)
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    @Test
    fun testLoadAlbumsButtons_OK_visibility_and_enabled() {
        // check the button is displayed
        onView(withId(R.id.sectionTitle)).check(matches(isDisplayed()))
        // perform click on recycler view item
        onView(withId(R.id.recyclerAlbumsList)).perform(
            RecyclerViewActions.actionOnItemAtPosition<AlbumRecyclerAdapter.AlbumItemViewHolder>(
                0,
                click()
            )
        )
        // after performing a click if buttons from second fragment are displayed on the view
        onView(withId(R.id.btnPlayAlbum)).check(matches(isDisplayed()))
    }

    @Test
    fun testRecyclerView_OK_show_albums_list() {
        // first album to receive in the list
        val firstAlbumTitle = "Groove Sessions"

        // Check if album title exist in the recycler view after data is displayed
        onView(withId(R.id.recyclerAlbumsList)).check(
            RecyclerViewItemAssertion(
                firstAlbumTitle,
                true
            )
        )
    }

    @After
    fun unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }
}