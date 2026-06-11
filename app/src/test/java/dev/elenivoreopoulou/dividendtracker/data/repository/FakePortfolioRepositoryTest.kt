package dev.elenivoreopoulou.dividendtracker.data.repository

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class FakePortfolioRepositoryTest {
    @Test
    fun getHoldings_returnsExpectedFakePortfolio() {
        val holdings = FakePortfolioRepository.getHoldings()

        assertEquals(4, holdings.size)
        assertTrue(holdings.any { it.ticker == "OPAP.AT" })
        assertTrue(holdings.all { it.shares > 0.0 })
        assertTrue(holdings.all { it.currentValue > 0.0 })
    }
}

