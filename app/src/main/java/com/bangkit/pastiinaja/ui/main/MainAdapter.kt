package com.bangkit.pastiinaja.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.pastiinaja.data.remote.response.FraudItem
import com.bangkit.pastiinaja.databinding.ItemFraudBinding
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.util.Locale

class MainAdapter(private val listFraud: List<FraudItem?>?) : RecyclerView.Adapter<MainAdapter.ListViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemFraudBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val fraud = listFraud?.get(position)
        holder.binding.tvText.text = fraud?.textInput
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd MMM yyyy", Locale("in", "ID"))
        val date = inputFormat.parse(fraud?.createdAt)
        holder.binding.tvDate.text = outputFormat.format(date)
        if (fraud?.imageLink == null) {
            holder.binding.ivFraud.visibility = View.GONE
        } else {
            Glide.with(holder.itemView)
                .load(fraud.imageLink)
                .into(holder.binding.ivFraud)
        }
    }

    override fun getItemCount(): Int = listFraud?.size ?: 0

    class ListViewHolder(var binding: ItemFraudBinding) : RecyclerView.ViewHolder(binding.root)

    interface OnItemClickCallback {
        fun onItemClicked(data: FraudItem)
    }
}