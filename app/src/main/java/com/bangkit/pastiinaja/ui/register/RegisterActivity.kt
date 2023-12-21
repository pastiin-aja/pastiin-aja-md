package com.bangkit.pastiinaja.ui.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.bangkit.pastiinaja.R
import com.bangkit.pastiinaja.databinding.ActivityRegisterBinding
import com.bangkit.pastiinaja.ui.ViewModelFactory
import com.bangkit.pastiinaja.ui.login.LoginActivity
import com.bangkit.pastiinaja.ui.main.MainActivity

class RegisterActivity : AppCompatActivity() {

    private val viewModel by viewModels<RegisterViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.isLoading.observe(this) {
            showLoading(it)
        }

        binding.signupButton.setOnClickListener {
            val email = binding.emailEditTextLogin.text ?: ""
            val password = binding.passwordEditTextLogin.text ?: ""
            val name = binding.nameEditTextLogin.text ?: ""

            if (email.isNotEmpty() && password.isNotEmpty() && name.isNotEmpty()) {
                viewModel.register(name.toString(), email.toString(), password.toString()) { isSuccess ->
                    if (isSuccess) {
                        val intentToLogin = Intent(this, LoginActivity::class.java)
                        startActivity(intentToLogin)
                    }
                }
            }
        }

        binding.txtLogin.setOnClickListener {
            val intentToLogin = Intent(this, LoginActivity::class.java)
            startActivity(intentToLogin)
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