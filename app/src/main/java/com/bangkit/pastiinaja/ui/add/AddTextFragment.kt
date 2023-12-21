package com.bangkit.pastiinaja.ui.add

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.bangkit.pastiinaja.databinding.FragmentAddTextBinding
import com.bangkit.pastiinaja.ui.ViewModelFactory
import com.bangkit.pastiinaja.ui.main.MainActivity

class AddTextFragment : Fragment() {

    private val viewModel by viewModels<AddViewModel> {
        ViewModelFactory.getInstance(requireActivity())
    }

    private lateinit var binding: FragmentAddTextBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddTextBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        viewModel.getSession().observe(viewLifecycleOwner) { userId ->
            binding.buttonCheck.setOnClickListener {
                val text = binding.inputText.text.toString()
                viewModel.postFraudByText(userId, text) { isSuccess ->
                    if (isSuccess) {
                        val intentToMain = Intent(requireContext(), MainActivity::class.java)
                        startActivity(intentToMain)
                    } else {
                        Log.e("AddTextFragment", "Failed to post fraud by text")
                    }
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

}