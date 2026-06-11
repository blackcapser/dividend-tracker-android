package dev.elenivoreopoulou.dividendtracker.presentation.goals

import androidx.lifecycle.ViewModel
import dev.elenivoreopoulou.dividendtracker.data.repository.FakePortfolioRepository
import dev.elenivoreopoulou.dividendtracker.data.repository.PortfolioRepository
import dev.elenivoreopoulou.dividendtracker.domain.model.Holding
import java.text.DecimalFormat
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class PassiveIncomeGoalUiState(
    val targetMonthlyIncome: String,
    val currentMonthlyIncome: String,
    val progressPercent: String,
    val remainingMonthlyIncome: String,
    val progress: Float,
    val estimatedTimeToGoal: String,
    val requiredGrowth: String,
    val accelerationSuggestion: String
) {
    companion object {
        fun from(
            holdings: List<Holding>,
            monthlyGoal: Double = DefaultMonthlyGoal
        ): PassiveIncomeGoalUiState {
            val annualDividends = holdings.sumOf { it.annualDividendIncome }
            val monthlyIncome = annualDividends / MonthsInYear
            val progress = (monthlyIncome / monthlyGoal).toFloat().coerceIn(0f, 1f)
            val remainingMonthlyIncome = (monthlyGoal - monthlyIncome).coerceAtLeast(0.0)

            return PassiveIncomeGoalUiState(
                targetMonthlyIncome = "€${currencyFormatter.format(monthlyGoal)}",
                currentMonthlyIncome = "€${currencyFormatter.format(monthlyIncome)}",
                progressPercent = "${percentFormatter.format(progress * 100)}%",
                remainingMonthlyIncome = "€${currencyFormatter.format(remainingMonthlyIncome)}",
                progress = progress,
                estimatedTimeToGoal = "4.2 Years",
                requiredGrowth = "12% / yr",
                accelerationSuggestion = "Invest €500/month more\nto achieve your goal 1.5 years sooner"
            )
        }
    }
}

class PassiveIncomeGoalViewModel(
    repository: PortfolioRepository = FakePortfolioRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(
        PassiveIncomeGoalUiState.from(repository.getHoldings())
    )
    val uiState: StateFlow<PassiveIncomeGoalUiState> = _uiState.asStateFlow()
}

private val currencyFormatter = DecimalFormat("#,##0.##")
private val percentFormatter = DecimalFormat("0.#")
private const val MonthsInYear = 12.0
private const val DefaultMonthlyGoal = 5_000.0

