package com.android.doctorce.feature_book_appointment.presentation.ui.doctor_details.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.doctorce.R
import com.android.doctorce.databinding.DoctorChipLayoutBinding
import com.android.doctorce.feature_book_appointment.presentation.ui.doctor_details.AppointmentDateUiState

class AppointmentDateAdapter :
    RecyclerView.Adapter<AppointmentDateAdapter.AppointmentDateViewHolder>() {

    var items = emptyList<AppointmentDateUiState>()
    var selectedItem: AppointmentDateUiState? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppointmentDateViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<DoctorChipLayoutBinding>(
            inflater,
            R.layout.doctor_chip_layout,
            parent,
            false
        )
        return AppointmentDateViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AppointmentDateViewHolder, position: Int) {
        val currentItem = items[position]
        holder.bind(currentItem)
    }

    private fun deselectTheRestElements() {
        items.forEach { item ->
            item.isSelected = false
        }
    }

    inner class AppointmentDateViewHolder(private val binding: DoctorChipLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: AppointmentDateUiState) {
            binding.tvDay.text = item.day
            binding.tvDate.text = item.date
            if (item.isSelected) {
                //   deselectTheRestElements()
                binding.apply {
                    root.setBackgroundResource(R.drawable.primary_chip_background)
                    tvDate.setTextColor(itemView.context.getColor(R.color.white))
                    tvDay.setTextColor(itemView.context.getColor(R.color.white))
                }
            } else {
                binding.apply {
                    item.isSelected = false
                    root.setBackgroundResource(R.drawable.white_chip_background)
                    tvDate.setTextColor(itemView.context.getColor(R.color.primary_color))
                    tvDay.setTextColor(itemView.context.getColor(R.color.primary_color))
                }
            }

            itemView.setOnClickListener {
                deselectTheRestElements()
                selectedItem = item
                item.isSelected = !item.isSelected
                notifyDataSetChanged()
            }
        }

    }

    override fun getItemCount(): Int = items.size

    fun submitList(items: List<AppointmentDateUiState>) {
        this.items = items
    }
}