package com.bangkit.pastiinaja.ui.profile

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
import com.bangkit.pastiinaja.databinding.FragmentProfileBinding
import com.bangkit.pastiinaja.ui.ViewModelFactory
import com.bangkit.pastiinaja.ui.detail.DetailActivity
import com.bangkit.pastiinaja.ui.detail.DetailTextActivity
import com.bangkit.pastiinaja.ui.main.MainAdapter

class ProfileFragment : Fragment() {

    private val viewModel by viewModels<ProfileViewModel> {
        ViewModelFactory.getInstance(requireActivity())
    }

    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = LinearLayoutManager(context)
        binding.rvFrauds.layoutManager = layoutManager

        viewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        viewModel.userId.observe(viewLifecycleOwner) { userId ->
            viewModel.getMyFraud(userId)
            viewModel.listFraud.observe(viewLifecycleOwner) { fraudData ->
                setupData(fraudData)
            }
        }

        binding.logoutButton.setOnClickListener {
            viewModel.logout()
        }

    }

    private fun setupData(fraudData: List<FraudItem?>?) {

        val fraudAdapter = MainAdapter(fraudData)
        binding.rvFrauds.adapter = fraudAdapter

        fraudAdapter.setOnItemClickCallback(object: MainAdapter.OnItemClickCallback {
            override fun onItemClicked(data: FraudItem) {
                Log.d("MainActivity", "onClicked: ${data.fraudId}")
                if (data.imageLink == null) {
                    val intentToDetail = Intent(requireContext(), DetailTextActivity::class.java)
                    intentToDetail.putExtra("DATA", data.fraudId)
                    startActivity(intentToDetail)
                } else {
                    val intentToDetail = Intent(requireContext(), DetailActivity::class.java)
                    intentToDetail.putExtra("DATA", data.fraudId)
                    startActivity(intentToDetail)
                }
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