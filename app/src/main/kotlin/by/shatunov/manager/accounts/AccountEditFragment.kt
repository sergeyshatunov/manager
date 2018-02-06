package by.shatunov.manager.accounts

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import by.shatunov.manager.R
import by.shatunov.manager.viewmodel.ManagerViewModel
import kotlinx.android.synthetic.main.fragment_account_edit.*

class AccountEditFragment : Fragment() {

    companion object {
        private const val ID = "ID"

        fun newInstance(id: Int): AccountEditFragment {
            val fragment = AccountEditFragment()
            val args = Bundle()
            args.putInt(ID, id)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_account_edit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = arguments?.getInt(ID)

        val viewModel = ViewModelProviders.of(this).get(ManagerViewModel::class.java)

        id?.let {
            viewModel.getAccountById(it)?.observe(this, Observer {
                val account = it
                account_title.setText(account?.accountTitle)
                account_initial_amount.setText(((account?.accountCurrentAmount?.toDouble()
                        ?: 0.0) / 100).toString())

                save_button.setOnClickListener {
                    val updatedAccount = Account(account!!.accountId,
                            Integer.valueOf(account_initial_amount.text.toString()),
                            account.accountInitDate,
                            account_title.text.toString())
                    Toast.makeText(this.activity, "save_button", Toast.LENGTH_SHORT).show()
                    viewModel.updateAccount(updatedAccount)
                    this.activity?.onBackPressed()
                }

                delete_button.setOnClickListener {
                    viewModel.deleteAccount(account!!)
                    this.activity?.onBackPressed()
                }
            })
        }
    }
}
