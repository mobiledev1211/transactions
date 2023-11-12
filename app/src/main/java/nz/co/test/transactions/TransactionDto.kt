package nz.co.test.transactions

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class TransactionDto(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("transactionDate") var transactionDate: String? = null,
    @SerializedName("summary") var summary: String? = null,
    @SerializedName("debit") var debit: Double? = null,
    @SerializedName("credit") var credit: Double? = null
) : Parcelable