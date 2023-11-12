package nz.co.test.transactions.features.transactions.transactionslist

import nz.co.test.transactions.TransactionDto
import javax.inject.Inject

class TransactionsUseCases @Inject constructor(private val transactionsRepository: TransactionsRepository) {

    suspend fun getTransactions(): Result<List<TransactionDto>> {
        return transactionsRepository.getAllTransactions()
    }
}