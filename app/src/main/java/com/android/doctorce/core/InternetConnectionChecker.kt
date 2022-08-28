package com.android.doctorce.core

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import com.android.doctorce.feature_book_appointment.presentation.util.ConnectivityObserver
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class InternetConnectionChecker(
    context: Context
): ConnectivityObserver {

    private val connectionManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun observeNetworkState(): Flow<ConnectivityObserver.ConnectionStatus> {
        return callbackFlow {
            val callback = object : ConnectivityManager.NetworkCallback(){
                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    launch {
                        send(ConnectivityObserver.ConnectionStatus.Connected)
                    }
                }

                override fun onLost(network: Network) {
                    super.onLost(network)
                    launch {
                        send(ConnectivityObserver.ConnectionStatus.Unavailable)
                    }
                }

                override fun onUnavailable() {
                    super.onUnavailable()
                    launch {
                        send(ConnectivityObserver.ConnectionStatus.Unavailable)
                    }
                }
            }
            connectionManager.registerDefaultNetworkCallback(callback)

            awaitClose {
                connectionManager.unregisterNetworkCallback(callback)
            }
        }.distinctUntilChanged()
    }
}