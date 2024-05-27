package sk.duri.calendar.ui.CreateEvent

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import sk.duri.calendar.ui.navigation.NavigationDestination
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextField
import androidx.compose.material3.TimeInput
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import sk.duri.calendar.R
import sk.duri.calendar.data.TypUdalosti
import sk.duri.calendar.ui.AppViewModelProvider
import java.util.Calendar

object CreateEventDestination : NavigationDestination {
    override val route = "createEvent"
    const val udalostIdArg = "udalostId"
    val routeWithArgs = "$route/{$udalostIdArg}"
}

@Composable
fun CreateEventScreen(
    viewModel: CreateEventViewModel = viewModel(factory = AppViewModelProvider.Factory),
    navigateBack: () -> Unit,
) {
   /* EventEntryScreen(
        navigateBack = navigateBack
    )*/
}





@Preview(showBackground = true)
@Composable
fun PreviewCreateEventScreen() {
    CreateEventScreen(navigateBack = { /*Do nothing*/ })
}