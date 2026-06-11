package dev.elenivoreopoulou.dividendtracker.presentation.addholding

import org.junit.Assert.assertEquals
import org.junit.Test

class AddHoldingViewModelTest {
    @Test
    fun onTickerChange_uppercasesAndLimitsTickerLength() {
        val viewModel = AddHoldingViewModel()

        viewModel.onTickerChange("opap.athens-long")

        assertEquals("OPAP.ATHENS-", viewModel.uiState.value.ticker)
    }

    @Test
    fun decimalInputs_keepDigitsAndSingleDecimalSeparator() {
        val viewModel = AddHoldingViewModel()

        viewModel.onSharesChange("12a,34.56")
        viewModel.onAveragePriceChange("€17,52")
        viewModel.onDividendPerShareChange("1..8x5")

        val uiState = viewModel.uiState.value
        assertEquals("12.3456", uiState.shares)
        assertEquals("17.52", uiState.averagePrice)
        assertEquals("1.85", uiState.dividendPerShare)
    }

    @Test
    fun onCompanyNameChange_updatesCompanyNameOnly() {
        val viewModel = AddHoldingViewModel()

        viewModel.onCompanyNameChange("OPAP")

        val uiState = viewModel.uiState.value
        assertEquals("OPAP", uiState.companyName)
        assertEquals("", uiState.ticker)
        assertEquals("", uiState.shares)
    }
}

