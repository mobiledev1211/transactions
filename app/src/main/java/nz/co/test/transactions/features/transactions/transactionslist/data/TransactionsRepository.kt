package nz.co.test.transactions.features.transactions.transactionslist.data

import nz.co.test.transactions.features.transactions.transactionslist.domain.TransactionDto


interface TransactionsRepository {
    suspend fun getAllTransactions(): Result<List<TransactionDto>>
}