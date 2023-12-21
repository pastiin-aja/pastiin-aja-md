package com.bangkit.pastiinaja.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bangkit.pastiinaja.R
import com.bangkit.pastiinaja.databinding.ActivityDetailTextBinding

class DetailTextActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailTextBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_text)
    }
}