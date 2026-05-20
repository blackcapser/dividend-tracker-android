package dev.elenivoreopoulou.dividendtracker.domain.model

data class Holding(
    val id: Int,
    val companyName: String,
    val ticker: String,
    val shares: Double,
    val averagePrice: Double,
    val currentPrice: Double,
    val annualDividendPerShare: Double,
    val currency: String = "€"
) {
    val investedAmount: Double
        get() = shares * averagePrice

    val currentValue: Double
        get() = shares * currentPrice

    val annualDividendIncome: Double
        get() = shares * annualDividendPerShare

    val dividendYield: Double
        get() = if (currentValue > 0) annualDividendIncome / currentValue * 100 else 0.0
}