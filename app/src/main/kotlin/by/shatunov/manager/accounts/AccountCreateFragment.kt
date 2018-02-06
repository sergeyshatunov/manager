package by.shatunov.manager.accounts

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import by.shatunov.manager.R
import by.shatunov.manager.viewmodel.ManagerViewModel
import kotlinx.android.synthetic.main.fragment_account_create.*
import kotlinx.android.synthetic.main.fragment_account_create.view.*
import java.util.*

class AccountCreateFragment : Fragment() {

    private var decimalAmount = 0.0

    companion object {
        fun newInstance() = AccountCreateFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_account_create, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel = ViewModelProviders.of(this).get(ManagerViewModel::class.java)

        save_button.setOnClickListener {
            if (account_title.text.toString() != "" && account_initial_amount.text.toString() != "0.0") {
                val account = Account(0,
                        Integer.valueOf(account_initial_amount.text.toString()),
                        Date(),
                        account_title.text.toString())

                viewModel.createAccount(account)
                Toast.makeText(context, "Account created", Toast.LENGTH_SHORT).show()
                activity?.onBackPressed()
            } else {
                Toast.makeText(context, "Enter title and initial amount", Toast.LENGTH_SHORT).show()
            }
        }

        account_amount_text.setOnClickListener {
            account_initial_amount.requestFocusFromTouch()
            val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(view.account_initial_amount, 0)
        }

        account_initial_amount.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                try {
                    decimalAmount = s.toString().toDouble() / 100
                    account_amount_text.text = decimalAmount.toString()
                } catch (e: NumberFormatException) {
                    decimalAmount = 0.0
                    account_amount_text.text = "0.00"
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
        })
    }
}
