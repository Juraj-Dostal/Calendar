package sk.duri.calendar.ui.eventEntry

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import sk.duri.calendar.ui.AppViewModelProvider
import sk.duri.calendar.ui.navigation.NavigationDestination

object EventEditDestination : NavigationDestination {
    override val route = "eventEntry"
    const val eventIdArg = "eventId"
    val routeWithArgs = "$route/{$eventIdArg}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventEditScreen(
    navigateBack: () -> Unit,
    navigateToNameDayEdit: () -> Unit,
    viewModel: EventEditViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val corutineScope = rememberCoroutineScope()

    Scaffold (
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text("Calendar")
                },
                navigationIcon = {
                    IconButton(onClick = navigateToNameDayEdit ) {
                        Icon(Icons.Filled.Settings, contentDescription = "Settings")
                    }
                },
                actions = {
                    IconButton(onClick = navigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Change screen to back")
                    }
                }
            )
        },
    ){
            innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            EventEntryBody(
                udalostDetails = viewModel.udalostUiState.udalostDetails,
                onUdalostValueChange = viewModel::updateUiState,
                navigateBack = navigateBack,
                onSaveClick = {
                    corutineScope.launch {
                        viewModel.updateEvent()
                        navigateBack()
                    }
                }
            )
        }
    }
}
