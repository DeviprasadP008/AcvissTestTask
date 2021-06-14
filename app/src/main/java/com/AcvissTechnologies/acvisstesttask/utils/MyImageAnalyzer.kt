package com.AcvissTechnologies.acvisstesttask.utils

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.AcvissTechnologies.acvisstesttask.database.Model.ScanDataModel
import com.AcvissTechnologies.acvisstesttask.database.ViewModel.ScanViewModel
import com.AcvissTechnologies.acvisstesttask.ui.scan.ScanListner
import com.google.mlkit.vision.barcode.Barcode
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*

class MyImageAnalyzer(private val scanViewModel: ScanViewModel, private val context: Context) : ImageAnalysis.Analyzer {

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun analyze(imageProxy: ImageProxy) {
        scanBarcode(imageProxy)
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    @SuppressLint("UnsafeExperimentalUsageError")
    private fun scanBarcode(imageProxy: ImageProxy) {
        imageProxy.image?.let { image ->
            val inputImage = InputImage.fromMediaImage(image, imageProxy.imageInfo.rotationDegrees)
            val options = BarcodeScannerOptions.Builder().setBarcodeFormats(Barcode.FORMAT_QR_CODE, Barcode.FORMAT_AZTEC).build()
            val scanner = BarcodeScanning.getClient()
            scanner.process(inputImage)
                .addOnCompleteListener {
                    imageProxy.close()
                    if (it.isSuccessful) {
                        readBarcodeData(it.result as List<Barcode>)
                    } else {
                        it.exception?.printStackTrace()
                    }
                }.addOnFailureListener {
                    it.message?.let { it1 -> Log.e("FAILURE", it1) }
                }.addOnSuccessListener { barcodes ->
                    barcodes.forEach {
                        Log.d("SUCCESS", it.rawValue)
                    }
                }
        }
    }

    private fun readBarcodeData(barcodes: List<Barcode>) {
        val simpleDateFormat = SimpleDateFormat("dd-MM-yyyy hh:mm a")
        val currentDateAndTime: String = simpleDateFormat.format(Date())
        println("Currenttimestamp is : " + currentDateAndTime)
        for (barcode in barcodes) {
            when (barcode.valueType) {
                Barcode.TYPE_URL -> {
                    println("Response from QR code URL is : " + barcode.url?.url.toString())
                    println("Response from QR code URL title is : " + barcode.url?.title.toString())
                    scanViewModel.insertScanData(context, barcode.url?.url.toString(), currentDateAndTime)
                }
                Barcode.TYPE_WIFI -> {
                    println("Response from QR code WIFI ssid is : " + barcode.wifi!!.ssid.toString())
                    println("Response from QR code WIFI password is : " + barcode.wifi!!.password.toString())
                    println("Response from QR code WIFI encryptiontype is : " + barcode.wifi!!.encryptionType.toString())
                    val ssid = barcode.wifi!!.ssid
                    val password = barcode.wifi!!.password
                    val type = barcode.wifi!!.encryptionType
                    scanViewModel.insertScanData(context, ssid.toString(), currentDateAndTime)
                }
            }
        }
    }

}
