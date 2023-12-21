package com.bangkit.pastiinaja.ui.register

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
                viewModel.register(name.toString(), email.toString(), password.toString()) { isSuccess, message ->
                    if (isSuccess) {
                        val intentToLogin = Intent(this, LoginActivity::class.java)
                        startActivity(intentToLogin)
                    } else {
                        binding.nameEditTextLogin.text?.clear()
                        binding.emailEditTextLogin.text?.clear()
                        binding.passwordEditTextLogin.text?.clear()
                        Toast.makeText(this, "Register Gagal", Toast.LENGTH_SHORT).show()
                        Log.e("Register", message)
                    }
                }
            }
        }

        binding.txtLogin.setOnClickListener {
            val intentToLogin = Intent(this, LoginActivity::class.java)
            startActivity(intentToLogin)
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