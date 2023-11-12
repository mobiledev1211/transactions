package nz.co.test.transactions.features.common.error

import androidx.annotation.Keep

@Keep
data class ErrorResponseModel(val error: ErrorModel)

@Keep
data class ErrorModel(
    val type: String, val title: String,
    val status: Int
)