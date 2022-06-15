package com.rossgramm.rossapp.login.ui


import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.rossgramm.rossapp.MainActivity
import com.rossgramm.rossapp.databinding.ActivityLoginBinding
import com.rossgramm.rossapp.databinding.FragmentRegistrationBinding
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.rossgramm.rossapp.R
import com.rossgramm.rossapp.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var binding: ActivityLoginBinding
    private lateinit var loginBinding: FragmentRegistrationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        loginBinding = FragmentRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val login = loginBinding.loginButton


        /*val username = binding.username
        val usernameEt = binding.usernameEt
        val password = binding.password
        val passwordEt = binding.passwordEt
        val login = binding.loginButton
        val loading = binding.loading
        */

        loginViewModel = ViewModelProvider(this, LoginViewModelFactory())
            .get(LoginViewModel::class.java)
        connectTabsWithViewPager(this)




/*
        loginViewModel.loginFormState.observe(this@LoginActivity, Observer {
            val loginState = it ?: return@Observer

            // disable login button unless both username / password is valid
            login.isEnabled = loginState.isDataValid

            if (loginState.usernameError != null) {
                username.error = getString(loginState.usernameError)
            }
            if (loginState.passwordError != null) {
                password.error = getString(loginState.passwordError)
            }
        })*/

       /* loginViewModel.loginResult.observe(this@LoginActivity, Observer {
            val loginResult = it ?: return@Observer

            loading.visibility = View.GONE
            if (loginResult.error != null) {
                showLoginFailed(loginResult.error)
            }
            if (loginResult.success != null) {
                updateUiWithUser(loginResult.success)
            }
            setResult(Activity.RESULT_OK)

            //Complete and destroy login activity once successful
            finish()
        })*/

       /* usernameEt.afterTextChanged {
            loginViewModel.loginDataChanged(
                usernameEt.text.toString(),
                passwordEt.text.toString()
            )
        }

        passwordEt.apply {
            afterTextChanged {
                loginViewModel.loginDataChanged(
                    usernameEt.text.toString(),
                    passwordEt.text.toString()
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        loginViewModel.login(
                            usernameEt.text.toString(),
                            passwordEt.text.toString()
                        )
                }
                false
            }


       // }

        */
       login.setOnClickListener {
                //loading.visibility = View.VISIBLE
                //loginViewModel.login(usernameEt.text.toString(), passwordEt.text.toString())
           val homeActivity = Intent(this, MainActivity::class.java)
           ContextCompat.startActivity(this, homeActivity, null)
       }


    }

    private fun connectTabsWithViewPager(context: Context) {
        binding.viewpager.adapter = LoginTabsAdapter(this)
        val tabNames = listOf(context.getString(R.string.tab_enter), context.getString(R.string.tab_registration))
        TabLayoutMediator(binding.tabLayout, binding.viewpager) { tab, position ->
            tab.text = tabNames[position]
        }.attach()
    }

        val navController = (supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment)
            .navController


        loginViewModel = ViewModelProvider(this, LoginViewModelFactory())
            .get(LoginViewModel::class.java)



    }

}
