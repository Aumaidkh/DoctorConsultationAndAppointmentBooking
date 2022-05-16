package com.android.doctorce.feature_book_appointment.domain.util

sealed class DoctorOrder(
    val orderType: OrderType
) {
    class Ratings(orderType: OrderType): DoctorOrder(orderType)
    class Fee(orderType: OrderType): DoctorOrder(orderType)
    class Experience(orderType: OrderType): DoctorOrder(orderType)
}
