package com.AcvissTechnologies.acvisstesttask.ui.scanhistory

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.AcvissTechnologies.acvisstesttask.R
import com.AcvissTechnologies.acvisstesttask.data.network.NetworkConnectionInterceptor
import com.AcvissTechnologies.acvisstesttask.database.Model.ScanDataModel
import com.AcvissTechnologies.acvisstesttask.database.ViewModel.ScanViewModel
import com.AcvissTechnologies.acvisstesttask.ui.base.BaseUIAdapter
import com.AcvissTechnologies.acvisstesttask.ui.basicAlert
import kotlinx.android.synthetic.main.fragment_scanhistory.*


class ScanHistoryFragment : Fragment(R.layout.fragment_scanhistory), ScanDataListner{

    lateinit var scanViewModel: ScanViewModel
    lateinit var scanLiveViewModel: ScanHistoryViewModel

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)

        scanViewModel = ViewModelProvider(this).get(ScanViewModel::class.java)
        scanLiveViewModel = ViewModelProvider(this).get(ScanHistoryViewModel::class.java)
        scanLiveViewModel.scanDataListner = this

        context?.let { scanViewModel.getScanDataDetails(it) }!!.observe(viewLifecycleOwner, Observer {
            println("Value from db are : " + it)
            if (it == null) {
                println("Context is null")
            } else {
                userListUpdateObserver(it)
            }
        })

    }

    private fun userListUpdateObserver(itvalue : List<ScanDataModel>) {
        println("Value from Sqlite is : " + itvalue)
        rv_scandata.adapter = activity?.applicationContext?.let {
            BaseUIAdapter(itvalue, it)
        }
        rv_scandata.layoutManager= LinearLayoutManager(activity?.applicationContext, LinearLayoutManager.VERTICAL,false)
        savedatatoserver(itvalue)
    }

    private fun savedatatoserver(itvalue : List<ScanDataModel>){
        if(isNetworkAvailable(context)){
            for(item in itvalue)
                activity?.applicationContext?.let { scanLiveViewModel.insertScanDataServer(item.scanresult.toString(), item.scantimestamp.toString()) }
        }
    }


    fun isNetworkAvailable(context: Context?): Boolean {
        if (context == null) return false
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        return true
                    }
                }
            }
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                return true
            }
        }
        return false
    }

    override fun onDataSyncSuccess() {
        println("Data Sync Success")
       // context?.let { scanViewModel.deleteAllScanData(it) }
       // view?.let { context?.let { it1 -> basicAlert(it, it1, "", "Data sent to Server") } }
    }

    override fun onDataSyncFailure(message: String) {
        println("Data Sync Failure : " + message)
       // view?.let { context?.let { it1 -> basicAlert(it, it1, "", message) } }
    }

}




