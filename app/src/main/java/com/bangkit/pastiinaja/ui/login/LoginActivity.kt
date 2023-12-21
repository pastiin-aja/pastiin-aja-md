package com.bangkit.pastiinaja.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.bangkit.pastiinaja.R
import com.bangkit.pastiinaja.databinding.ActivityLoginBinding
import com.bangkit.pastiinaja.ui.ViewModelFactory
import com.bangkit.pastiinaja.ui.main.MainActivity
import com.bangkit.pastiinaja.ui.register.RegisterActivity

class LoginActivity : AppCompatActivity() {

    private val viewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.isLoading.observe(this) {
            showLoading(it)
        }

        binding.txtSignup.setOnClickListener {
            val intentToRegister = Intent(this, RegisterActivity::class.java)
            startActivity(intentToRegister)
        }

        binding.loginButton.setOnClickListener {
            val email = binding.emailEditTextLogin.text ?: ""
            val password = binding.passwordEditTextLogin.text ?: ""

            if (email.isNotEmpty() && password.isNotEmpty()) {
                viewModel.login(email.toString(), password.toString()) { isSuccess ->
                    if (isSuccess) {
                        val intentToMain = Intent(this, MainActivity::class.java)
                        startActivity(intentToMain)
                    }
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}