package by.shatunov.manager

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import by.shatunov.manager.accounts.AccountCreateFragment
import by.shatunov.manager.accounts.AccountEditFragment
import by.shatunov.manager.accounts.AccountListFragment
import by.shatunov.manager.accounts.AccountListFragment.OnCreateAccountListener
import by.shatunov.manager.accounts.AccountListFragment.OnEditAccountListener
import by.shatunov.manager.transactions.TransactionFragment
import by.shatunov.manager.transactions.TransactionListFragment
import by.shatunov.manager.transactions.TransactionListFragment.OnTransactionListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),
        OnCreateAccountListener,
        OnEditAccountListener,
        OnTransactionListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        supportFragmentManager.findFragmentById(container.id) ?: supportFragmentManager
                .beginTransaction()
                .add(container.id, TransactionListFragment.newInstance())
                .commit()

        bottom_navigation.setOnNavigationItemSelectedListener({
            when (it.itemId) {
                R.id.menu_transactions -> {
                    changeFragment(TransactionListFragment.newInstance())
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.menu_accounts -> {
                    changeFragment(AccountListFragment.newInstance())
                    return@setOnNavigationItemSelectedListener true
                }

                else -> return@setOnNavigationItemSelectedListener false
            }
        })
    }

    override fun onCreateAccount() {
        changeFragment(AccountCreateFragment.newInstance(), true)
    }

    override fun onEditAccount(id: Int) {
        changeFragment(AccountEditFragment.newInstance(id), true)
    }

    override fun onTransactionCreate(id: Int?) {
        changeFragment(TransactionFragment.newInstance(id), true)
    }

    private fun changeFragment(instance: Fragment, addToBackStack: Boolean = false) {
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
                .replace(container.id, instance)

        if (addToBackStack) {
            transaction.addToBackStack("backstack")
        }

        transaction.commit()
    }
}
