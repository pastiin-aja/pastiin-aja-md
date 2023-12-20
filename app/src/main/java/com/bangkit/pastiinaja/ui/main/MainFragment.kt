package com.bangkit.pastiinaja.ui.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.pastiinaja.R
import com.bangkit.pastiinaja.data.remote.response.ListFraudItem
import com.bangkit.pastiinaja.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding

    private val dummyList = ArrayList<ListFraudItem>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMainBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = LinearLayoutManager(context)
        binding.rvFrauds.layoutManager = layoutManager

        dummyList.addAll(getDummyData())
        setupData(dummyList)
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