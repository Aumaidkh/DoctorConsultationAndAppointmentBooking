package com.android.doctorce.core

import android.animation.ValueAnimator
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.lifecycleScope
import com.android.doctorce.feature_book_appointment.presentation.core.InternetAwareViewModel
import com.android.doctorce.feature_book_appointment.presentation.util.ConnectivityObserver
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

interface InternetAwareActivity {

    fun handleInternetConnection(connectivityShower: View, viewModel: InternetAwareViewModel, scope: CoroutineScope)

}

class InternetAwareActivityImpl: InternetAwareActivity{

    /**
     * Toggles the visibility of connectivityShower widget according to the internet
     * availability*/
    override fun handleInternetConnection(connectivityShower: View, viewModel: InternetAwareViewModel, scope: CoroutineScope) {
        scope.launch {
            viewModel.connectionStatusChannel.collect { connectionStatus ->
                when(connectionStatus) {
                    ConnectivityObserver.ConnectionStatus.Connected -> {
                        connectivityShower.visibility = View.GONE
                        showNoInternetDialog(false,connectivityShower)
                    }
                    ConnectivityObserver.ConnectionStatus.Unavailable -> {
                        connectivityShower.visibility = View.VISIBLE
                        showNoInternetDialog(true,connectivityShower)
                    }
                }
            }
        }
    }


    private fun showNoInternetDialog(shouldShow: Boolean, view: View){
        val anim = ValueAnimator.ofInt(
            view.measuredHeight,
            if (shouldShow) 73 else 0
        )
        anim.addUpdateListener {
            val value = it.animatedValue as Int
            val layoutParams = view.layoutParams
            layoutParams.height = value
            view.layoutParams = layoutParams
        }
        anim.duration = 100
        anim.start()
    }
}
