package com.bangkit.pastiinaja.ui.add

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import com.bangkit.pastiinaja.R
import com.bangkit.pastiinaja.databinding.ActivityAddBinding

class AddActivity : AppCompatActivity(), AddMainFragment.OnButtonClickListener {

    private lateinit var binding: ActivityAddBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()

        val initialFragment = AddMainFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, initialFragment)
            .commit()
    }

    override fun onInputTextButtonClick() {
        val newTextFragment = AddTextFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, newTextFragment)
            .commit()
    }

    override fun onInputImageButtonClick() {
        val newImageFragment = AddImageFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, newImageFragment)
            .commit()
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