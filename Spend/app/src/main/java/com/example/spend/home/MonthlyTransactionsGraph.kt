import android.annotation.SuppressLint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.spend.auth.models.Transaction
import com.example.spend.auth.models.TransactionType
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter

@SuppressLint("DefaultLocale")
@Composable
fun MonthlyTransactionsGraph(
    transactions: List<Transaction>,
    modifier: Modifier = Modifier
) {
    val formatter = DateTimeFormatter.ISO_DATE_TIME

    val yearMonth = remember(transactions) {
        transactions
            .mapNotNull { it.createdAt }
            .mapNotNull {
                try { LocalDate.parse(it, formatter) } catch (e: Exception) { null }
            }
            .maxOrNull()
            ?.let { YearMonth.of(it.year, it.month) }
            ?: YearMonth.now()
    }

    val daysInMonth = remember(yearMonth) {
        (1..yearMonth.lengthOfMonth()).map { day -> yearMonth.atDay(day) }
    }

    val dailyExpenses = remember(transactions) {
        transactions
            .filter { it.type == TransactionType.EXPENSE && it.createdAt != null }
            .mapNotNull {
                try {
                    LocalDate.parse(it.createdAt, formatter) to it.amount
                } catch (e: Exception) {
                    null
                }
            }
            .groupBy({ it.first }, { it.second })
            .mapValues { it.value.sum() }
    }

    val expensesByDay = remember(daysInMonth, dailyExpenses) {
        daysInMonth.associateWith { dailyExpenses[it] ?: 0.0 }
    }

    val maxAmount = expensesByDay.values.maxOrNull() ?: 0.0

    Column(modifier = modifier.padding(16.dp)) {
        Text(
            text = "Dépenses du mois",
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        if (maxAmount == 0.0) {
            Text("Aucune dépense ce mois-ci")
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val verticalSteps = 5
                    val paddingLeft = 40f
                    val paddingBottom = 20f

                    val graphWidth = size.width - paddingLeft
                    val graphHeight = size.height - paddingBottom

                    // Points
                    val pointDistance = graphWidth / (expensesByDay.size - 1)
                    val points = expensesByDay.values.mapIndexed { index, amount ->
                        Offset(
                            x = paddingLeft + index * pointDistance,
                            y = graphHeight - (amount / maxAmount).toFloat() * graphHeight
                        )
                    }

                    for (i in 0..verticalSteps) {
                        val value = maxAmount / verticalSteps * i
                        val y = graphHeight - (value / maxAmount).toFloat() * graphHeight
                        drawLine(
                            color = Color.LightGray,
                            start = Offset(paddingLeft, y),
                            end = Offset(size.width, y),
                            strokeWidth = 1f
                        )
                        drawContext.canvas.nativeCanvas.drawText(
                            String.format("%.0f", value),
                            0f,
                            y,
                            android.graphics.Paint().apply {
                                color = android.graphics.Color.BLACK
                                textSize = 24f
                            }
                        )
                    }

                    daysInMonth.forEachIndexed { index, date ->
                        val x = paddingLeft + index * pointDistance
                        drawContext.canvas.nativeCanvas.drawText(
                            date.dayOfMonth.toString(),
                            x - 10,
                            size.height,
                            android.graphics.Paint().apply {
                                color = android.graphics.Color.BLACK
                                textSize = 24f
                                textAlign = android.graphics.Paint.Align.CENTER
                            }
                        )
                    }

                    // Lignes entre points
                    for (i in 0 until points.size - 1) {
                        drawLine(
                            color = Color(0xFF3F51B5),
                            start = points[i],
                            end = points[i + 1],
                            strokeWidth = 4f,
                            cap = StrokeCap.Round
                        )
                    }

                    points.forEach { point ->
                        drawCircle(
                            color = Color(0xFF3F51B5),
                            radius = 6f,
                            center = point
                        )
                    }
                }
            }
        }
    }
}
