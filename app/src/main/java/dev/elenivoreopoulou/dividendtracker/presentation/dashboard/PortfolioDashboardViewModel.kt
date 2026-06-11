package dev.elenivoreopoulou.dividendtracker.presentation.dashboard

import androidx.lifecycle.ViewModel
import dev.elenivoreopoulou.dividendtracker.data.repository.FakePortfolioRepository
import dev.elenivoreopoulou.dividendtracker.data.repository.PortfolioRepository
import dev.elenivoreopoulou.dividendtracker.domain.model.Holding
import java.text.DecimalFormat
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class PortfolioDashboardUiState(
    val portfolioValue: String,
    val holdingsCountText: String,
    val annualDividends: String,
    val monthlyIncome: String,
    val monthlyGoal: String,
    val remainingMonthlyGoal: String,
    val goalProgress: Float,
    val portfolioYield: String,
    val upcomingPayouts: List<UpcomingPayoutUiState>
) {
    companion object {
        fun from(
            holdings: List<Holding>,
            monthlyGoal: Double = DefaultMonthlyGoal,
            upcomingPayouts: List<UpcomingPayoutUiState> = DefaultUpcomingPayouts
        ): PortfolioDashboardUiState {
            val portfolioValue = holdings.sumOf { it.currentValue }
            val annualDividends = holdings.sumOf { it.annualDividendIncome }
            val monthlyIncome = annualDividends / MonthsInYear
            val remainingMonthlyGoal = (monthlyGoal - monthlyIncome).coerceAtLeast(0.0)
            val goalProgress = (monthlyIncome / monthlyGoal).toFloat().coerceIn(0f, 1f)
            val portfolioYield = if (portfolioValue > 0) {
                annualDividends / portfolioValue * 100
            } else {
                0.0
            }

            return PortfolioDashboardUiState(
                portfolioValue = "€${currencyFormatter.format(portfolioValue)}",
                holdingsCountText = "${holdings.size} holdings",
                annualDividends = "€${currencyFormatter.format(annualDividends)}",
                monthlyIncome = "€${currencyFormatter.format(monthlyIncome)}",
                monthlyGoal = "€${currencyFormatter.format(monthlyGoal)}",
                remainingMonthlyGoal = "€${currencyFormatter.format(remainingMonthlyGoal)}",
                goalProgress = goalProgress,
                portfolioYield = "${percentFormatter.format(portfolioYield)}%",
                upcomingPayouts = upcomingPayouts
            )
        }
    }
}

data class UpcomingPayoutUiState(
    val initials: String,
    val title: String,
    val subtitle: String,
    val amount: String
)

class PortfolioDashboardViewModel(
    repository: PortfolioRepository = FakePortfolioRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(
        PortfolioDashboardUiState.from(repository.getHoldings())
    )
    val uiState: StateFlow<PortfolioDashboardUiState> = _uiState.asStateFlow()
}

private val currencyFormatter = DecimalFormat("#,##0.0#")
private val percentFormatter = DecimalFormat("0.##")
private const val MonthsInYear = 12.0
private const val DefaultMonthlyGoal = 5_000.0

private val DefaultUpcomingPayouts = listOf(
    UpcomingPayoutUiState(
        initials = "BE",
        title = "BELA.AT",
        subtitle = "Mar 15, 2026",
        amount = "+€120"
    ),
    UpcomingPayoutUiState(
        initials = "OP",
        title = "OPAP.AT",
        subtitle = "May 04, 2026",
        amount = "+€450"
    )
)

