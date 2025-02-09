package com.towertex.nba.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.towertex.nba.R
import com.towertex.nba.viewmodel.TeamDetailViewModel
import org.koin.compose.koinInject
import org.koin.core.parameter.parametersOf

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun TeamDetail(
    teamId: Int,
    onBack: () -> Unit
) {
    val viewModel: TeamDetailViewModel = koinInject { parametersOf(teamId) }
    val fullName: String = viewModel.fullName.collectAsStateWithLifecycle().value
    val name: String = viewModel.name.collectAsStateWithLifecycle().value
    val shortName: String = viewModel.shortName.collectAsStateWithLifecycle().value
    val city: String = viewModel.city.collectAsStateWithLifecycle().value
    val division: String = viewModel.division.collectAsStateWithLifecycle().value
    val glideUrl: String = viewModel.glideUrl.collectAsStateWithLifecycle().value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.card_padding)),
        verticalArrangement = Arrangement.Center
    ) {
        GlideImage(
            model = glideUrl,
            contentDescription = shortName,
            contentScale = ContentScale.Fit,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.card_padding)))
        Text(text = fullName, style = MaterialTheme.typography.h4)
        Text(text = name, style = MaterialTheme.typography.h5)
        Text(text = shortName, style = MaterialTheme.typography.h6)
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.card_padding)))
        Text(text = city, style = MaterialTheme.typography.body1)
        Text(text = division, style = MaterialTheme.typography.body1)
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.card_padding)))
        Button(onClick = onBack) { Text(text = "Back") }
    }
}

@Preview(showBackground = true)
@Composable
fun TeamDetailPreview() {
    TeamDetail(1, onBack = {})
}