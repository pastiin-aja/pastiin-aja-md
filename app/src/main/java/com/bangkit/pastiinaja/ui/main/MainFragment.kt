package com.bangkit.pastiinaja.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.pastiinaja.data.remote.response.FraudItem
import com.bangkit.pastiinaja.databinding.FragmentMainBinding
import com.bangkit.pastiinaja.ui.ViewModelFactory
import com.bangkit.pastiinaja.ui.add.AddActivity

class MainFragment : Fragment() {

    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(requireActivity())
    }

    private lateinit var binding: FragmentMainBinding

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

        viewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        viewModel.listFraud.observe(viewLifecycleOwner) { fraudData ->
            setupData(fraudData)
        }

        binding.fabAdd.setOnClickListener {
            val intentToAdd = Intent(requireContext(), AddActivity::class.java)
            startActivity(intentToAdd)
        }

    }

    private fun setupData(fraudData: List<FraudItem?>?) {

        val fraudAdapter = MainAdapter(fraudData)
        binding.rvFrauds.adapter = fraudAdapter

        fraudAdapter.setOnItemClickCallback(object: MainAdapter.OnItemClickCallback {
            override fun onItemClicked(data: FraudItem) {
                Log.d("MainActivity", "onClicked: ${data.fraudId}")
//                val intentToDetail = Intent(this@MainActivity, DetailActivity::class.java)
//                intentToDetail.putExtra(DetailActivity.DATA, data)
//                startActivity(intentToDetail)
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}