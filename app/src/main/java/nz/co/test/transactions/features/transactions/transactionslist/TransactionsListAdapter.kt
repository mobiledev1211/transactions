package nz.co.test.transactions.features.transactions.transactionslist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.Group
import androidx.recyclerview.widget.RecyclerView
import nz.co.test.transactions.R
import nz.co.test.transactions.TransactionDto
import java.text.NumberFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class GroupedTransactionItem(
    val isHeader: Boolean,
    val headerText: String?,
    val finalAmount: Double?,
    val transaction: TransactionDto?
)

class TransactionsListAdapter(transactions: List<TransactionDto>) :
    RecyclerView.Adapter<TransactionsListAdapter.ViewHolder>() {

    private val groupedItems: List<GroupedTransactionItem> = generateGroupedItems(transactions)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_transaction, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = groupedItems[position]

        val isHeader = item.isHeader
        holder.dividers.visibility = if (isHeader) View.VISIBLE else View.INVISIBLE
        holder.textShortDate.apply {
            text =
                if (isHeader) item.headerText else getShortDate(item.transaction?.transactionDate)
            visibility = if (isHeader) View.VISIBLE else View.GONE
        }
        holder.textAmount.text = if (isHeader) formatAmount(item.finalAmount) else {
            val amount = if (item.transaction?.credit != 0.0) {
                item.transaction?.credit
            } else -1 * (item.transaction.debit ?: 0.0)
            formatAmount(amount)
        }
        holder.textSummary.text = if (isHeader) "" else item.transaction?.summary
    }

    override fun getItemCount(): Int = groupedItems.size

    override fun getItemViewType(position: Int): Int = position
    override fun getItemId(position: Int): Long = position.toLong()
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textShortDate: TextView = itemView.findViewById(R.id.textTransactionDate)
        val textSummary: TextView = itemView.findViewById(R.id.textSummary)
        val textAmount: TextView = itemView.findViewById(R.id.textAmount)
        val dividers: Group = itemView.findViewById(R.id.dividers)
    }

    private fun generateGroupedItems(transactions: List<TransactionDto>): List<GroupedTransactionItem> {
        val groupedItems = mutableListOf<GroupedTransactionItem>()
        val dailyAmountMap = mutableMapOf<String, Double>()

        for (transaction in transactions) {
            val transactionDate = getShortDate(transaction.transactionDate)

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

    private fun getShortDate(fullDate: String?): String {
        if (fullDate.isNullOrEmpty()) return ""

        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US)
        val outputFormat = SimpleDateFormat("EEE dd MMM yyyy", Locale.US)

        return try {
            val date = inputFormat.parse(fullDate)
            outputFormat.format(date ?: Date()).uppercase(Locale.ROOT)
        } catch (e: ParseException) {
            ""
        }
    }

    private fun formatAmount(amount: Double?): String {
        if (amount == null) return ""

        val numberFormat = NumberFormat.getCurrencyInstance(Locale.US)
        return numberFormat.format(amount)
    }
}

