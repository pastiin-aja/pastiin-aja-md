package com.bangkit.pastiinaja.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.pastiinaja.data.remote.response.ListFraudItem
import com.bangkit.pastiinaja.databinding.ItemFraudBinding
import com.bumptech.glide.Glide

class MainDummyAdapter(private val listFraud: ArrayList<ListFraudItem>) : RecyclerView.Adapter<MainDummyAdapter.ListViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemFraudBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (text, uploadedAt, photoUrl) = listFraud[position]
        holder.binding.tvText.text = text
        holder.binding.tvDate.text = uploadedAt
        if (photoUrl == null) {
            holder.binding.ivFraud.visibility = View.GONE
        } else {
            Glide.with(holder.itemView)
                .load(photoUrl)
                .into(holder.binding.ivFraud)
        }
    }

    override fun getItemCount(): Int = listFraud.size

    class ListViewHolder(var binding: ItemFraudBinding) : RecyclerView.ViewHolder(binding.root)

    interface OnItemClickCallback {
        fun onItemClicked(data: ListFraudItem)
    }
}