package com.android.doctorce.feature_book_appointment.presentation.util

import kotlinx.coroutines.flow.Flow

interface ConnectivityObserver {

    fun observeNetworkState(): Flow<ConnectionStatus>

    enum class ConnectionStatus {
        Connected, Unavailable
    }
}