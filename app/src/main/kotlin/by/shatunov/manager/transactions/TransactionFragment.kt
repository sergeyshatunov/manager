package by.shatunov.manager.transactions

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import by.shatunov.manager.R
import by.shatunov.manager.accounts.Account
import by.shatunov.manager.viewmodel.ManagerViewModel
import kotlinx.android.synthetic.main.fragment_transaction.*
import java.util.*

class TransactionFragment : Fragment() {

    companion object {
        fun newInstance(id: Int?) = TransactionFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_transaction, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel = ViewModelProviders.of(this).get(ManagerViewModel::class.java)

        viewModel.getAllAccounts().observe(this, Observer {
            if (it != null) {
                for (account: Account in it) {
                    val radioButton = RadioButton(this.activity)
                    radioButton.text = account.accountTitle
                    radioButton.id = account.accountId
                    transaction_accounts_radio.addView(radioButton)
                }
            }
        })

        button_expense.setOnClickListener {
            if (transaction_title.text.toString() != "" && transaction_amount.text.toString() != "" && transaction_accounts_radio.checkedRadioButtonId != -1) {
                val transaction = Transaction(0,
                        transaction_title.text.toString(),
                        transaction_accounts_radio.checkedRadioButtonId,
                        Integer.valueOf(transaction_amount.text.toString()),
                        Date())

                viewModel.getAccountById(transaction_accounts_radio.checkedRadioButtonId)?.observe(this, Observer {
                    it?.let {
                        val updatedAccount = Account(it.accountId,
                                (Integer.valueOf(it.accountCurrentAmount.toString()) - Integer.valueOf(transaction_amount.text.toString())),
                                it.accountInitDate,
                                it.accountTitle)

                        viewModel.createTransaction(transaction, updatedAccount)
                        this.activity?.onBackPressed()
                    }
                })
            }
        }

        button_income.setOnClickListener {
            if (transaction_title.text.toString() != "" && transaction_amount.text.toString() != "" && transaction_accounts_radio.checkedRadioButtonId != -1) {
                val transaction = Transaction(0,
                        transaction_title.text.toString(),
                        transaction_accounts_radio.checkedRadioButtonId,
                        Integer.valueOf(transaction_amount.text.toString()),
                        Date())

                viewModel.getAccountById(transaction_accounts_radio.checkedRadioButtonId)?.observe(this, Observer {
                    it?.let {
                        val updatedAccount = Account(it.accountId,
                                (Integer.valueOf(it.accountCurrentAmount.toString()) + Integer.valueOf(transaction_amount.text.toString())),
                                it.accountInitDate,
                                it.accountTitle)

                        viewModel.createTransaction(transaction, updatedAccount)
                        this.activity?.onBackPressed()
                    }
                })
            }
        }
    }
}
