package sk.duri.calendar.ui.widget

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.SizeMode
import androidx.glance.appwidget.cornerRadius
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.padding
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import sk.duri.calendar.R


/**
 *  Inspirovane z internetu
 *  link: https://medium.com/@binayshaw7777/creating-a-simple-calendar-widget-using-jetpack-compose-glance-db2ad133459e
 */

class DateWidget : GlanceAppWidget() {

    override val sizeMode: SizeMode
        get() = SizeMode.Exact

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
                CalendarUI(
                calendar = java.util.Calendar.getInstance(),
                context = context
            )
        }
    }

}

class DateWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget
        get() = DateWidget()

}
@Composable
fun CalendarUI(
    calendar: java.util.Calendar,
    context: Context
) {
    val day = calendar.get(java.util.Calendar.DAY_OF_MONTH)
    val dayOfWeek = calendar.get(java.util.Calendar.DAY_OF_WEEK)
    val dayOfWeekString = getDayName(dayOfWeek, context)
    val month = calendar.get(java.util.Calendar.MONTH) + 1
    val monthString = getMonthName(month - 1, context)
    val year = calendar.get(java.util.Calendar.YEAR)

    Box(
        modifier = GlanceModifier
            .cornerRadius(30.dp)
            .fillMaxSize()
            .background( Color(0XFFFFDDB5) )
            .padding(16.dp)
            .clickable {
                Toast.makeText(
                    context,
                    "Today is ${day}.${month}.${year}",
                    Toast.LENGTH_SHORT
                ).show()
            },
        contentAlignment = Alignment.Center
    ) {
        Spacer()
        Row(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = day.toString(),
                style = TextStyle(
                    color = ColorProvider(Color(0xFF2A1800)),
                    fontSize = 44.sp,
                    fontWeight = FontWeight.Bold
                ),
            )

            Column(
                modifier = GlanceModifier.padding(horizontal = 5.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = dayOfWeekString,
                    style = TextStyle(
                        color = ColorProvider(Color(0xFF2A1800)),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
                Text(
                    text = "$monthString, $year",
                    style = TextStyle(
                        color = ColorProvider(Color(0xFF2A1800)),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
        Spacer()
    }
}

fun getMonthName(month: Int, context: Context): String {
    return when (month) {
        0 -> context.getString(R.string.january).substring(0, 3)
        1 -> context.getString(R.string.february).substring(0, 3)
        2 -> context.getString(R.string.march).substring(0, 3)
        3 -> context.getString(R.string.april).substring(0, 3)
        4 -> context.getString(R.string.may).substring(0, 3)
        5 -> context.getString(R.string.june).substring(0, 3)
        6 -> context.getString(R.string.july).substring(0, 3)
        7 -> context.getString(R.string.august).substring(0, 3)
        8 -> context.getString(R.string.september).substring(0, 3)
        9 -> context.getString(R.string.october).substring(0, 3)
        10 -> context.getString(R.string.november).substring(0, 3)
        11 -> context.getString(R.string.december).substring(0, 3)
        else -> ""
    }
}

fun getDayName(day: Int, context: Context): String {
    return when (day) {
        1 -> context.getString(R.string.monday)
        2 -> context.getString(R.string.tuesday)
        3 -> context.getString(R.string.wednesday)
        4 -> context.getString(R.string.thursday)
        5 -> context.getString(R.string.friday)
        6 -> context.getString(R.string.saturday)
        7 -> context.getString(R.string.sunday)
        else -> ""
    }
}

