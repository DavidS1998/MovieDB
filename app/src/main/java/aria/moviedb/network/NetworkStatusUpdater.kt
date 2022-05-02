package aria.moviedb.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.lifecycle.LiveData

// Two relevant network states
sealed class NetworkStatus {
    object Available : NetworkStatus()
    object Unavailable : NetworkStatus()
}

// Created by following a tutorial
class NetworkStatusHelper(context: Context) : LiveData<NetworkStatus>() {
    val validateNetworkConnections: MutableSet<Network> = HashSet()

    // ConnectivityManager that helps monitor network changes
    var connectivityManager: ConnectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private lateinit var connectivityManagerCallback: ConnectivityManager.NetworkCallback

    // Sets network status based on presence of the network within the network list
    fun announceStatus() {
        if (validateNetworkConnections.isNotEmpty()) {
            postValue(NetworkStatus.Available)
        } else {
            postValue(NetworkStatus.Unavailable)
        }
    }

    // Network listener
    private fun getConnectivityManagerCallback() =
        object : ConnectivityManager.NetworkCallback() {

            // Network is available, add it to the list
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                val networkCapability = connectivityManager.getNetworkCapabilities(network)
                val hasNetworkConnection =
                    networkCapability?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                        ?: false
                if (hasNetworkConnection) {
                    validateNetworkConnections.add(network)
                    announceStatus()
                }
            }

            // Network is unavailable, remove it from the list
            override fun onLost(network: Network) {
                super.onLost(network)
                validateNetworkConnections.remove(network)
                announceStatus()
            }

            // Runs when a network change has occured AND connection setup is finished
            override fun onCapabilitiesChanged(
                network: Network,
                networkCapabilities: NetworkCapabilities
            ) {
                super.onCapabilitiesChanged(network, networkCapabilities)
                if (networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)) {
                    validateNetworkConnections.add(network)
                } else {
                    validateNetworkConnections.remove(network)
                }
                announceStatus()
            }
        }


    // Monitor only when active/needed
    override fun onActive() {
        super.onActive()
        connectivityManagerCallback = getConnectivityManagerCallback()
        val networkRequest = NetworkRequest
            .Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()
        connectivityManager.registerNetworkCallback(networkRequest, connectivityManagerCallback)
    }

    // Stop monitoring when not needed
    override fun onInactive() {
        super.onInactive()
        connectivityManager.unregisterNetworkCallback(connectivityManagerCallback)
    }
}