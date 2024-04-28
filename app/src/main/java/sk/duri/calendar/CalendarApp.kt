package sk.duri.calendar

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun CalendarApp() {

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarTopBar(
    modifier: Modifier = Modifier,
) {
    TopAppBar(
        { Text(text = "Calendar") },
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun CalendarTopBarPreview() {
    CalendarTopBar()
}