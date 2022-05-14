package com.android.doctorce.feature_book_appointment.presentation.ui.home_and_key_features.fragments.my_appointments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.doctorce.R
import com.android.doctorce.databinding.RvAppointmentItemBinding
import com.android.doctorce.feature_book_appointment.domain.model.AppointmentModel
import com.bumptech.glide.Glide

class AppointmentAdapter: ListAdapter<AppointmentModel,AppointmentAdapter.AppointmentViewHolder>(DIFF_UTIL_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppointmentViewHolder {
        return from(parent)
    }

    override fun onBindViewHolder(holder: AppointmentViewHolder, position: Int) {
        val appointmentItem = getItem(position)
        holder.bind(appointmentItem)
    }

    class AppointmentViewHolder(private val binding: RvAppointmentItemBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(appointment: AppointmentModel){

            binding.apply {
                Glide.with(itemView.context)
                    .load(appointment.imageUrl)
                    .into(ivDoctor)

                tvDoctorName.text = appointment.doctorName
                tvDoctorSpeciality.text = appointment.speciality
                tvAppointmentDate.text = appointment.appointmentDate.toString()
                statusChip.text = appointment.status
            }
        }

    }

    companion object {
        val DIFF_UTIL_CALLBACK = object : DiffUtil.ItemCallback<AppointmentModel>(){
            override fun areItemsTheSame(
                oldItem: AppointmentModel,
                newItem: AppointmentModel
            ): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(
                oldItem: AppointmentModel,
                newItem: AppointmentModel
            ): Boolean {
                return newItem.appointmentDate == oldItem.appointmentDate
            }
        }

        fun from(parent: ViewGroup): AppointmentViewHolder{
            val inflater = LayoutInflater.from(parent.context)
            val binding = DataBindingUtil.inflate<RvAppointmentItemBinding>(inflater, R.layout.rv_appointment_item,parent,false)
            return AppointmentViewHolder(binding)
        }
    }
}