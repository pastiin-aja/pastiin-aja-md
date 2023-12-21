package com.bangkit.pastiinaja.ui.add

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bangkit.pastiinaja.R
import com.bangkit.pastiinaja.databinding.FragmentAddMainBinding

class AddMainFragment : Fragment() {

    private lateinit var binding: FragmentAddMainBinding

    private var buttonClickListener: OnButtonClickListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentAddMainBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    interface OnButtonClickListener {
        fun onInputTextButtonClick()
        fun onInputImageButtonClick()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnButtonClickListener) {
            buttonClickListener = context
        } else {
            throw RuntimeException("$context must implement OnButtonClickListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        buttonClickListener = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonInputText.setOnClickListener {
            buttonClickListener?.onInputTextButtonClick()
        }
        binding.buttonInputImage.setOnClickListener {
            buttonClickListener?.onInputImageButtonClick()
        }
    }

}