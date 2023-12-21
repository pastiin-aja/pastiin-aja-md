package com.bangkit.pastiinaja.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bangkit.pastiinaja.R
import com.bangkit.pastiinaja.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
    }
}