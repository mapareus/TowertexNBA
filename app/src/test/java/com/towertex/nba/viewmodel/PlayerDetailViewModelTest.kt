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
import com.towertex.nbamodel.model.PlayerItem
import com.towertex.nbamodel.model.TeamItem
import com.towertex.nbamodel.repositories.PlayerRepositoryContract
import com.towertex.nba.repository.ResourceRepositoryContract
import com.towertex.nba.viewmodel.PlayerDetailViewModel
import kotlinx.coroutines.test.advanceUntilIdle

@OptIn(ExperimentalCoroutinesApi::class)
class PlayerDetailViewModelTest {

    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    private lateinit var playerRepository: PlayerRepositoryContract
    private lateinit var resourceRepository: ResourceRepositoryContract

    @Before
    fun setUp() {
        playerRepository = mockk()
        resourceRepository = mockk()
    }

    @Test
    fun `init sets name, position, body, number, college, draft, and teamId correctly`() = runTest {
        // Arrange
        val player = PlayerItem(1, 1, "John", "Doe", "Guard", "1", "23", "J1", "CO", "US", 1990, 2, 3, 4)
        val team = TeamItem(4, "Co", "Di", "Ci","Na", "Fu", "Ab")
        coEvery { playerRepository.getPlayer(any()) } returns flowOf(player)
        coEvery { playerRepository.getTeam(any()) } returns flowOf(team)
        coEvery { resourceRepository.getString(R.string.pd_name, any(), any()) } returns "John Doe"
        coEvery { resourceRepository.getString(R.string.pd_position, any(), any()) } returns "Guard - Celtics"
        coEvery { resourceRepository.getString(R.string.pd_body, any(), any()) } returns "6'7\" - 220 lbs"
        coEvery { resourceRepository.getString(R.string.pd_number, any()) } returns "23"
        coEvery { resourceRepository.getString(R.string.pd_college, any(), any()) } returns "Duke - USA"
        coEvery { resourceRepository.getString(R.string.pd_draft, any(), any(), any()) } returns "2010 - Round 1 - Pick 2"
        coEvery { resourceRepository.getString(R.string.unknown) } returns "Unknown"

        // Act
        val viewModel = PlayerDetailViewModel(1, playerRepository, resourceRepository)
        advanceUntilIdle()

        // Assert
        assertEquals("John Doe", viewModel.name.value)
        assertEquals("Guard - Celtics", viewModel.position.value)
        assertEquals("6'7\" - 220 lbs", viewModel.body.value)
        assertEquals("23", viewModel.number.value)
        assertEquals("Duke - USA", viewModel.college.value)
        assertEquals("2010 - Round 1 - Pick 2", viewModel.draft.value)
        assertEquals(4, viewModel.teamId.value)
    }
}