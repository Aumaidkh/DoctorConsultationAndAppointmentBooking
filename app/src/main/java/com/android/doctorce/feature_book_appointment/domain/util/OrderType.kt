package com.android.doctorce.feature_book_appointment.domain.util

sealed class OrderType{
    object Ascending: OrderType()
    object Descending: OrderType()
}
