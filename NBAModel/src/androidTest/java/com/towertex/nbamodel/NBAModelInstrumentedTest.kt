package com.towertex.nbamodel

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.towertex.nbaapi.NBAApiBuilder
import com.towertex.nbaapi.services.PlayersApiContract
import com.towertex.nbamodel.room.NBADatabase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.runBlocking

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class NBAModelInstrumentedTest {

    companion object {
        private const val EXPECTED_PAGE_SIZE = 30

        private lateinit var appContext: Context
        private lateinit var api: PlayersApiContract
        private lateinit var db: NBADatabase
        private lateinit var repository: NBARepository
    }

    @Test
    fun dummyTest() {
        // Context of the app under test.
        assertEquals("com.towertex.nbamodel.test", appContext.packageName)
    }

    @Before
    fun init(): Unit = runBlocking {
        appContext = InstrumentationRegistry.getInstrumentation().targetContext
        api = NBAApiBuilder {
            setPerPage(EXPECTED_PAGE_SIZE)
            enableLogging()
        }.build()
        db = NBADatabase.buildDatabase(appContext)
        db.nbaDao.deleteTeams()
        db.nbaDao.deletePlayers()
        repository = NBARepository(api, db)
    }

    @Test
    fun testPlayers(): Unit = runBlocking {
        try {
            //first we should get just one response from network
            var i = 0
            repository.getPlayers(1).onEach{
                i++
                assertEquals(EXPECTED_PAGE_SIZE, it.size)
                assertEquals(1, i)
                assertEquals(1, it.first().page)
            }.launchIn(this).join()

            //check whether the data are indeed in the database
            val dbItems = db.nbaDao.getPlayers(1)
            assertEquals(EXPECTED_PAGE_SIZE, dbItems.size)
            assertEquals(1, dbItems.first().page)

            //artificially delete one item from db
            db.nbaDao.deletePlayers(listOf(dbItems.first().id))

            //we should obtain first the incomplete list from database and second should be the updated list from network
            i = 0
            repository.getPlayers(1).onEach{
                i++
                when (i) {
                    1 -> assertEquals(EXPECTED_PAGE_SIZE-1, it.size)
                    2 -> assertEquals(EXPECTED_PAGE_SIZE, it.size)
                    else -> fail("there should be just two values")
                }
                assertEquals(1, it.first().page)
            }.launchIn(this)
        } catch (e: AssertionError) {
            throw e
        } catch (e: Throwable) {
            fail("should not fail $e")
        }
    }
}