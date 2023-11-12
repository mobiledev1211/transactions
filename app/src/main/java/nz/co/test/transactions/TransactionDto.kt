package nz.co.test.transactions

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.text.NumberFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Parcelize
data class TransactionDto(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("transactionDate") var transactionDate: String? = null,
    @SerializedName("summary") var summary: String? = null,
    @SerializedName("debit") var debit: Double? = null,
    @SerializedName("credit") var credit: Double? = null
) : Parcelable {
    private fun getAmount(): Double = if (credit != 0.0) {
        (credit ?: 0.0)
    } else -1 * (debit ?: 0.0)

    private fun getGst(): Double {
        val amount = getAmount()
        val gstPercentage = 0.15 // 15% GST
        return amount * gstPercentage
    }

    fun getFormattedAmount(): String {
        val amount = getAmount()
        val numberFormat = NumberFormat.getCurrencyInstance(Locale.US)
        return numberFormat.format(amount)
    }

    fun getFormattedGst(): String {
        val amount = getGst()
        val numberFormat = NumberFormat.getCurrencyInstance(Locale.US)
        return numberFormat.format(amount)
    }

    fun getShortDate(): String {
        if (transactionDate.isNullOrEmpty()) return ""

        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US)
        val outputFormat = SimpleDateFormat("EEE dd MMM yyyy", Locale.US)

        return try {
            val date = transactionDate?.let { inputFormat.parse(it) }
            outputFormat.format(date ?: Date()).uppercase(Locale.ROOT)
        } catch (e: ParseException) {
            ""
        }
    }

    fun getLongDate(): String {
        if (transactionDate.isNullOrEmpty()) return ""

        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US)
        val outputFormat = SimpleDateFormat("hh:mm:ss a, EEE, dd MMM yyyy", Locale.US)

        return try {
            val date = transactionDate?.let { inputFormat.parse(it) }
            outputFormat.format(date ?: Date())
        } catch (e: ParseException) {
            ""
        }
    }

}

