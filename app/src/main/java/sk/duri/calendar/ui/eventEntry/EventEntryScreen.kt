package sk.duri.calendar.ui.eventEntry

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import sk.duri.calendar.R
import sk.duri.calendar.data.TypUdalosti
import sk.duri.calendar.ui.AppViewModelProvider
import sk.duri.calendar.ui.eventEntry.EventEntryViewModel
import sk.duri.calendar.ui.navigation.NavigationDestination

object EventEntryDestination : NavigationDestination {
    override val route = "eventEntry"
}

@Composable
fun EventEntryScreen(
    navigateBack: () -> Unit,
    //viewModel: EventEntryViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val corutineScope = rememberCoroutineScope()

    EventEntryBody(
        //udalostUiState = viewModel.udalostUiState,
        onUdalostValueChange = {/**/},//viewModel::updateUiState,
        navigateBack = navigateBack,
        onSaveClick = {
            /*corutineScope.launch {
                viewModel.saveUdalost()
                navigateBack()
            }*/
        }
    )
}

@Composable
fun EventEntryBody(
    //udalostUiState: UdalostUiState,
    onUdalostValueChange: (UdalostDetails) -> Unit,
    navigateBack: () -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var nazov by remember { mutableStateOf("") }
    var poznamka by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }

    Column {
        OutlinedTextField(
            value = nazov,
            onValueChange = {nazov = it},
            label = { Text(text = stringResource(R.string.nameEvent)) },
            modifier = modifier.align(Alignment.CenterHorizontally),
            )
        Box(
            modifier = Modifier
                .wrapContentSize(Alignment.TopStart)
                .align(Alignment.CenterHorizontally)
                .padding(5.dp)
                .height(30.dp)
                .background(MaterialTheme.colorScheme.primary)

        ) {
            Text(
                text = TypUdalosti.Udalost.nazov,
                color = MaterialTheme.colorScheme.onPrimary,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .clickable(onClick = { expanded = true })
                    .fillMaxWidth()
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp)

            ) {
                TypUdalosti.entries.forEach {
                    DropdownMenuItem(
                        text = { Text(text = it.nazov) },
                        onClick = { /*TODO*/
                            expanded = false
                        }
                    )
                }
            }
        }
        DateInput(
            stringResource(R.string.from),
            modifier.align(Alignment.CenterHorizontally)
        )
        DateInput(
            stringResource(R.string.to),
            modifier.align(Alignment.CenterHorizontally)
        )

        Text(
            text = stringResource(R.string.notes),
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge,
            modifier = modifier
                .align(Alignment.CenterHorizontally)
                .padding(8.dp)

        )
        OutlinedTextField(
            value = poznamka,
            onValueChange = {poznamka = it},
            modifier = modifier
                .align(Alignment.CenterHorizontally),
        )
        Row(
            modifier = modifier.align(Alignment.CenterHorizontally)
        ) {
            Button(
                onClick = onSaveClick,
                modifier = modifier.padding(5.dp)
            ) {
                Text("Save")
            }
            Button(
                onClick = navigateBack,
                modifier = modifier.padding(5.dp)
            ) {
                Text("Cancel")
            }
        }
    }
}

@Composable
fun DateInput(
    nazov: String,
    modifier: Modifier = Modifier
) {
    var minuta by remember { mutableStateOf("") }
    var hodina by remember { mutableStateOf("") }
    var den by remember { mutableStateOf("") }
    var mesiac by remember { mutableStateOf("") }
    var rok by remember { mutableStateOf("") }

    Column(
        modifier = modifier
    ) {

        Text(
            text = nazov,
            fontSize = 30.sp,
            textAlign = TextAlign.Center,
        )
        Row {
            Text(
                text = stringResource(R.string.time),
                fontSize = 20.sp,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(end = 10.dp)
            )
            OutlinedTextField(
                value = hodina,
                onValueChange = { hodina = it },
                label = { Text(text = stringResource(R.string.hour)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                maxLines = 1,
                modifier = Modifier
                    .width(80.dp)
            )
            Text(
                text = ":",
                fontSize = 40.sp,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
            )
            OutlinedTextField(
                value = minuta,
                onValueChange = { minuta = it },
                label = { Text(text = stringResource(R.string.minute)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                maxLines = 1,
                modifier = modifier
                    .width(80.dp)
            )
        }
        Row {
            Text(
                text = stringResource(R.string.date),
                fontSize = 20.sp,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(end = 10.dp)
            )
            OutlinedTextField(
                value = den,
                onValueChange = { den = it },
                label = { Text(text = stringResource(R.string.day)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                maxLines = 1,
                modifier = modifier
                    .width(60.dp)
            )
            Text(
                text = ".",
                fontSize = 25.sp,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = modifier
                    .align(Alignment.Bottom)
            )
            OutlinedTextField(
                value = mesiac,
                onValueChange = { mesiac = it },
                label = { Text(text = stringResource(R.string.month)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                maxLines = 1,
                modifier = modifier
                    .width(80.dp)
            )
            Text(
                text = ".",
                fontSize = 25.sp,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = modifier
                    .align(Alignment.Bottom)
            )
            OutlinedTextField(
                value = rok,
                onValueChange = { rok = it },
                label = { Text(text = stringResource(R.string.year)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                maxLines = 1,
                modifier = modifier
                    .width(80.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EventEntryScreenPreview() {
    EventEntryScreen(navigateBack = {})
}