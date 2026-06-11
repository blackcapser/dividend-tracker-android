package dev.elenivoreopoulou.dividendtracker.data.repository

import dev.elenivoreopoulou.dividendtracker.data.model.FakePortfolioData
import dev.elenivoreopoulou.dividendtracker.domain.model.Holding

object FakePortfolioRepository : PortfolioRepository {
    override fun getHoldings(): List<Holding> = FakePortfolioData.holdings
}

