package nz.co.test.transactions.features.transactions.transactionslist.data

import nz.co.test.transactions.features.transactions.transactionslist.domain.TransactionDto
import nz.co.test.transactions.services.TransactionsService
import javax.inject.Inject

class TransactionsRepositoryImpl @Inject constructor(
    private val apiService: TransactionsService
) : TransactionsRepository {

    override suspend fun getAllTransactions(): Result<List<TransactionDto>> {
        return try {
            val response = apiService.retrieveTransactions()

            run {
                Result.success(response)
            }

        } catch (exception: Exception) {
            Result.failure(exception)
        }
    }
}
