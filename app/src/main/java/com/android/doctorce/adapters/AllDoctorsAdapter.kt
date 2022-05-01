package com.android.doctorce.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.android.doctorce.R
import com.android.doctorce.databinding.RvAllDoctorsBinding
import com.android.doctorce.ui_models.TopDoctor

class AllDoctorsAdapter(
    private val doctors: List<TopDoctor>
): RecyclerView.Adapter<AllDoctorsAdapter.AllDoctorViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllDoctorViewHolder {
        val binding: RvAllDoctorsBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.rv_all_doctors,
            parent,
            false
        )

        return AllDoctorViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AllDoctorViewHolder, position: Int) {
        val doctor = doctors[position]
        holder.bindItem(doctor)
    }

    override fun getItemCount(): Int = doctors.size

    inner class AllDoctorViewHolder(private val binding: RvAllDoctorsBinding): RecyclerView.ViewHolder(binding.root) {

        fun bindItem(doctor: TopDoctor) {
            binding.tvDoctorName.text = doctor.name
            binding.tvDoctorSpeciality.text = doctor.speciality
            binding.tvRatings.text = doctor.rating.toString()

        }
    }
}