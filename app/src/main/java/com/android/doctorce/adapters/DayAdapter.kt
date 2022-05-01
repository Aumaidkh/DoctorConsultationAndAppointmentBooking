package com.android.doctorce.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.doctorce.R
import com.android.doctorce.ui_models.Day

class DayAdapter(
    private val days: List<Day>
) : RecyclerView.Adapter<DayAdapter.DayViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_day_model,parent,false)
        return DayViewHolder(view)
    }

    override fun onBindViewHolder(holder: DayViewHolder, position: Int) {
       val day = days[position]
        holder.bind(day)
    }

    override fun getItemCount(): Int = days.size

    inner class DayViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        fun bind(day: Day) {
            itemView.findViewById<TextView>(R.id.rv_day_day_name).text = day.day
            itemView.findViewById<TextView>(R.id.rv_day_date).text = day.date.toString()
        }

    }
}