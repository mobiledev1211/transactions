package nz.co.test.transactions.features.transactions.transactionslist.presentation

//import androidx.lifecycle.repeatOnLifecycle
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import nz.co.test.transactions.R
import nz.co.test.transactions.features.transactions.transactionslist.domain.TransactionDto
import nz.co.test.transactions.databinding.FragmentTransactionsListBinding
import nz.co.test.transactions.features.common.error.ErrorType
import nz.co.test.transactions.features.transactions.transactiondetails.TransactionDetailsFragment


class TransactionsListFragment : Fragment() {

    companion object {
        fun newInstance() = TransactionsListFragment()
    }
//    @Inject
//    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: TransactionsListViewModel by lazy {
        ViewModelProvider(this)[TransactionsListViewModel::class.java]
    }
    private lateinit var binding: FragmentTransactionsListBinding
    private val transactionsList: RecyclerView by lazy { binding.transactionsList }
    private val progressBar: View by lazy { binding.progressBar }


//    private val viewModel2: TransactionsListViewModel by lazy {
////        ViewModelProvider(this)[TransactionsListViewModel::class.java]
//        ViewModelProvider(
//            this,
//            viewModelFactory,
////            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
//        )[TransactionsListViewModel::class.java]
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTransactionsListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        val viewModel =  ViewModelProvider(this, viewModelFactory)
//            .get(TransactionsListViewModel::class.java)
//
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.showTransactionsList.collect {
                it?.let {
                    val sortedList = it.sortedByDescending { dto -> dto.transactionDate }
                    println("sortedList: $sortedList")
                    showTransactionsList(sortedList)
                }
            }
//            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
//                launch {
//                    viewModel.showProfile.collect {
//                        it?.let {
//                            showUserProfile(it)
//                        }
//                    }
//                }
//                launch {
//                    viewModel.showLoading.collect(::showLoading)
//                }
//
//                launch {
//                    viewModel.showError.collect {
//                        it?.let {
//                            showError(it)
//                        }
//                    }
//                }
//            }
        }

        viewModel.fetchTransactions()
    }

    private fun showTransactionsList(transactionDtos: List<TransactionDto>) {
        transactionsList.layoutManager = LinearLayoutManager(context)
        transactionsList.adapter = TransactionsListAdapter(
            transactionDtos,
            onItemClickListener = {
                parentFragmentManager.commit {
                    val transactionDetailsFragment = TransactionDetailsFragment.newInstance(
                        it
                    )
                    add(
                        R.id.transactions_container_view,
                        transactionDetailsFragment,
                    )
                    addToBackStack(transactionDetailsFragment.javaClass.name)
                }
            },
        )
    }


    fun onRetryClicked() {
    }

    fun showError(error: ErrorType?) {

    }

    fun showLoading(loading: Boolean) {
        progressBar.visibility = if (loading) View.VISIBLE else View.GONE
    }

}