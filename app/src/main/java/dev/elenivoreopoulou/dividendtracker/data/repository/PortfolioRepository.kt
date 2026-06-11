package dev.elenivoreopoulou.dividendtracker.data.repository

import dev.elenivoreopoulou.dividendtracker.domain.model.Holding

interface PortfolioRepository {
    fun getHoldings(): List<Holding>
}

