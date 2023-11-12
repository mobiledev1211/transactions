package nz.co.test.transactions.services

import nz.co.test.transactions.TransactionDto
import retrofit2.http.GET

interface TransactionsService {
    @GET("Josh-Ng/500f2716604dc1e8e2a3c6d31ad01830/raw/4d73acaa7caa1167676445c922835554c5572e82/test-data.json")
    suspend fun retrieveTransactions(): List<TransactionDto>
}

