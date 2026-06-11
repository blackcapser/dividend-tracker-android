package dev.elenivoreopoulou.dividendtracker.presentation.portfolio

import androidx.lifecycle.ViewModel
import dev.elenivoreopoulou.dividendtracker.data.repository.FakePortfolioRepository
import dev.elenivoreopoulou.dividendtracker.data.repository.PortfolioRepository
import dev.elenivoreopoulou.dividendtracker.domain.model.Holding
import java.text.DecimalFormat
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class PortfolioScreenUiState(
    val searchQuery: String = "",
    val holdings: List<Holding> = emptyList(),
    val filteredHoldings: List<Holding> = emptyList(),
    val summary: PortfolioSummaryUiState = PortfolioSummaryUiState()
) {
    companion object {
        fun from(
            holdings: List<Holding>,
            searchQuery: String = ""
        ): PortfolioScreenUiState {
            val sortedHoldings = holdings.sortedBy { it.ticker }
            val filteredHoldings = sortedHoldings.filter { holding ->
                holding.ticker.contains(searchQuery, ignoreCase = true) ||
                    holding.companyName.contains(searchQuery, ignoreCase = true)
            }

            return PortfolioScreenUiState(
                searchQuery = searchQuery,
                holdings = sortedHoldings,
                filteredHoldings = filteredHoldings,
                summary = PortfolioSummaryUiState.from(sortedHoldings)
            )
        }
    }
}

data class PortfolioSummaryUiState(
    val investedAmount: String = "€0",
    val currentValue: String = "€0",
    val annualDividendIncome: String = "€0",
    val yieldOnCost: String = "0%"
) {
    companion object {
        fun from(holdings: List<Holding>): PortfolioSummaryUiState {
            val investedAmount = holdings.sumOf { it.investedAmount }
            val currentValue = holdings.sumOf { it.currentValue }
            val annualDividendIncome = holdings.sumOf { it.annualDividendIncome }
            val yieldOnCost = if (investedAmount > 0) {
                annualDividendIncome / investedAmount * 100
            } else {
                0.0
            }

            return PortfolioSummaryUiState(
                investedAmount = "€${currencyFormatter.format(investedAmount)}",
                currentValue = "€${currencyFormatter.format(currentValue)}",
                annualDividendIncome = "€${currencyFormatter.format(annualDividendIncome)}",
                yieldOnCost = "${percentFormatter.format(yieldOnCost)}%"
            )
        }
    }
}

class PortfolioViewModel(
    repository: PortfolioRepository = FakePortfolioRepository
) : ViewModel() {
    private val holdings = repository.getHoldings()

    private val _uiState = MutableStateFlow(PortfolioScreenUiState.from(holdings))
    val uiState: StateFlow<PortfolioScreenUiState> = _uiState.asStateFlow()

    fun onSearchQueryChange(query: String) {
        _uiState.value = PortfolioScreenUiState.from(
            holdings = holdings,
            searchQuery = query
        )
    }
}

private val currencyFormatter = DecimalFormat("#,##0.0#")
private val percentFormatter = DecimalFormat("0.##")

