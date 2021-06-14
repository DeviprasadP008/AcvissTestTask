package com.AcvissTechnologies.acvisstesttask.database.ViewModel

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.AcvissTechnologies.acvisstesttask.database.Model.ScanDataModel
import com.AcvissTechnologies.acvisstesttask.database.Repository.ScanRepository
import com.AcvissTechnologies.acvisstesttask.ui.scan.ScanListner

class ScanViewModel : ViewModel() {

    var liveScanData: LiveData<List<ScanDataModel>>? = null
    var scanListner: ScanListner? = null

    fun insertScanData(context: Context, scanresult: String, scantimestamp: String) {
        println("Scan result to insert is : " + scanresult)
        println("Scan timestamp to insert is : " + scantimestamp)
        ScanRepository.insertScanData(context, scanresult, scantimestamp)
        scanListner?.onEvent("Data saved", "QR code scan successful")
    }

    fun getScanDataDetails(context: Context) : LiveData<List<ScanDataModel>>? {
        liveScanData = ScanRepository.getAllScanDetails(context)
        return liveScanData
    }

    fun deleteAllScanData(context: Context){
        ScanRepository.deleteAllScanDetails(context)
    }

}
