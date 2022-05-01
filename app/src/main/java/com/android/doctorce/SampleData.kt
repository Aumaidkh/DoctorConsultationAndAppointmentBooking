package com.android.doctorce

import com.android.doctorce.ui_models.TopDoctor

class SampleData {


    companion object {

        fun getPopularDoctors(): List<TopDoctor> {

            return listOf(
                TopDoctor("Dr. Arshid ","Neurologist",4.5f,"a"),
                TopDoctor("Dr. Soban ","Gynecologist",5.0f,"a"),
                TopDoctor("Dr. Ketty ","Phsychatriast",3.5f,"a"),
                TopDoctor("Dr. Lynda ","Surgeon",2.5f,"a"),
                TopDoctor("Dr. Harsh ","Nutritionist",4.5f,"a"),
            )
        }

    }
}