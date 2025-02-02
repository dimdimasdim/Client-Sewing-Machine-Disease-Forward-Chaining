package com.dimas.networkexercise.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dimas.networkexercise.databinding.ItemDiseaseBinding
import com.dimas.networkexercise.domain.model.MachineDisease

class DiseaseAdapter(
    private val items: MutableList<MachineDisease>
): RecyclerView.Adapter<DiseaseAdapter.MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemDiseaseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(items[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addAll(data: List<MachineDisease>) {
        items.addAll(data)
        notifyDataSetChanged()
    }

    inner class MovieViewHolder(private val binding: ItemDiseaseBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(data: MachineDisease) {
            with(binding) {
                textCode.text = data.code
                textDesc.text = data.desc
            }
        }

    }

    fun items(): MutableList<MachineDisease> = items

    @SuppressLint("NotifyDataSetChanged")
    fun clear() {
        items.clear()
        notifyDataSetChanged()
    }
}