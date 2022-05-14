package com.android.doctorce.feature_book_appointment.data.util

sealed class Resource<T>(
    val data: T? = null,
    val message: String? = null,
    val isLoading: Boolean? = null
) {
    class Loading<T>(isLoading: Boolean): Resource<T>(null,null,isLoading)
    class Success<T>(data: T):Resource<T>(data)
    class Error<T>(message: String): Resource<T>(null,message)
}