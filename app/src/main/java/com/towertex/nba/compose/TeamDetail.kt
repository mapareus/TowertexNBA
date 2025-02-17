package com.towertex.nba.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
        Text(text = fullName, style = MaterialTheme.typography.titleLarge)
        Text(text = name, style = MaterialTheme.typography.titleMedium)
        Text(text = shortName, style = MaterialTheme.typography.titleSmall)
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.card_padding)))
        Text(text = city, style = MaterialTheme.typography.bodyLarge)
        Text(text = division, style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.card_padding)))
        Button(
            onClick = onBack,
            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.inverseSurface),
        ) { Text(text = "Back") }
    }
}

@Preview(showBackground = true)
@Composable
fun TeamDetailPreview() {
    TeamDetail(1, onBack = {})
}