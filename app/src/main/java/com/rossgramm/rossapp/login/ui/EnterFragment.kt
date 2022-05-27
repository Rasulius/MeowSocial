package com.rossgramm.rossapp.login.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.rossgramm.rossapp.R
import com.rossgramm.rossapp.base.ui.*
import com.rossgramm.rossapp.MainActivity
import com.rossgramm.rossapp.databinding.FragmentEnterBinding

class EnterFragment : Fragment() {

    private var _binding: FragmentEnterBinding? = null
    private val binding get() = _binding!!

    private val viewModel: EnterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentEnterBinding.inflate(inflater, container, false)
        val root: View = binding.root
        // Пока временная обработка для перехода на следующую аткивность
        //

        binding.loginButton.setOnClickListener {
            goToMainScreen()
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(binding) {

            viewModel.errorMessageLiveData.observe(viewLifecycleOwner) {
                if (it) {
                    Snackbar.make(binding.loginButton, "Error", Snackbar.LENGTH_LONG).show()
                }
            }

            viewModel.userInputErrorLiveData.observe(viewLifecycleOwner) {
                username.setError(it)
            }

            viewModel.passInputErrorLiveData.observe(viewLifecycleOwner) {
                password.setError(it)
            }


            viewModel.buttonLiveData.observe(viewLifecycleOwner) { state ->
                loginButton.setShowProgress(state == ButtonState.LOADING)
                when (state) {
                    ButtonState.ENABLE -> loginButton.isEnabled = true
                    ButtonState.DISABLE -> loginButton.isEnabled = false
                }
            }

            viewModel.toHomeScreenLiveData.observe(viewLifecycleOwner){
                goToMainScreen()
            }

            val afterTextChanged: (String) -> Unit = {
                viewModel.inputDataChanged(
                    usernameEt.text.toString(),
                    passwordEt.text.toString()
                )
            }

            usernameEt.afterTextChanged { afterTextChanged.invoke(it) }
            passwordEt.afterTextChanged { afterTextChanged.invoke(it) }

            loginButton.setOnClickListener {
                val login = usernameEt.text.toString()
                val password = passwordEt.text.toString()
                viewModel.login(login = login, pass = password)
            }
            signup.setOnClickListener { findNavController().navigate(R.id.signup_screen) }
        }
    }

    private fun goToMainScreen() {
        val homePage = Intent(context, MainActivity::class.java)
        startActivity(homePage)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

private fun TextInputLayout.setError(it: InputError?) {
    this.isErrorEnabled = it.text() != null
    this.error = it.text()
}
