package nz.co.test.transactions.features.transactions.transactionslist

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import nz.co.test.transactions.R

class TransactionsListFragment : Fragment() {

    companion object {
        fun newInstance() = TransactionsListFragment()
    }

    private val viewModel: TransactionsListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_transactions_list, container, false)
    }
}