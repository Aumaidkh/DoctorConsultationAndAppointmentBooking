package com.android.doctorce.feature_book_appointment.presentation.ui.doctors.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.doctorce.R
import com.android.doctorce.databinding.RvDoctorListItemBinding
import com.android.doctorce.feature_book_appointment.domain.model.DoctorModel
import com.bumptech.glide.Glide

class DoctorAdapter(
    private val onDoctorClick: (doctor: DoctorModel) -> Unit
) : ListAdapter<DoctorModel, DoctorAdapter.DoctorViewHolder>(DIFF_UTIL_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorViewHolder {
        return DoctorViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.rv_doctor_list_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: DoctorViewHolder, position: Int) {
        val doctor = getItem(position)
        holder.bind(doctor)
    }

    inner class DoctorViewHolder(private val binding: RvDoctorListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(doctor: DoctorModel) {

            binding.apply {
                Glide.with(itemView.context)
                    .load(doctor.imageUrl)
                    .into(ivDoctorImage)

                tvDoctorName.text = doctor.name
                tvDoctorSpeciality.text = doctor.speciality

            }

            itemView.setOnClickListener {
                onDoctorClick.invoke(doctor)
            }
        }

    }

    companion object {
        val DIFF_UTIL_CALLBACK = object : DiffUtil.ItemCallback<DoctorModel>() {
            override fun areItemsTheSame(
                oldItem: DoctorModel,
                newItem: DoctorModel
            ): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(
                oldItem: DoctorModel,
                newItem: DoctorModel
            ): Boolean {
                return newItem.name == oldItem.name
            }
        }
    }
}