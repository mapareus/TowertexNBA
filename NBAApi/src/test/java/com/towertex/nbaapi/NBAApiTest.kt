package com.towertex.nbaapi

import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

class NBAApiTest {

    companion object {
        private const val BASE_URL = "https://api.balldontlie.io/"
        private const val NBA_API_KEY = "d9a551fe-f341-4e8a-84e7-2f436d87a2fb"
        private const val EXPECTED_PAGE_SIZE = 35

        private lateinit var api: NBAApi
        private lateinit var wrongApi: NBAApi
    }

    @Test
    fun dummyTest() {
        assertEquals(4, 2 + 2)
    }

    @Before
    fun init() {
        api = NBAApiBuilder {
            setEndpoint(BASE_URL)
            setToken(NBA_API_KEY)
            setPerPage(EXPECTED_PAGE_SIZE)
            enableLogging()
        }.build()
        wrongApi = NBAApiBuilder {
            setToken("wrong_token")
            enableLogging()
        }.build()
    }

    @Test
    fun `test getAllPlayers returns first page with correct size and cursor`(): Unit = runBlocking {
        try {
            val response = api.getAllPlayers(1).execute()
            assertTrue("response should success", response.isSuccessful)
            assertEquals(EXPECTED_PAGE_SIZE, response.body()?.data?.size ?: 0)
            assertEquals(EXPECTED_PAGE_SIZE, response.body()?.meta?.next_cursor)
        } catch (e: AssertionError) {
            throw e
        } catch (e: Throwable) {
            fail("should not fail 1 $e")
        }
    }

    @Test
    fun `test getAllPlayers returns second page with correct size and cursor`(): Unit = runBlocking {
        try {
            val response = api.getAllPlayers(2).execute()
            assertTrue("response should success", response.isSuccessful)
            assertEquals(EXPECTED_PAGE_SIZE, response.body()?.data?.size ?: 0)
            assertEquals(EXPECTED_PAGE_SIZE*2, response.body()?.meta?.next_cursor)
        } catch (e: AssertionError) {
            throw e
        } catch (e: Throwable) {
            fail("should not fail 2 $e")
        }
    }

    @Test
    fun `test getAllPlayers fails with wrong api key`(): Unit = runBlocking {
        try {
            val result = wrongApi.getAllPlayers(0).execute()
            fail("should have failed but got $result")
        } catch (e: AssertionError) {
            throw e
        } catch (e: Throwable) {
            assertTrue("should be NBAApiException", e is NBAApiException)
            assertEquals(
                NBAApiErrorType.UNAUTHORIZED,
                NBAApiErrorType.fromHttpCode((e as NBAApiException).httpCode)
            )
        }
    }
}