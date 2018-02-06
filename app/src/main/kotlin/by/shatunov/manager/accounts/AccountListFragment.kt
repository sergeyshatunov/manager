package by.shatunov.manager.accounts

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import by.shatunov.manager.MainActivity
import by.shatunov.manager.R
import by.shatunov.manager.viewmodel.ManagerViewModel
import kotlinx.android.synthetic.main.fragment_account_list.*
import kotlinx.android.synthetic.main.list_item_account.view.*
import java.util.*

class AccountListFragment : Fragment() {

    companion object {
        fun newInstance() = AccountListFragment()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        check(context is OnCreateAccountListener) { "OnCreateAccountListener must be implement" }
        check(context is OnEditAccountListener) { "OnEditAccountListener must be implement" }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_account_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel = ViewModelProviders.of(this).get(ManagerViewModel::class.java)

        account_list_recycler.layoutManager = LinearLayoutManager(context)
        account_list_recycler.adapter = Adapter(context as Context, Collections.emptyList())

        viewModel.getAllAccounts().observe(this, Observer {
            val adapter = Adapter(context as Context, it.orEmpty())
            account_list_recycler.adapter = adapter
        })

        account_list_fab.setOnClickListener({
            (activity as MainActivity).onCreateAccount()
        })
    }


    private class Holder(private val context: Context, view: View) : RecyclerView.ViewHolder(view) {
        fun bind(account: Account) {
            itemView.account_title.text = account.accountTitle
            itemView.account_amount.text = (account.accountCurrentAmount.toDouble() / 100).toString()
            itemView.setOnClickListener {
                Toast.makeText(context, "${itemView.account_title.text}, ${itemView.account_amount.text}", Toast.LENGTH_SHORT).show()
                (context as MainActivity).onEditAccount(account.accountId)
            }
        }
    }

    private class Adapter(private val context: Context, private val accounts: List<Account>) : RecyclerView.Adapter<Holder>() {
        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): Holder {
            val inflatedView = LayoutInflater.from(context).inflate(R.layout.list_item_account, parent, false)
            return Holder(context, inflatedView)
        }

        override fun getItemCount(): Int {
            return accounts.size
        }

        override fun onBindViewHolder(holder: Holder, position: Int) {
            holder.bind(accounts[position])
        }
    }

    interface OnCreateAccountListener {
        fun onCreateAccount() {}
    }

    interface OnEditAccountListener {
        fun onEditAccount(id: Int) {}
    }
}
