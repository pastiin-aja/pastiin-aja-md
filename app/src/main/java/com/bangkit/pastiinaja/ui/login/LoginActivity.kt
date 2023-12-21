package com.bangkit.pastiinaja.ui.login

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
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
                viewModel.login(email.toString(), password.toString()) { isSuccess, message ->
                    if (isSuccess) {
                        val intentToMain = Intent(this, MainActivity::class.java)
                        startActivity(intentToMain)
                    } else {
                        Toast.makeText(this, "Login Gagal", Toast.LENGTH_SHORT).show()
                        binding.emailEditTextLogin.text?.clear()
                        binding.passwordEditTextLogin.text?.clear()
                        Log.e("Login", message)
                    }
                }
            }
        }

        setupView()
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }
}