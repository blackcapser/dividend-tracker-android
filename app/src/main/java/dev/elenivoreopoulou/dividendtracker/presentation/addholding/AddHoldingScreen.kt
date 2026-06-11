package dev.elenivoreopoulou.dividendtracker.presentation.addholding

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import dev.elenivoreopoulou.dividendtracker.ui.theme.isDividendInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.elenivoreopoulou.dividendtracker.ui.components.DividendPrimaryButton
import dev.elenivoreopoulou.dividendtracker.ui.components.DividendAppHeader
import dev.elenivoreopoulou.dividendtracker.ui.components.DividendBackTitleRow
import dev.elenivoreopoulou.dividendtracker.ui.components.DividendScreenBottomPadding
import dev.elenivoreopoulou.dividendtracker.ui.components.DividendScreenHorizontalPadding
import dev.elenivoreopoulou.dividendtracker.ui.components.DividendScreenTopPadding
import dev.elenivoreopoulou.dividendtracker.ui.components.DividendTextField
import dev.elenivoreopoulou.dividendtracker.ui.theme.DarkBackground
import dev.elenivoreopoulou.dividendtracker.ui.theme.DarkOutline
import dev.elenivoreopoulou.dividendtracker.ui.theme.DarkTextSecondary
import dev.elenivoreopoulou.dividendtracker.ui.theme.DividendTrackerTheme
import dev.elenivoreopoulou.dividendtracker.ui.theme.LightBackground
import dev.elenivoreopoulou.dividendtracker.ui.theme.LightOutline
import dev.elenivoreopoulou.dividendtracker.ui.theme.LightTextSecondary

@Composable
fun AddHoldingScreen(
    onBackClick: () -> Unit = {}
) {
    var companyName by rememberSaveable { mutableStateOf("") }
    var ticker by rememberSaveable { mutableStateOf("") }
    var shares by rememberSaveable { mutableStateOf("") }
    var averagePrice by rememberSaveable { mutableStateOf("") }
    var dividendPerShare by rememberSaveable { mutableStateOf("") }
    val isDark = isDividendInDarkTheme()
    val backgroundColor = if (isDark) DarkBackground else LightBackground
    val secondaryTextColor = if (isDark) DarkTextSecondary else LightTextSecondary

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .verticalScroll(rememberScrollState())
            .imePadding()
            .padding(horizontal = DividendScreenHorizontalPadding)
            .padding(top = DividendScreenTopPadding, bottom = DividendScreenBottomPadding)
    ) {
        DividendAppHeader()

        DividendBackTitleRow(
            title = "Add Holding",
            onBackClick = onBackClick,
            modifier = Modifier.padding(top = 34.dp)
        )

        Column(
            modifier = Modifier.padding(top = 48.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            LabeledTextField(
                label = "Company name",
                value = companyName,
                onValueChange = { companyName = it },
                placeholder = "Enter company name",
                labelColor = secondaryTextColor
            )

            LabeledTextField(
                label = "Ticker",
                value = ticker,
                onValueChange = { ticker = it.uppercase().take(MaxTickerLength) },
                placeholder = "e.g. AAPL",
                labelColor = secondaryTextColor
            )

            LabeledTextField(
                label = "Shares",
                value = shares,
                onValueChange = { shares = it.sanitizeDecimalInput() },
                placeholder = "Number of shares",
                labelColor = secondaryTextColor
            )

            LabeledTextField(
                label = "Average buy price",
                value = averagePrice,
                onValueChange = { averagePrice = it.sanitizeDecimalInput() },
                placeholder = "€0.00",
                labelColor = secondaryTextColor
            )

            LabeledTextField(
                label = "Annual dividend per share",
                value = dividendPerShare,
                onValueChange = { dividendPerShare = it.sanitizeDecimalInput() },
                placeholder = "€0.00",
                labelColor = secondaryTextColor
            )
        }

        DividendPrimaryButton(
            text = "Save Holding",
            onClick = {
                // Repository save will be connected when persistence is introduced.
            },
            modifier = Modifier.padding(top = 38.dp)
        )
    }
}


@Composable
private fun LabeledTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    labelColor: Color,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
            color = labelColor
        )

        OutlinedDividendTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = placeholder
        )
    }
}

@Composable
private fun OutlinedDividendTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String
) {
    val isDark = isDividendInDarkTheme()
    val outlineColor = if (isDark) DarkOutline.copy(alpha = 0.55f) else LightOutline.copy(alpha = 0.95f)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = outlineColor,
                shape = RoundedCornerShape(15.dp)
            )
    ) {
        DividendTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = placeholder,
            modifier = Modifier.fillMaxWidth()
        )
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

@Preview(
    name = "Add Holding Light",
    showBackground = true
)
@Composable
private fun AddHoldingScreenLightPreview() {
    DividendTrackerTheme(darkTheme = false) {
        AddHoldingScreen()
    }
}

@Preview(
    name = "Add Holding Dark",
    showBackground = true
)
@Composable
private fun AddHoldingScreenDarkPreview() {
    DividendTrackerTheme(darkTheme = true) {
        AddHoldingScreen()
    }
}

