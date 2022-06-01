package com.devexperto.architectcoders.data

import arrow.core.Option
import com.devexperto.architectcoders.data.PermissionChecker.Permission.COARSE_LOCATION
import com.devexperto.architectcoders.data.datasource.LocationDataSource
import io.reactivex.rxjava3.core.Single
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

@RunWith(MockitoJUnitRunner::class)
class RegionRepositoryTest {

    @Test
    fun `Returns default region when coarse permission not granted`() {
        val regionRepository = buildRegionRepository(
            permissionChecker = mock { on { check(COARSE_LOCATION) } doReturn false }
        )

        val region = regionRepository.findLastRegion()

        region.test().assertResult(RegionRepository.DEFAULT_REGION)
    }

    @Test
    fun `Returns region from location data source when permission granted`() {
        val regionRepository = buildRegionRepository(
            locationDataSource = mock { onBlocking { findLastRegion() } doReturn Single.just(Option("ES")) },
            permissionChecker = mock { on { check(COARSE_LOCATION) } doReturn true }
        )

        val region = regionRepository.findLastRegion()

        region.test().assertResult("ES")
    }
}

private fun buildRegionRepository(
    locationDataSource: LocationDataSource = mock(),
    permissionChecker: PermissionChecker = mock()
) = RegionRepository(locationDataSource, permissionChecker)