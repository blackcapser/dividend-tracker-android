package dev.elenivoreopoulou.dividendtracker.data.model

import dev.elenivoreopoulou.dividendtracker.domain.model.Holding

object FakePortfolioData {

    val holdings = listOf(
        Holding(
            id = 1,
            companyName = "OPAP",
            ticker = "OPAP.AT",
            shares = 7250.0,
            averagePrice = 17.52,
            currentPrice = 17.60,
            annualDividendPerShare = 1.85
        ),
        Holding(
            id = 2,
            companyName = "Alpha Services and Holdings",
            ticker = "ALPHA.AT",
            shares = 1500.0,
            averagePrice = 1.65,
            currentPrice = 1.72,
            annualDividendPerShare = 0.03
        ),
        Holding(
            id = 3,
            companyName = "Jumbo",
            ticker = "BELA.AT",
            shares = 100.0,
            averagePrice = 25.00,
            currentPrice = 27.40,
            annualDividendPerShare = 1.20
        )
    )
}