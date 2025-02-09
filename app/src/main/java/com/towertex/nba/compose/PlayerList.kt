package com.towertex.nba.compose

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.towertex.nba.R
import com.towertex.nba.viewmodel.PlayerListViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun PlayerList(
    onClick: (Int) -> Unit
) {
    val viewModel: PlayerListViewModel = koinViewModel()
    val pagingPlayers = viewModel.players.collectAsLazyPagingItems()

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(all = dimensionResource(R.dimen.card_margin))
    ) {
        items(
            count = pagingPlayers.itemCount,
            key = pagingPlayers.itemKey { it.id }
        ) { index ->
            val playerItem = pagingPlayers[index] ?: return@items
            PlayerCard(
                playerId = playerItem.id,
                onClick = onClick
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PlayerListPreview() {
    PlayerList(
        onClick = {}
    )
}