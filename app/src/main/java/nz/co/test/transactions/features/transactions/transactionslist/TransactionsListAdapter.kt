package nz.co.test.transactions.features.transactions.transactionslist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.Group
import androidx.recyclerview.widget.RecyclerView
import nz.co.test.transactions.TransactionDto
import nz.co.test.transactions.databinding.ItemTransactionBinding
import java.text.NumberFormat
import java.util.Locale

data class GroupedTransactionItem(
    val isHeader: Boolean,
    val headerText: String?,
    val finalAmount: Double?,
    val transaction: TransactionDto?
)

class TransactionsListAdapter(
    transactions: List<TransactionDto>,
    private val onItemClickListener: (TransactionDto) -> Unit,
) :
    RecyclerView.Adapter<TransactionsListAdapter.ViewHolder>() {

    private val groupedItems: List<GroupedTransactionItem> = generateGroupedItems(transactions)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ItemTransactionBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = groupedItems[position]

        val isHeader = item.isHeader
        holder.dividers.visibility = if (isHeader) View.VISIBLE else View.INVISIBLE
        val transaction = item.transaction
        holder.textShortDate.apply {
            text = if (isHeader) item.headerText else transaction?.getShortDate()
            visibility = if (isHeader) View.VISIBLE else View.GONE
        }
        holder.textAmount.text = if (isHeader) formatAmount(item.finalAmount) else {
            transaction?.getFormattedAmount() ?: ""
        }
        holder.textSummary.text = if (isHeader) "" else transaction?.summary

        holder.itemView.setOnClickListener {
            if (!isHeader)
                transaction?.let { dto -> onItemClickListener.invoke(dto) }
        }
    }

    override fun getItemCount(): Int = groupedItems.size

    override fun getItemViewType(position: Int): Int = position

    override fun getItemId(position: Int): Long = position.toLong()

    class ViewHolder(binding: ItemTransactionBinding) : RecyclerView.ViewHolder(binding.root) {
        val textShortDate: TextView = binding.textTransactionDate
        val textSummary: TextView = binding.textSummary
        val textAmount: TextView = binding.textAmount
        val dividers: Group = binding.dividers
    }

    private fun generateGroupedItems(transactions: List<TransactionDto>): List<GroupedTransactionItem> {
        val groupedItems = mutableListOf<GroupedTransactionItem>()
        val dailyAmountMap = mutableMapOf<String, Double>()

        for (transaction in transactions) {
            val transactionDate = transaction.getShortDate()

            // Update daily amount
            val currentAmount = dailyAmountMap.getOrDefault(transactionDate, 0.0)
            val newAmount = currentAmount + (transaction.credit ?: 0.0) - (transaction.debit ?: 0.0)
            dailyAmountMap[transactionDate] = newAmount

            // Find an existing header for the same date
            val existingHeaderIndex = groupedItems.indexOfFirst { it.headerText == transactionDate }

            if (existingHeaderIndex == -1) {
                // If header doesn't exist, add a new one
                groupedItems.add(
                    GroupedTransactionItem(
                        true,
                        transactionDate,
                        dailyAmountMap[transactionDate],
                        null
                    )
                )
            } else {
                // If header exists, create a new instance with updated finalAmount
                val existingHeader = groupedItems[existingHeaderIndex]
                val updatedHeader =
                    existingHeader.copy(finalAmount = dailyAmountMap[transactionDate])
                groupedItems[existingHeaderIndex] = updatedHeader
            }

            // Add the transaction item
            groupedItems.add(GroupedTransactionItem(false, null, null, transaction))
        }
        return groupedItems
    }

    private fun formatAmount(amount: Double?): String {
        if (amount == null) return ""

        val numberFormat = NumberFormat.getCurrencyInstance(Locale.US)
        return numberFormat.format(amount)
    }
}
