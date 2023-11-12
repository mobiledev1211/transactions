package nz.co.test.transactions.features.transactions.transactiondetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import nz.co.test.transactions.R
import nz.co.test.transactions.TransactionDto
import nz.co.test.transactions.databinding.FragmentTransactionDetailsBinding

private const val ARG_TRANSACTION = "transaction"

class TransactionDetailsFragment : Fragment() {

    companion object {
        fun newInstance(transactionDto: TransactionDto) =
            TransactionDetailsFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_TRANSACTION, transactionDto)
                }
            }
    }

    private val viewModel: TransactionDetailsViewModel by viewModels()

    private lateinit var binding: FragmentTransactionDetailsBinding

    private var transaction: TransactionDto? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            transaction = it.getParcelable(ARG_TRANSACTION)
        }
        println("transaction: $transaction")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        binding = FragmentTransactionDetailsBinding.inflate(inflater)
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_transaction_details, container, false
        )
        binding.transaction = transaction
        binding.lifecycleOwner = this
        return binding.root
    }
}