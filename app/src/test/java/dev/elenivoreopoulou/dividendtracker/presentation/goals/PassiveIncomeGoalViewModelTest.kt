package dev.elenivoreopoulou.dividendtracker.presentation.goals

import dev.elenivoreopoulou.dividendtracker.data.repository.PortfolioRepository
import dev.elenivoreopoulou.dividendtracker.domain.model.Holding
import org.junit.Assert.assertEquals
import org.junit.Test

class PassiveIncomeGoalViewModelTest {
    @Test
    fun uiState_mapsRepositoryHoldingsToGoalProgress() {
        val viewModel = PassiveIncomeGoalViewModel(
            repository = TestPortfolioRepository(testHoldings)
        )

        val uiState = viewModel.uiState.value

        assertEquals("€5,000", uiState.targetMonthlyIncome)
        assertEquals("€2", uiState.currentMonthlyIncome)
        assertEquals("0%", uiState.progressPercent)
        assertEquals("€4,998", uiState.remainingMonthlyIncome)
        assertEquals(0.0004f, uiState.progress, 0.000001f)
        assertEquals("4.2 Years", uiState.estimatedTimeToGoal)
        assertEquals("12% / yr", uiState.requiredGrowth)
        assertEquals("Invest €500/month more\nto achieve your goal 1.5 years sooner", uiState.accelerationSuggestion)
    }

    private class TestPortfolioRepository(
        private val holdings: List<Holding>
    ) : PortfolioRepository {
        override fun getHoldings(): List<Holding> = holdings
    }
}

private val testHoldings = listOf(
    Holding(
        id = 1,
        companyName = "Beta Holdings",
        ticker = "BBB",
        shares = 5.0,
        averagePrice = 8.0,
        currentPrice = 10.0,
        annualDividendPerShare = 2.4
    ),
    Holding(
        id = 2,
        companyName = "Alpha Income",
        ticker = "AAA",
        shares = 10.0,
        averagePrice = 5.0,
        currentPrice = 6.0,
        annualDividendPerShare = 1.2
    )
)

