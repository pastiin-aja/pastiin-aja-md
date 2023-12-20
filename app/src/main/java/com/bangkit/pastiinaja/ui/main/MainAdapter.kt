package com.bangkit.pastiinaja.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.pastiinaja.data.remote.response.ListFraudItem
import com.bangkit.pastiinaja.databinding.ItemFraudBinding
import com.bumptech.glide.Glide

class MainAdapter: PagingDataAdapter<ListFraudItem, MainAdapter.MyViewHolder>(DIFF_CALLBACK) {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val fraud = getItem(position)
        if (fraud != null) {
            holder.bind(fraud)
            holder.itemView.setOnClickListener {
                onItemClickCallback.onItemClicked(fraud)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemFraudBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    class MyViewHolder(val binding: ItemFraudBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(fraud: ListFraudItem) {
            binding.tvText.text = fraud.text
            binding.tvDate.text = fraud.uploadedAt
            if (fraud.photoUrl == null) {
                binding.ivFraud.visibility = View.GONE
            } else {
                Glide.with(itemView)
                    .load(fraud.photoUrl)
                    .into(binding.ivFraud)
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: ListFraudItem)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListFraudItem>() {
            override fun areContentsTheSame(oldItem: ListFraudItem, newItem: ListFraudItem): Boolean {
                return oldItem == newItem
            }

            override fun areItemsTheSame(oldItem: ListFraudItem, newItem: ListFraudItem): Boolean {
                return oldItem == newItem
            }
        }
    }

}