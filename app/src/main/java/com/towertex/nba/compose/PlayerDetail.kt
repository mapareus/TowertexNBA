package com.towertex.nba.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.card_padding)),
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = name, style = MaterialTheme.typography.h4)
        Text(text = position, style = MaterialTheme.typography.h5)
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.card_padding)))
        Text(text = body, style = MaterialTheme.typography.body1)
        Text(text = number, style = MaterialTheme.typography.body1)
        Text(text = college, style = MaterialTheme.typography.body1)
        Text(text = draft, style = MaterialTheme.typography.body1)
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.card_padding)))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Button(onClick = onBack) { Text(text = "Back") }
            Button(onClick = { teamId?.also { onClick(it) } } ) { Text(text = "View Team") }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PlayerDetailPreview() {
    PlayerDetail(1, onBack = {}, onClick = {})
}