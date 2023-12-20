package com.bangkit.pastiinaja.ui.main

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowInsets
import android.view.WindowManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.pastiinaja.R
import com.bangkit.pastiinaja.data.remote.response.ListFraudItem
import com.bangkit.pastiinaja.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val dummyList = ArrayList<ListFraudItem>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.rvFrauds.layoutManager = layoutManager

        setupView()

        dummyList.addAll(getDummyData())
        setupData(dummyList)
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

    private fun setupData(fraudData: ArrayList<ListFraudItem>) {
//        val fraudAdapter = MainAdapter()
//        fraudAdapter.submitData(lifecycle, fraudData)
//        binding.rvFrauds.adapter = fraudAdapter.withLoadStateFooter(
//            footer = LoadingStateAdapter {
//                fraudAdapter.retry()
//            }
//        )

        val fraudAdapter = MainDummyAdapter(fraudData)
        binding.rvFrauds.adapter = fraudAdapter

        fraudAdapter.setOnItemClickCallback(object: MainDummyAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ListFraudItem) {
                Log.d("MainActivity", "onClicked: ${data.id}")
//                val intentToDetail = Intent(this@MainActivity, DetailActivity::class.java)
//                intentToDetail.putExtra(DetailActivity.DATA, data)
//                startActivity(intentToDetail)
            }
        })
    }

    private fun getDummyData(): ArrayList<ListFraudItem> {
        val dataText = resources.getStringArray(R.array.dummy_text)
        val dataDate = resources.getStringArray(R.array.dummy_uploaded_at)
        val dataPhoto = resources.getStringArray(R.array.dummy_photo_url)

        val listFraud = ArrayList<ListFraudItem>()

        for (i in dataText.indices) {
            val fraud = ListFraudItem(dataText[i], dataDate[i], dataPhoto[i], i.toString())
            listFraud.add(fraud)
        }

        return listFraud
    }
}