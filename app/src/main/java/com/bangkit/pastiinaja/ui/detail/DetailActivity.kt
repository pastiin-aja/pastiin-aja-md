package com.bangkit.pastiinaja.ui.detail

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.viewModels
import com.bangkit.pastiinaja.R
import com.bangkit.pastiinaja.data.remote.response.FraudPostResponse
import com.bangkit.pastiinaja.data.remote.response.FraudResponse
import com.bangkit.pastiinaja.databinding.ActivityDetailBinding
import com.bangkit.pastiinaja.ui.ViewModelFactory
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.util.Locale

class DetailActivity : AppCompatActivity() {

    private val viewModel by viewModels<DetailViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()

        val fraudId = intent.getStringExtra("DATA") ?: ""

        viewModel.isLoading.observe(this) {
            showLoading(it)
        }

        viewModel.getDetail(fraudId)

        viewModel.detailStory.observe(this) { data ->
            setDetail(data)
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
        binding.tvDescription.text = fraud.data?.textInput

        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd MMM yyyy", Locale("in", "ID"))
        val date = inputFormat.parse(fraud.data?.createdAt)
        binding.tvCreatedDate.text = outputFormat.format(date)

        val result = fraud.data?.result
        if (result is Double) {
            val percentage = String.format(Locale.getDefault(), "%.2f%%", result * 100)
            binding.tvPercentageImage.text = percentage
        }
        Glide.with(this)
            .load(fraud.data?.imageLink)
            .into(binding.ivFraudImage)
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}