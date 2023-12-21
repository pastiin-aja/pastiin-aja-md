package com.bangkit.pastiinaja.ui.result

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.viewModels
import com.bangkit.pastiinaja.R
import com.bangkit.pastiinaja.data.remote.response.FraudPostResponse
import com.bangkit.pastiinaja.databinding.ActivityDetailBinding
import com.bangkit.pastiinaja.databinding.ActivityResultBinding
import com.bangkit.pastiinaja.ui.ViewModelFactory
import com.bangkit.pastiinaja.ui.detail.DetailViewModel
import com.bangkit.pastiinaja.ui.main.MainActivity
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.util.Locale

class ResultActivity : AppCompatActivity() {

    private val viewModel by viewModels<ResultViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var binding: ActivityResultBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()

        val fraudId = intent.getStringExtra("DATA") ?: ""

        viewModel.isLoading.observe(this) {
            showLoading(it)
        }

        viewModel.getResult(fraudId)

        viewModel.detailResult.observe(this) { data ->
            setDetail(data)
        }

        binding.buttonBack.setOnClickListener {
            val intentToMain = Intent(this, MainActivity::class.java)
            startActivity(intentToMain)
        }

        binding.buttonShare.setOnClickListener {
            viewModel.shareFraud(fraudId)
            val intentToMain = Intent(this, MainActivity::class.java)
            startActivity(intentToMain)
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

    private fun setDetail(fraud: FraudPostResponse) {
        binding.tvTranscript.text = fraud.data?.textInput

        val result = fraud.data?.result
        if (result is Double) {
            val percentage = String.format(Locale.getDefault(), "%.2f%%", result * 100)
            binding.tvPercentage.text = percentage
        }
        Glide.with(this)
            .load(fraud.data?.imageLink)
            .into(binding.ivPreview)
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}