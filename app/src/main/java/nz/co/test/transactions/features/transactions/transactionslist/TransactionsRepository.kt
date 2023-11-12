package nz.co.test.transactions.features.transactions.transactionslist

import nz.co.test.transactions.TransactionDto


interface TransactionsRepository {
    suspend fun getAllTransactions(): Result<List<TransactionDto>>
}