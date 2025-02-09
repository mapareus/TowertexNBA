package com.towertex.nba.repository

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GlideRepositoryTest {

    private lateinit var resourceRepository: ResourceRepositoryContract
    private lateinit var glideRepository: GlideRepositoryContract

    @Before
    fun setUp() {
        resourceRepository = mockk()
        glideRepository = GlideRepository(resourceRepository)
    }

    @Test
    fun `getGlideUrl returns correct URL for known team`() {
        // Arrange
        val teamShortName = "Celtics"
        val expectedUrl = "https://example.com/bos"
        every { resourceRepository.getString(any(), "bos") } returns expectedUrl

        // Act
        val result = glideRepository.getGlideUrl(teamShortName)

        // Assert
        assertEquals(expectedUrl, result)
        verify { resourceRepository.getString(any(), "bos") }
    }

    @Test
    fun `getGlideUrl returns empty string for unknown team`() {
        // Arrange
        val teamShortName = "Unknown"
        every { resourceRepository.getString(any(), "") } returns ""

        // Act
        val result = glideRepository.getGlideUrl(teamShortName)

        // Assert
        assertEquals("", result)
        verify { resourceRepository.getString(any(), "") }
    }
}