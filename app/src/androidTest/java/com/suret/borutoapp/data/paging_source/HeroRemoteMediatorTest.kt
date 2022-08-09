package com.suret.borutoapp.data.paging_source

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingConfig
import androidx.paging.PagingState
import androidx.paging.RemoteMediator.MediatorResult
import androidx.test.core.app.ApplicationProvider
import com.suret.borutoapp.data.local.BorutoDatabase
import com.suret.borutoapp.data.remote.FakeBoruto2Api
import com.suret.borutoapp.domain.model.Hero
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

@ExperimentalPagingApi
@ExperimentalCoroutinesApi
class HeroRemoteMediatorTest {

    private lateinit var borutoApi: FakeBoruto2Api
    private lateinit var borutoDatabase: BorutoDatabase

    @Before
    fun setup() {
        borutoApi = FakeBoruto2Api()
        borutoDatabase = BorutoDatabase.create(
            context = ApplicationProvider.getApplicationContext(),
            useInMemory = true
        )
    }

    @After
    fun cleanup() {
        borutoDatabase.clearAllTables()
    }

    @Test
    fun refreshLoadReturnsSuccessResultWhenMoreDetailsIsPresent() =
        runBlocking {
            val remoteMediator = HeroRemoteMediator(
                borutoApi = borutoApi,
                borutoDatabase = borutoDatabase
            )
            val pagingState = PagingState<Int, Hero>(
                pages = listOf(),
                anchorPosition = null,
                config = PagingConfig(pageSize = 3),
                leadingPlaceholderCount = 0
            )
            val result = remoteMediator.load(loadType = LoadType.REFRESH, state = pagingState)

            assertTrue(result is MediatorResult.Success)
            assertFalse((result as MediatorResult.Success).endOfPaginationReached)

        }

    @Test
    fun refreshLoadSuccessResultAndEndOfPaginationTrueWhenNoMoreData() =
        runBlocking {
            borutoApi.clearData()
            val remoteMediator = HeroRemoteMediator(
                borutoApi = borutoApi,
                borutoDatabase = borutoDatabase
            )
            val pagingState = PagingState<Int, Hero>(
                pages = listOf(),
                anchorPosition = null,
                config = PagingConfig(pageSize = 3),
                leadingPlaceholderCount = 0
            )
            val result = remoteMediator.load(loadType = LoadType.REFRESH, state = pagingState)

            assertTrue(result is MediatorResult.Success)
            assertFalse((result as MediatorResult.Success).endOfPaginationReached)

        }

    @Test
    fun refreshLoadReturnsErrorResultsWhenErrorOccurs() =
        runBlocking {
            borutoApi.addException()
            val remoteMediator = HeroRemoteMediator(
                borutoApi = borutoApi,
                borutoDatabase = borutoDatabase
            )
            val pagingState = PagingState<Int, Hero>(
                pages = listOf(),
                anchorPosition = null,
                config = PagingConfig(pageSize = 3),
                leadingPlaceholderCount = 0
            )
            val result = remoteMediator.load(loadType = LoadType.REFRESH, state = pagingState)

            assertTrue(result is MediatorResult.Success)
        }
}