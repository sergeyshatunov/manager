package by.shatunov.manager.transactions

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.shatunov.manager.MainActivity
import by.shatunov.manager.R
import by.shatunov.manager.viewmodel.ManagerViewModel
import kotlinx.android.synthetic.main.fragment_transaction_list.*
import kotlinx.android.synthetic.main.list_item_transaction.view.*
import java.util.*

class TransactionListFragment : Fragment() {

    companion object {
        fun newInstance() = TransactionListFragment()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        check(context is OnTransactionListener) { "OnTransactionListener must be implement" }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_transaction_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel = ViewModelProviders.of(this).get(ManagerViewModel::class.java)

        transaction_list_recycler.layoutManager = LinearLayoutManager(context)
        transaction_list_recycler.adapter = TransactionListFragment.Adapter(context as Context, Collections.emptyList())

        viewModel.getAllTransactions().observe(this, android.arch.lifecycle.Observer {
            val adapter = Adapter(context as Context, it.orEmpty())
            transaction_list_recycler.adapter = adapter
        })

        transaction_list_fab.setOnClickListener {
            (activity as MainActivity).onTransactionCreate(null)
        }
    }

    class Holder(private val context: Context, private val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(transaction: Transaction) {
            itemView.transaction_list_item_title.text = transaction.transactionTitle
            itemView.transaction_list_item_amount.text = (transaction.transactionAmount.toDouble() / 100).toString()
        }
    }

    class Adapter(private val context: Context, private val transactions: List<Transaction>) : RecyclerView.Adapter<Holder>() {
        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): Holder {
            val inflatedView = LayoutInflater.from(context).inflate(R.layout.list_item_transaction, parent, false)
            return Holder(context, inflatedView)
        }

        override fun getItemCount(): Int {
            return transactions.size
        }

        override fun onBindViewHolder(holder: Holder, position: Int) {
            holder.bind(transactions[position])
        }
    }

    interface OnTransactionListener {
        fun onTransactionCreate(id: Int?) {}
    }
}
