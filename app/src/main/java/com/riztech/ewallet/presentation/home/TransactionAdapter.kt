package com.riztech.ewallet.presentation.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.riztech.ewallet.R
import com.riztech.ewallet.databinding.ListItemTransactionBinding
import com.riztech.ewallet.domain.model.UserTransaction
import com.riztech.ewallet.presentation.Constants
import java.text.SimpleDateFormat
import java.util.*

class TransactionAdapter(val transactions: List<UserTransaction>) : RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>() {

    class TransactionViewHolder(val binding: ListItemTransactionBinding): RecyclerView.ViewHolder(binding.root){
        private fun convertDate(createdAt: Long): String{
            val sdf = SimpleDateFormat("dd/MM/yy hh:mm a")
            val netDate = Date(createdAt*1000)
            val date = sdf.format(netDate)
            return date
        }

        fun bind(userTransaction: UserTransaction){
            when(userTransaction.label){
                Constants.LABEL_TRANSFER -> {
                    binding.tvAmount.text = "-${userTransaction.amount}"
                    binding.tvLabel.text = "Transfer To ${userTransaction.receiver}"
                    binding.ivLogo.setImageResource(R.drawable.ic_withdraw)
                    binding.ivLogo.setBackgroundColor(ContextCompat.getColor(binding.root.context, R.color.red))
                    binding.tvDate.text = convertDate(userTransaction.createdAt)
                }
                Constants.LABEL_WITHDRAW -> {
                    binding.tvAmount.text = "-${userTransaction.amount}"
                    binding.tvLabel.text = "Withdraw by ${userTransaction.sender}"
                    binding.ivLogo.setImageResource(R.drawable.ic_up)
                    binding.ivLogo.setBackgroundColor(ContextCompat.getColor(binding.root.context, R.color.red))
                    binding.tvDate.text = convertDate(userTransaction.createdAt)
                }
                Constants.LABEL_TOPUP -> {
                    binding.tvAmount.text = "+${userTransaction.amount}"
                    binding.tvLabel.text = "TOP UP by ${userTransaction.sender}"
                    binding.ivLogo.setImageResource(R.drawable.ic_withdraw)
                    binding.ivLogo.setBackgroundColor(ContextCompat.getColor(binding.root.context, R.color.green))
                    binding.tvDate.text = convertDate(userTransaction.createdAt)
                }
            }
        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val binding = ListItemTransactionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TransactionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        holder.bind(transactions[position])
    }

    override fun getItemCount(): Int {
        return transactions.size
    }
}