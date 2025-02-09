import com.towertex.nba.CoroutinesTestRule
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import com.towertex.nba.R
import com.towertex.nbamodel.model.TeamItem
import com.towertex.nbamodel.repositories.PlayerRepositoryContract
import com.towertex.nba.repository.ResourceRepositoryContract
import com.towertex.nba.repository.GlideRepositoryContract
import com.towertex.nba.viewmodel.TeamDetailViewModel
import kotlinx.coroutines.test.advanceUntilIdle

@OptIn(ExperimentalCoroutinesApi::class)
class TeamDetailViewModelTest {

    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    private lateinit var playerRepository: PlayerRepositoryContract
    private lateinit var resourceRepository: ResourceRepositoryContract
    private lateinit var glideRepository: GlideRepositoryContract

    @Before
    fun setUp() {
        playerRepository = mockk()
        resourceRepository = mockk()
        glideRepository = mockk()
    }

    @Test
    fun `init sets fullName, name, shortName, city, division, and glideUrl correctly`() = runTest {
        // Arrange
        val team = TeamItem(1, "Full Name", "Name", "Short", "City", "Conference", "Division")
        coEvery { playerRepository.getTeam(any()) } returns flowOf(team)
        coEvery { resourceRepository.getString(R.string.td_full_name, any()) } returns "Full Name"
        coEvery { resourceRepository.getString(R.string.td_name, any()) } returns "Name"
        coEvery { resourceRepository.getString(R.string.td_short_name, any()) } returns "Short"
        coEvery { resourceRepository.getString(R.string.td_city, any()) } returns "City"
        coEvery { resourceRepository.getString(R.string.td_division, any(), any()) } returns "Conference - Division"
        coEvery { glideRepository.getGlideUrl(any()) } returns "https://example.com/logo.png"
        coEvery { resourceRepository.getString(R.string.unknown) } returns "Unknown"

        // Act
        val viewModel = TeamDetailViewModel(1, playerRepository, resourceRepository, glideRepository)
        advanceUntilIdle()

        // Assert
        assertEquals("Full Name", viewModel.fullName.value)
        assertEquals("Name", viewModel.name.value)
        assertEquals("Short", viewModel.shortName.value)
        assertEquals("City", viewModel.city.value)
        assertEquals("Conference - Division", viewModel.division.value)
        assertEquals("https://example.com/logo.png", viewModel.glideUrl.value)
    }
}