package com.devexperto.architectcoders.ui

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.rule.GrantPermissionRule
import com.devexperto.architectcoders.data.database.MovieDao
import com.devexperto.architectcoders.data.datasource.MovieRemoteDataSource
import com.devexperto.architectcoders.data.server.MockWebServerRule
import com.devexperto.architectcoders.fromJson
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltAndroidTest
class MainInstrumentationTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val mockWebServerRule = MockWebServerRule()

    @get:Rule(order = 2)
    val locationPermissionRule: GrantPermissionRule = GrantPermissionRule.grant(
        "android.permission.ACCESS_COARSE_LOCATION"
    )

    @get:Rule(order = 3)
    val activityRule = ActivityScenarioRule(NavHostActivity::class.java)

    @Inject
    lateinit var movieDao: MovieDao

    @Inject
    lateinit var remoteDataSource: MovieRemoteDataSource

    @Before
    fun setUp() {
        mockWebServerRule.server.enqueue(
            MockResponse().fromJson("popular_movies.json")
        )

        hiltRule.inject()
    }

    @Test
    fun check_4_items_db() = runTest {
        movieDao.insertMovies(buildDatabaseMovies(1, 2, 3, 4))
        assertEquals(4, movieDao.movieCount())
    }

    @Test
    fun check_6_items_db() = runTest {
        movieDao.insertMovies(buildDatabaseMovies(1, 2, 3, 4, 5, 6))
        assertEquals(6, movieDao.movieCount())
    }

    @Test
    fun check_mock_server_is_working() = runTest {
        val movies = remoteDataSource.findPopularMovies("EN")
        movies.fold({ throw Exception(it.toString()) }) {
            assertEquals("The Batman", it[0].title)
        }
    }
}