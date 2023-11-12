package nz.co.test.transactions

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import nz.co.test.transactions.features.common.error.ErrorType
import nz.co.test.transactions.features.transactions.transactionslist.domain.TransactionDto
import nz.co.test.transactions.features.transactions.transactionslist.presentation.TransactionsListViewModel
import nz.co.test.transactions.services.TransactionsService
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class TransactionsListViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    // Mocking the service
    @Mock
    private lateinit var transactionsService: TransactionsService

    // Class under test
    private lateinit var viewModel: TransactionsListViewModel

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        viewModel = TransactionsListViewModel(/*transactionsService*/)
    }

    @Test
    fun `test fetchTransactions success`() = runBlockingTest {
        // Mock data
        val mockTransactions = listOf(
            TransactionDto(
                id = 1,
                transactionDate = "2020-01-01",
                summary = "Test summary",
                debit = 100.0,
                credit = 0.0,
            ),
            TransactionDto(
                id = 2,
                transactionDate = "2030-01-01",
                summary = "Test summary",
                debit = 0.0,
                credit = 100.0,
            ),
        )

        // Mock service response
        Mockito.`when`(transactionsService.retrieveTransactions()).thenReturn(mockTransactions)

        // Call the method to be tested
        viewModel.fetchTransactions()

        // Assert the expected values after the method call
        assert(viewModel.showTransactionsList.value == mockTransactions)
        assert(!viewModel.showLoading.value)
        assert(viewModel.showError.value == null)
    }

    @Test
    fun `test fetchTransactions failure`() = runBlockingTest {
        // Mock error response
        val mockErrorType = ErrorType.GENERIC_ERROR

        // Mock service response with an error
        Mockito.`when`(transactionsService.retrieveTransactions())
            .thenThrow(RuntimeException("Error"))

        // Call the method to be tested
        viewModel.fetchTransactions()

        // Assert the expected values after the method call
        assert(viewModel.showTransactionsList.value == null)
        assert(!viewModel.showLoading.value)
        assert(viewModel.showError.value == mockErrorType)
    }
}

// Reusable JUnit4 TestRule to override the Main dispatcher
@OptIn(ExperimentalCoroutinesApi::class)
class MainDispatcherRule(
    private val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()
) : TestWatcher() {
    override fun starting(description: Description) {
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description) {
        Dispatchers.resetMain()
    }
}