package dev.elenivoreopoulou.dividendtracker.ui.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.elenivoreopoulou.dividendtracker.ui.theme.DividendTrackerTheme
import dev.elenivoreopoulou.dividendtracker.ui.theme.PrimaryBlue
import dev.elenivoreopoulou.dividendtracker.ui.theme.SuccessGreen

@Composable
fun TrendChip(
    text: String,
    modifier: Modifier = Modifier,
    leadingSymbol: String? = "↗",
    contentPadding: PaddingValues = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
) {
    val isDark = isSystemInDarkTheme()
    val displayText = leadingSymbol?.let { "$it $text" } ?: text

    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(50),
        color = SuccessGreen.copy(alpha = if (isDark) 0.28f else 0.16f)
    ) {
        Text(
            text = displayText,
            modifier = Modifier.padding(contentPadding),
            style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
            color = SuccessGreen
        )
    }
}

@Composable
fun YieldChip(
    text: String,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
) {
    val isDark = isSystemInDarkTheme()

    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(10.dp),
        color = PrimaryBlue.copy(alpha = if (isDark) 0.08f else 0.14f)
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(contentPadding),
            style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
            color = PrimaryBlue
        )
    }
}

@Preview(name = "Dividend Chips")
@Composable
private fun DividendChipPreview() {
    DividendTrackerTheme(darkTheme = true) {
        DividendCard {
            TrendChip(text = "+€120")
        }
    }
}

