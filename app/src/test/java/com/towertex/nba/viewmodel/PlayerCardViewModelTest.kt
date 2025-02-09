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
import com.towertex.nbamodel.repositories.PlayerRepositoryContract
import com.towertex.nba.repository.ResourceRepositoryContract
import com.towertex.nba.viewmodel.PlayerCardViewModel
import com.towertex.nbamodel.model.PlayerItem
import com.towertex.nbamodel.model.TeamItem
import kotlinx.coroutines.test.advanceUntilIdle

@OptIn(ExperimentalCoroutinesApi::class)
class PlayerCardViewModelTest {

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
    fun `init sets name, position, and index correctly`() = runTest {
        // Arrange
        val player = PlayerItem(1, 1, "John", "Doe", "Guard", "1", "23", "J1", "CO", "US", 1990, 2, 3, 4)
        val team = TeamItem(4, "Co", "Di", "Ci","Na", "Fu", "Ab")
        coEvery { playerRepository.getPlayer(any()) } returns flowOf(player)
        coEvery { playerRepository.getTeam(any()) } returns flowOf(team)
        coEvery { resourceRepository.getString(R.string.pc_name, any(), any()) } returns "John Doe"
        coEvery { resourceRepository.getString(R.string.pc_position, any(), any()) } returns "Guard - Celtics"
        coEvery { resourceRepository.getString(R.string.pc_index, any(), any()) } returns "1 - 23"
        coEvery { resourceRepository.getString(R.string.unknown) } returns "Unknown"

        // Act
        val viewModel = PlayerCardViewModel(1, playerRepository, resourceRepository)
        advanceUntilIdle()

        // Assert
        assertEquals("John Doe", viewModel.name.value)
        assertEquals("Guard - Celtics", viewModel.position.value)
        assertEquals("1 - 23", viewModel.index.value)
    }
}