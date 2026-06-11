package dev.elenivoreopoulou.dividendtracker.presentation.portfolio

import dev.elenivoreopoulou.dividendtracker.data.repository.PortfolioRepository
import dev.elenivoreopoulou.dividendtracker.domain.model.Holding
import org.junit.Assert.assertEquals
import org.junit.Test

class PortfolioViewModelTest {
    @Test
    fun uiState_sortsHoldingsAndMapsPortfolioSummary() {
        val viewModel = PortfolioViewModel(
            repository = TestPortfolioRepository(testHoldings)
        )

        val uiState = viewModel.uiState.value

        assertEquals(listOf("AAA", "BBB"), uiState.holdings.map { it.ticker })
        assertEquals(listOf("AAA", "BBB"), uiState.filteredHoldings.map { it.ticker })
        assertEquals("€90.0", uiState.summary.investedAmount)
        assertEquals("€110.0", uiState.summary.currentValue)
        assertEquals("€24.0", uiState.summary.annualDividendIncome)
        assertEquals("26.67%", uiState.summary.yieldOnCost)
    }

    @Test
    fun onSearchQueryChange_filtersByTickerOrCompanyNameIgnoringCase() {
        val viewModel = PortfolioViewModel(
            repository = TestPortfolioRepository(testHoldings)
        )

        viewModel.onSearchQueryChange("alpha")

        val uiState = viewModel.uiState.value
        assertEquals("alpha", uiState.searchQuery)
        assertEquals(listOf("AAA"), uiState.filteredHoldings.map { it.ticker })
        assertEquals(listOf("AAA", "BBB"), uiState.holdings.map { it.ticker })
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

