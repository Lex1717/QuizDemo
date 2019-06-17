package com.alexeeff.golangpuzzler.core.entity

import android.content.Context
import android.net.ConnectivityManager
import com.alexeeff.golangpuzzler.core.interfaces.NetworkManager

/**
 * implementation {@link NetworkManager}
 *
 * @author Yaroslav Alekseev
 */
class NetworkManagerImpl(context: Context) : NetworkManager {

    private var connectivityManager: ConnectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    
    override fun isOnline(): Boolean {
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}