package dev.elenivoreopoulou.dividendtracker.presentation.dividends

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class DividendCalendarViewModelTest {
    @Test
    fun uiState_exposesDefaultAnnualCalendarSummary() {
        val viewModel = DividendCalendarViewModel()

        val uiState = viewModel.uiState.value

        assertEquals("2026", uiState.selectedYear)
        assertEquals("€7,096", uiState.expectedIncome)
        assertEquals(12, uiState.months.size)

        val may = uiState.months.first { it.name == "May" }
        assertTrue(may.hasPayouts)
        assertEquals("€6,706", may.totalPayoutAmount)
        assertEquals(listOf("OPAP.AT", "EEE.AT"), may.payouts.map { it.ticker })
        assertEquals(listOf("€450", "€6,256"), may.payouts.map { it.formattedAmount })

        val january = uiState.months.first { it.name == "Jan" }
        assertFalse(january.hasPayouts)
        assertEquals("€0", january.totalPayoutAmount)
    }
}

