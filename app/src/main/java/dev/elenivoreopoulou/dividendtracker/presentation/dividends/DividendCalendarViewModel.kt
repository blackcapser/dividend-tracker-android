package dev.elenivoreopoulou.dividendtracker.presentation.dividends

import androidx.lifecycle.ViewModel
import java.text.DecimalFormat
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class DividendCalendarUiState(
    val selectedYear: String,
    val expectedIncome: String,
    val months: List<CalendarMonthUiState>
) {
    companion object {
        fun from(
            selectedYear: Int = DefaultSelectedYear,
            months: List<CalendarMonthUiState> = DefaultCalendarMonths
        ): DividendCalendarUiState {
            val annualExpectedIncome = months.sumOf { month ->
                month.payouts.sumOf { payout -> payout.amount }
            }

            return DividendCalendarUiState(
                selectedYear = selectedYear.toString(),
                expectedIncome = "€${currencyFormatter.format(annualExpectedIncome)}",
                months = months
            )
        }
    }
}

data class CalendarMonthUiState(
    val name: String,
    val payouts: List<CalendarPayoutUiState> = emptyList()
) {
    val hasPayouts: Boolean = payouts.isNotEmpty()
    val totalPayoutAmount: String = "€${currencyFormatter.format(payouts.sumOf { it.amount })}"
}

data class CalendarPayoutUiState(
    val ticker: String,
    val amount: Int
) {
    val formattedAmount: String = "€${currencyFormatter.format(amount)}"
}

class DividendCalendarViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(DividendCalendarUiState.from())
    val uiState: StateFlow<DividendCalendarUiState> = _uiState.asStateFlow()
}

private val currencyFormatter = DecimalFormat("#,##0")
private const val DefaultSelectedYear = 2026

private val DefaultCalendarMonths = listOf(
    CalendarMonthUiState(name = "Jan"),
    CalendarMonthUiState(name = "Feb"),
    CalendarMonthUiState(name = "Mar", payouts = listOf(CalendarPayoutUiState("BELA.AT", 120))),
    CalendarMonthUiState(name = "Apr"),
    CalendarMonthUiState(
        name = "May",
        payouts = listOf(
            CalendarPayoutUiState("OPAP.AT", 450),
            CalendarPayoutUiState("EEE.AT", 6256)
        )
    ),
    CalendarMonthUiState(name = "Jun", payouts = listOf(CalendarPayoutUiState("ALPHA.AT", 45))),
    CalendarMonthUiState(name = "Jul"),
    CalendarMonthUiState(name = "Aug"),
    CalendarMonthUiState(name = "Sep", payouts = listOf(CalendarPayoutUiState("OPAP.AT", 225))),
    CalendarMonthUiState(name = "Oct"),
    CalendarMonthUiState(name = "Nov"),
    CalendarMonthUiState(name = "Dec")
)

