package com.towertex.nba.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.towertex.nba.R
import com.towertex.nba.viewmodel.PlayerCardViewModel
import org.koin.compose.koinInject
import org.koin.core.parameter.parametersOf

@Composable
fun PlayerCard(
    playerId: Int,
    onClick: (Int) -> Unit,
) {
    val viewModel: PlayerCardViewModel = koinInject { parametersOf(playerId) }
    val playerName: String = viewModel.name.collectAsStateWithLifecycle().value
    val playerPosition: String = viewModel.position.collectAsStateWithLifecycle().value
    val playerIndex: String = viewModel.index.collectAsStateWithLifecycle().value

    PlayerCardView(
        onClick = { onClick(playerId) },
        playerName = playerName,
        playerPosition = playerPosition,
        playerIndex = playerIndex,
    )
}

@Composable
fun PlayerCardView(
    onClick: () -> Unit,
    playerName: String,
    playerPosition: String,
    playerIndex: String,
) {
    Card(
        elevation = CardDefaults.cardElevation(dimensionResource(R.dimen.card_elevation)),
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.card_margin))
            .clickable { onClick() },
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primaryContainer),
    ) {
        Column(modifier = Modifier.padding(dimensionResource(R.dimen.card_padding))) {
            Text(
                text = playerName,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
            )
            Text(
                text = playerPosition,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
            )
            Text(
                text = playerIndex,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PlayerCardViewPreview() {
    PlayerCardView(
        onClick = {},
        playerName = "Player Name",
        playerPosition = "Position",
        playerIndex = "Index",
    )
}