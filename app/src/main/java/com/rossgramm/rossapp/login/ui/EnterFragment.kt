package com.rossgramm.rossapp.login.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.rossgramm.rossapp.MainActivity
import com.rossgramm.rossapp.databinding.FragmentEnterBinding

class EnterFragment : Fragment() {

    private var _binding: FragmentEnterBinding? = null
    private val binding get() = _binding!!

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
            launchSignInFlow()
        }
        return root
    }

    private fun launchSignInFlow() {
        val homePage = Intent(context, MainActivity::class.java)
        startActivity(homePage)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}
