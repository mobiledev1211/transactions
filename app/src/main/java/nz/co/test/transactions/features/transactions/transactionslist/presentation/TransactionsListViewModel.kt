package nz.co.test.transactions.features.transactions.transactionslist.presentation

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import nz.co.test.transactions.BuildConfig
import nz.co.test.transactions.features.transactions.transactionslist.domain.TransactionDto
import nz.co.test.transactions.features.common.error.ErrorType
import nz.co.test.transactions.services.TransactionsService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject


class TransactionsListViewModel @Inject constructor(
//    private val transactionsUseCases: TransactionsUseCases
) : ViewModel() {

    companion object {
        val TAG: String = TransactionsListViewModel::class.java.simpleName
    }

    private val _showLoading = MutableStateFlow(true)
    val showLoading: StateFlow<Boolean> = _showLoading
    private val _showError = MutableStateFlow<ErrorType?>(null)
    val showError: StateFlow<ErrorType?> = _showError
    private val _showProfile = MutableStateFlow<List<TransactionDto>?>(null)
    val showTransactionsList: StateFlow<List<TransactionDto>?> = _showProfile


    val transactions = MutableLiveData<Result<List<TransactionDto>>>()

    fun fetchTransactions() {
        Log.d(TAG, "fetchTransactions")

        viewModelScope.launch {
//            transactions.value = transactionsUseCases.getTransactions()
            // use TransactionsUseCases to get transactions
//            TransactionsUseCases(
//                transactionsRepository = TransactionsRepositoryImpl(
//                    apiService = TransactionsService(
//
//                    )
//                )
//            )

            val interceptor = HttpLoggingInterceptor()
            if (BuildConfig.DEBUG) interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            val client: OkHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()

            val retrofit = Retrofit.Builder()
                .baseUrl("https://gist.githubusercontent.com/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val service: TransactionsService = retrofit.create(TransactionsService::class.java)
            val txns: List<TransactionDto> = service.retrieveTransactions()
            Log.d(TAG, "txns: $txns")
            _showProfile.value = txns
        }
    }

}