package com.devexperto.architectcoders.ui

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.rule.GrantPermissionRule
import com.devexperto.architectcoders.R
import com.devexperto.architectcoders.TestApp
import com.devexperto.architectcoders.data.server.MockWebServerRule
import com.devexperto.architectcoders.fromJson
import kotlinx.coroutines.ExperimentalCoroutinesApi
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@ExperimentalCoroutinesApi
class MainInstrumentationTest {

    @get:Rule(order = 1)
    val mockWebServerRule = MockWebServerRule()

    @get:Rule(order = 2)
    val locationPermissionRule: GrantPermissionRule = GrantPermissionRule.grant(
        "android.permission.ACCESS_COARSE_LOCATION"
    )

    @get:Rule(order = 3)
    val activityRule = ActivityScenarioRule(NavHostActivity::class.java)

    @Inject
    lateinit var okHttpClient: OkHttpClient

    @Before
    fun setUp() {
        val app = ApplicationProvider.getApplicationContext<TestApp>()
        app.component.inject(this)

        mockWebServerRule.server.enqueue(
            MockResponse().fromJson("popular_movies.json")
        )

        val resource = OkHttp3IdlingResource.create("OkHttp", okHttpClient)
        IdlingRegistry.getInstance().register(resource)
    }

    @Test
    fun click_a_movie_navigates_to_detail() {
        onView(withId(R.id.recycler))
            .perform(
                actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    4, click()
                )
            )

        onView(withId(R.id.movie_detail_toolbar))
            .check(matches(hasDescendant(withText("Turning Red"))))

    }
}