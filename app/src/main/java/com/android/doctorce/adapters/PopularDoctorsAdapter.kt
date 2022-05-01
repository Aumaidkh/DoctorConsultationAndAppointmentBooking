package com.android.doctorce.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.android.doctorce.R
import com.android.doctorce.databinding.RvPopularDoctorBinding
import com.android.doctorce.ui_models.TopDoctor

class PopularDoctorsAdapter(
    private val doctors: List<TopDoctor>
): RecyclerView.Adapter<PopularDoctorsAdapter.PopularDoctorViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularDoctorViewHolder {
        val binding: RvPopularDoctorBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.rv_popular_doctor,
            parent,
            false
        )

        return PopularDoctorViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PopularDoctorViewHolder, position: Int) {
        val doctor = doctors[position]
        holder.bindItem(doctor)
    }

    override fun getItemCount(): Int = doctors.size

    inner class PopularDoctorViewHolder(private val binding: RvPopularDoctorBinding): RecyclerView.ViewHolder(binding.root) {

        fun bindItem(doctor: TopDoctor) {
            binding.tvDoctorName.text = doctor.name
            binding.tvDoctorSpeciality.text = doctor.speciality
            binding.rbDoctorRating.rating = (doctor.rating)

        }
    }
}