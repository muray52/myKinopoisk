package com.vostrikov.mykinopoisk.presentation.login

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.vostrikov.mykinopoisk.databinding.ActivityLoginBinding
import com.vostrikov.mykinopoisk.domain.model.LoginEntity
import com.vostrikov.mykinopoisk.presentation.list_films.ListFilmsActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var viewModel: LoginViewModel

    private var _binding: ActivityLoginBinding? = null
    private val binding: ActivityLoginBinding
        get() = _binding ?: throw RuntimeException("ActivityLoginBinding is null")
    private var toastMessage: Toast? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLoginBinding.inflate((layoutInflater))
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]

        setupObservers()
        setupListeners()
    }

    private fun setupObservers() {
        viewModel.successLogin.observe(this) {
            if (it) {
                startListFilmsActivity()
            }
        }
        viewModel.errorResponseMessage.observe(this){
            toastObserve(it)
        }
    }

    private fun toastObserve(message: String){
        toastMessage?.cancel()
        toastMessage = Toast.makeText(this, message, Toast.LENGTH_SHORT)
        toastMessage?.show()
    }

    private fun setupListeners() {
        binding.buttonGuestSignIn.setOnClickListener {
            viewModel.signInGuest()
        }
        binding.buttonSignIn.setOnClickListener {
            viewModel.signIn(
                LoginEntity(
                    binding.editTextLogin.text.toString(),
                    binding.editTextPassword.text.toString()
                )
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    private fun startListFilmsActivity() {
        val intent = ListFilmsActivity.newIntent(this)
        startActivity(intent)
    }
}