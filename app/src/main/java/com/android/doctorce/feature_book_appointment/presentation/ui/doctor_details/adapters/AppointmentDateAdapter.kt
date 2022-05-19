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

class AppointmentDateAdapter: ListAdapter<AppointmentDateUiState,AppointmentDateAdapter.AppointmentDateViewHolder>(
    DIFF_UTIL_CALLBACK){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppointmentDateViewHolder {
        return from(parent)
    }

    override fun onBindViewHolder(holder: AppointmentDateViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    class AppointmentDateViewHolder(private val binding: DoctorChipLayoutBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(item: AppointmentDateUiState){
            binding.tvDay.text = item.day
            binding.tvDate.text = item.date
            if (item.isSelected){
                binding.apply {
                    root.setBackgroundResource(R.drawable.primary_chip_background)
                    tvDate.setTextColor(itemView.context.getColor(R.color.white))
                    tvDay.setTextColor(itemView.context.getColor(R.color.white))
                }
            }

            itemView.setOnClickListener {
                item.isSelected = !item.isSelected
                bind(item)
            }
        }

    }

    companion object {

        val DIFF_UTIL_CALLBACK = object : DiffUtil.ItemCallback<AppointmentDateUiState>(){
            override fun areItemsTheSame(
                oldItem: AppointmentDateUiState,
                newItem: AppointmentDateUiState
            ) = oldItem === newItem

            override fun areContentsTheSame(
                oldItem: AppointmentDateUiState,
                newItem: AppointmentDateUiState
            ) = oldItem.day == newItem.day
        }
        fun from(parent: ViewGroup): AppointmentDateViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = DataBindingUtil.inflate<DoctorChipLayoutBinding>(inflater, R.layout.doctor_chip_layout,parent,false)
            return AppointmentDateViewHolder(binding)
        }
    }
}