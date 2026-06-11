package dev.elenivoreopoulou.dividendtracker.presentation.dashboard

import dev.elenivoreopoulou.dividendtracker.data.repository.PortfolioRepository
import dev.elenivoreopoulou.dividendtracker.domain.model.Holding
import org.junit.Assert.assertEquals
import org.junit.Test

class PortfolioDashboardViewModelTest {
    @Test
    fun uiState_mapsRepositoryHoldingsToDashboardSummary() {
        val viewModel = PortfolioDashboardViewModel(
            repository = TestPortfolioRepository(testHoldings)
        )

        val uiState = viewModel.uiState.value

        assertEquals("€110.0", uiState.portfolioValue)
        assertEquals("2 holdings", uiState.holdingsCountText)
        assertEquals("€24.0", uiState.annualDividends)
        assertEquals("€2.0", uiState.monthlyIncome)
        assertEquals("€5,000.0", uiState.monthlyGoal)
        assertEquals("€4,998.0", uiState.remainingMonthlyGoal)
        assertEquals(0.0004f, uiState.goalProgress, 0.000001f)
        assertEquals("21.82%", uiState.portfolioYield)
        assertEquals(2, uiState.upcomingPayouts.size)
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

