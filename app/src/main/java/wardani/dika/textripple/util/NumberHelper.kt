package wardani.dika.textripple.util

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols

fun toCurrency(currencySymbol: String, value: Double, groupingSeparator: Char, decimalSeparator: Char): String {
    val pattern = "$currencySymbol #,###.00"
    val numberFormat = DecimalFormat(pattern).apply {
        decimalFormatSymbols = DecimalFormatSymbols().apply {
            this.groupingSeparator = groupingSeparator
            this.decimalSeparator = decimalSeparator
        }
    }

    return  numberFormat.format(value)
}

fun toIndonesiaCurrency(value: Double): String {
    return toCurrency(
        currencySymbol = "Rp",
        value = value,
        groupingSeparator = '.',
        decimalSeparator = ','
    )
}