package com.bangkit.pastiinaja.ui.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.pastiinaja.R
import com.bangkit.pastiinaja.data.remote.response.FraudItem
import com.bangkit.pastiinaja.databinding.FragmentMainBinding
import com.bangkit.pastiinaja.ui.ViewModelFactory

class MainFragment : Fragment() {

    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(requireActivity())
    }

    private lateinit var binding: FragmentMainBinding

    private val dummyList = ArrayList<FraudItem>()

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

//        dummyList.addAll(getDummyData())
//        setupData(dummyList)

        viewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        viewModel.listFraud.observe(viewLifecycleOwner) { fraudData ->
            setupData(fraudData)
        }


    }

    private fun setupData(fraudData: List<FraudItem?>?) {
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
            override fun onItemClicked(data: FraudItem) {
                Log.d("MainActivity", "onClicked: ${data.fraudId}")
//                val intentToDetail = Intent(this@MainActivity, DetailActivity::class.java)
//                intentToDetail.putExtra(DetailActivity.DATA, data)
//                startActivity(intentToDetail)
            }
        })
    }
    private fun getDummyData(): ArrayList<FraudItem> {
        val dataText = resources.getStringArray(R.array.dummy_text)
        val dataDate = resources.getStringArray(R.array.dummy_uploaded_at)
        val dataPhoto = resources.getStringArray(R.array.dummy_photo_url)

        val listFraud = ArrayList<FraudItem>()

        for (i in dataText.indices) {
            val fraud = FraudItem(dataText[i], dataDate[i], dataPhoto[i], i.toString())
            listFraud.add(fraud)
        }

        return listFraud
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}