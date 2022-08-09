package com.suret.borutoapp.data.paging_source

import androidx.paging.PagingSource.LoadParams
import androidx.paging.PagingSource.LoadResult
import com.suret.borutoapp.data.remote.BorutoApi
import com.suret.borutoapp.data.remote.FakeBorutoApi
import com.suret.borutoapp.domain.model.Hero
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@ExperimentalCoroutinesApi
class SearchHeroesSourceTest {

    private lateinit var borutoApi: BorutoApi
    private lateinit var heroes: List<Hero>

    @Before
    fun setup() {
        borutoApi = FakeBorutoApi()
        heroes = listOf(
            Hero(
                id = 1,
                name = "Sasuke",
                image = "",
                about = "",
                rating = 5.0,
                power = 98,
                month = "July",
                day = "23rd",
                family = listOf(
                    "Fugaku",
                    "Mikoto",
                    "Itachi",
                    "Sarada",
                    "Sakura"
                ),
                abilities = listOf(
                    "Sharingan",
                    "Rinnegan",
                    "Sussano",
                    "Amateratsu",
                    "Intelligence"
                ),
                natureTypes = listOf(
                    "Lightning",
                    "Fire",
                    "Wind",
                    "Earth",
                    "Water"
                )
            ),
            Hero(
                id = 2,
                name = "Naruto",
                image = "",
                about = "",
                rating = 5.0,
                power = 98,
                month = "Oct",
                day = "10th",
                family = listOf(
                    "Minato",
                    "Kushina",
                    "Boruto",
                    "Himawari",
                    "Hinata"
                ),
                abilities = listOf(
                    "Rasengan",
                    "Rasen-Shuriken",
                    "Shadow Clone",
                    "Senin Mode"
                ),
                natureTypes = listOf(
                    "Wind",
                    "Earth",
                    "Lava",
                    "Fire"
                )
            ),
            Hero(
                id = 3,
                name = "Sakura",
                image = "",
                about = "",
                rating = 4.5,
                power = 92,
                month = "Mar",
                day = "28th",
                family = listOf(
                    "Kizashi",
                    "Mebuki",
                    "Sarada",
                    "Sasuke"
                ),
                abilities = listOf(
                    "Chakra Control",
                    "Medical Ninjutsu",
                    "Strength",
                    "Intelligence"
                ),
                natureTypes = listOf(
                    "Earth",
                    "Water",
                    "Fire"
                )
            )
        )
    }

    @Test
    fun `Search api with existing hero name, expect single hero result, assert LoadResult_Page`() =
        runTest {
            val heroSource = SearchHeroesSource(borutoApi = borutoApi, query = "Sasuke")
            assertEquals<LoadResult<Int, Hero>>(
                expected = LoadResult.Page(
                    data = listOf(heroes.first()),
                    prevKey = null,
                    nextKey = null
                ),
                actual = heroSource.load(
                    LoadParams.Refresh(
                        key = null,
                        loadSize = 3,
                        placeholdersEnabled = false
                    )
                )
            )
        }

    @Test
    fun `Search api with existing hero name, expect multiple hero result, assert LoadResult_Page`() =
        runTest {
            val heroSource = SearchHeroesSource(borutoApi = borutoApi, query = "Sa")
            assertEquals<LoadResult<Int, Hero>>(
                expected = LoadResult.Page(
                    data = listOf(heroes.first(), heroes[2]),
                    prevKey = null,
                    nextKey = null
                ),
                actual = heroSource.load(
                    LoadParams.Refresh(
                        key = null,
                        loadSize = 3,
                        placeholdersEnabled = false
                    )
                )
            )
        }

    @Test
    fun `Search api with empty hero name, assert empty heroes list and LoadResult_Page`() =
        runTest {
            val heroSource = SearchHeroesSource(borutoApi = borutoApi, query = "")
            val loadResult = heroSource.load(
                LoadParams.Refresh(
                    key = null,
                    loadSize = 3,
                    placeholdersEnabled = false
                )
            )

            val result = borutoApi.searchHeroes("").heroes

            assertTrue { result.isEmpty() }
            assertTrue { loadResult is LoadResult.Page }
        }

    @Test
    fun `Search api with non_existing hero name, assert empty heroes list and LoadResult_Page`() =
        runTest {
            val heroSource = SearchHeroesSource(borutoApi = borutoApi, query = "Unknown")
            val loadResult = heroSource.load(
                LoadParams.Refresh(
                    key = null,
                    loadSize = 3,
                    placeholdersEnabled = false
                )
            )

            val result = borutoApi.searchHeroes("Unknown").heroes

            assertTrue { result.isEmpty() }
            assertTrue { loadResult is LoadResult.Page }
        }
}