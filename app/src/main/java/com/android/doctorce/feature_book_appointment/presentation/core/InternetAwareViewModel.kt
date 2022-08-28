package com.android.doctorce.feature_book_appointment.presentation.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.doctorce.core.InternetConnectionChecker
import com.android.doctorce.feature_book_appointment.presentation.util.ConnectivityObserver
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InternetAwareViewModel @Inject constructor(
    private val connectivityObserver: ConnectivityObserver
) : ViewModel() {

    /**
     * connection status will be sent into this channel
     * */
    private val _connectionStatusChannel: Channel<ConnectivityObserver.ConnectionStatus> = Channel()
    val connectionStatusChannel = _connectionStatusChannel.receiveAsFlow()

    init {
        viewModelScope.launch {
            connectivityObserver.observeNetworkState().collect {
                delay(1000)
                _connectionStatusChannel.send(it)
            }
        }
    }
}