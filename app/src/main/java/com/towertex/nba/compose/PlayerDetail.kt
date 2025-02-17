package com.towertex.nba.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.towertex.nba.R
import com.towertex.nba.viewmodel.PlayerDetailViewModel
import org.koin.compose.koinInject
import org.koin.core.parameter.parametersOf

@Composable
fun PlayerDetail(
    playerId: Int,
    onBack: () -> Unit,
    onClick: (Int) -> Unit
) {
    val viewModel: PlayerDetailViewModel = koinInject { parametersOf(playerId) }
    val name: String = viewModel.name.collectAsStateWithLifecycle().value
    val position: String = viewModel.position.collectAsStateWithLifecycle().value
    val body: String = viewModel.body.collectAsStateWithLifecycle().value
    val number: String = viewModel.number.collectAsStateWithLifecycle().value
    val college: String = viewModel.college.collectAsStateWithLifecycle().value
    val draft: String = viewModel.draft.collectAsStateWithLifecycle().value
    val teamId: Int? = viewModel.teamId.collectAsStateWithLifecycle().value

    PlayerDetailView(
        name = name,
        position = position,
        body = body,
        number = number,
        college = college,
        draft = draft,
        onBack = onBack,
        onClick = { teamId?.also { onClick(it) } }
    )
}

@Composable
fun PlayerDetailView(
    name: String,
    position: String,
    body: String,
    number: String,
    college: String,
    draft: String,
    onBack: () -> Unit,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.card_padding)),
        verticalArrangement = Arrangement.Center
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(MaterialTheme.colorScheme.secondaryContainer),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen.card_padding))
            ) {
                Text(
                    text = name,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                )
                Text(
                    text = position,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                )
            }
        }
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.card_padding)))
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(MaterialTheme.colorScheme.tertiaryContainer),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen.card_padding))
            ) {
                Text(
                    text = body,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onTertiaryContainer,
                )
                Text(
                    text = number,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onTertiaryContainer,
                )
                Text(
                    text = college,
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onTertiaryContainer,
                )
                Text(
                    text = draft,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onTertiaryContainer,
                )
            }
        }
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.card_padding)))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Button(
                onClick = onBack,
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.inverseSurface),
            ) { Text(text = stringResource(R.string.pd_button_back)) }
            Button(
                onClick = onClick,
            ) { Text(text = stringResource(R.string.pd_button_next)) }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PlayerDetailViewPreview() {
    PlayerDetailView(
        name = "LeBron James",
        position = "Forward",
        body = "6'9\" 250 lbs",
        number = "23",
        college = "St. Vincent-St. Mary HS (OH)",
        draft = "2003 R1 P1",
        onBack = {},
        onClick = {}
    )
}