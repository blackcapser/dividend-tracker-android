package dev.elenivoreopoulou.dividendtracker.presentation.addholding

import androidx.lifecycle.ViewModel
import java.util.Locale
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class AddHoldingUiState(
    val companyName: String = "",
    val ticker: String = "",
    val shares: String = "",
    val averagePrice: String = "",
    val dividendPerShare: String = ""
)

class AddHoldingViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(AddHoldingUiState())
    val uiState: StateFlow<AddHoldingUiState> = _uiState.asStateFlow()

    fun onCompanyNameChange(companyName: String) {
        _uiState.value = _uiState.value.copy(companyName = companyName)
    }

    fun onTickerChange(ticker: String) {
        _uiState.value = _uiState.value.copy(
            ticker = ticker.uppercase(Locale.ROOT).take(MaxTickerLength)
        )
    }

    fun onSharesChange(shares: String) {
        _uiState.value = _uiState.value.copy(shares = shares.sanitizeDecimalInput())
    }

    fun onAveragePriceChange(averagePrice: String) {
        _uiState.value = _uiState.value.copy(averagePrice = averagePrice.sanitizeDecimalInput())
    }

    fun onDividendPerShareChange(dividendPerShare: String) {
        _uiState.value = _uiState.value.copy(dividendPerShare = dividendPerShare.sanitizeDecimalInput())
    }

    fun onSaveHoldingClick() {
        // Repository save will be connected when persistence is introduced.
    }
}

private fun String.sanitizeDecimalInput(): String {
    var hasSeparator = false

    return buildString {
        this@sanitizeDecimalInput.forEach { character ->
            when {
                character.isDigit() -> append(character)
                (character == '.' || character == ',') && !hasSeparator -> {
                    append('.')
                    hasSeparator = true
                }
            }
        }
    }
}

private const val MaxTickerLength = 12

