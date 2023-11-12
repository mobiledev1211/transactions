package nz.co.test.transactions.features.common.error

sealed class ErrorType {
    object NO_DATA : ErrorType()
    object DATA_NOT_READY : ErrorType()
    object SOMETHING_WENT_WRONG : ErrorType()
    object GENERIC_ERROR : ErrorType()
    object NO_INTERNET : ErrorType()
    data class CUSTOMIZED_ERROR(val errorResponse: ErrorResponseModel?) : ErrorType()
}
