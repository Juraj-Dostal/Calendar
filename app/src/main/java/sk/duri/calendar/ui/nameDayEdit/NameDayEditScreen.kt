package sk.duri.calendar.ui.nameDayEdit

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import sk.duri.calendar.R
import sk.duri.calendar.ui.AppViewModelProvider
import sk.duri.calendar.ui.navigation.NavigationDestination

object NameDayEditDestination : NavigationDestination {
    override val route = "nameDayEdit"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NameDayEditScreen(
    navigateToDayCalendar: () -> Unit,
    navigateToMonthCalendar: () -> Unit,
    navigateToEventEntry: () -> Unit,
    viewModel: NameDayEditViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val nameDayList = viewModel.nameDayListUiState.collectAsState()

    Scaffold (
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(stringResource(R.string.app_name))
                },
                actions = {
                    IconButton(
                        onClick = {
                            viewModel.save()
                            navigateToDayCalendar()
                        }
                    ) {
                        Icon(Icons.AutoMirrored.Filled.List, contentDescription = "Change screen to DayCalendar")
                    }
                    IconButton(
                        onClick = {
                            viewModel.save()
                            navigateToMonthCalendar()
                    }
                    ) {
                        Icon(Icons.Filled.DateRange , contentDescription = "Change screen to DayCalendar")
                    }
                }
            )
        },
        floatingActionButton =  {
            FloatingActionButton(
                onClick = viewModel::checkAll,
            ) { Icon(Icons.Filled.CheckCircle, contentDescription = "Check") }
        }
    ){  innerPadding ->
        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
        ) {
            items(
                items = nameDayList.value.nameDayList,
                key = { nameDayState -> "${nameDayState.month}-${nameDayState.day}" }
            ){
                CheckNameDay(
                    nameDay = it,
                    onValueChange = viewModel::changeIsChecked
                )
            }
        }
    }
}

@Composable
fun CheckNameDay(
    nameDay: NameDayCheckBox,
    onValueChange: (NameDayCheckBox) -> Unit,
    modifier: Modifier = Modifier
){

    Row(
        Modifier
            .fillMaxWidth()
            .height(56.dp)
            .toggleable(
                value = nameDay.isChecked.value,
                onValueChange = { onValueChange(nameDay) },
                role = Role.Checkbox
            )
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically

    ) {
        Checkbox(
            checked = nameDay.isChecked.value,
            onCheckedChange = null
        )
        Text(
            text = nameDay.name,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(start = 16.dp)

        )
    }

}

@Preview(showBackground = true)
@Composable
fun NameDayEditScreenPreview() {
    NameDayEditScreen(
        navigateToDayCalendar = { /**/ },
        navigateToEventEntry = { /**/ },
        navigateToMonthCalendar = { /**/ }
    )
}