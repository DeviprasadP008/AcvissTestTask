package com.AcvissTechnologies.acvisstesttask.ui.scan

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Size
import android.view.View
import androidx.annotation.RequiresApi
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import com.AcvissTechnologies.acvisstesttask.MainActivity
import com.AcvissTechnologies.acvisstesttask.R
import com.AcvissTechnologies.acvisstesttask.database.ViewModel.ScanViewModel
import com.AcvissTechnologies.acvisstesttask.ui.basicAlert
import com.AcvissTechnologies.acvisstesttask.ui.home.HomeFragment
import com.AcvissTechnologies.acvisstesttask.utils.MyImageAnalyzer
import com.google.android.material.tabs.TabLayout
import com.google.common.util.concurrent.ListenableFuture
import kotlinx.android.synthetic.main.fragment_scan.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class ScanFragment : Fragment(R.layout.fragment_scan), ScanListner {

    private lateinit var cameraProviderFuture: ListenableFuture<ProcessCameraProvider>
    private lateinit var cameraExecutor: ExecutorService
    private lateinit var analyzer: MyImageAnalyzer
    private val PERMISSION_REQUEST_CODE = 200
    lateinit var scanViewModel: ScanViewModel

    @SuppressLint("UseRequireInsteadOfGet")
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        scanViewModel = ViewModelProvider(this).get(ScanViewModel()::class.java)
        scanViewModel.scanListner = this
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            openScanning()
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun openScanning() {
        if (requireActivity().checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.CAMERA), PERMISSION_REQUEST_CODE)
        } else {
            initializescanning()
        }
    }

    private fun initializescanning(){
        analyzer = activity?.let { MyImageAnalyzer(scanViewModel, it.applicationContext) }!!
        cameraExecutor = Executors.newSingleThreadExecutor()
        cameraProviderFuture = activity?.let { ProcessCameraProvider.getInstance(it) } as ListenableFuture<ProcessCameraProvider>
        cameraProviderFuture.addListener(Runnable {
            val cameraProvider = cameraProviderFuture.get()
            bindPreview(cameraProvider)
        }, ContextCompat.getMainExecutor(activity))
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == PERMISSION_REQUEST_CODE && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            initializescanning()
        }
    }

    @SuppressLint("UnsafeExperimentalUsageError")
    private fun bindPreview(cameraProvider: ProcessCameraProvider) {
        val preview: Preview = Preview.Builder().build()
        val cameraSelector: CameraSelector = CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_BACK).build()
        preview.setSurfaceProvider(previewView.createSurfaceProvider(null))
        val imageAnalysis = ImageAnalysis.Builder()
            .setTargetResolution(Size(1280, 720))
            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
            .build()
        imageAnalysis.setAnalyzer(cameraExecutor, analyzer)
        cameraProvider.bindToLifecycle(
            this as LifecycleOwner,
            cameraSelector,
            imageAnalysis,
            preview
        )
    }


    override fun onEvent(title: String, message: String) {
        println("Dialog title and message are : " + title + " " + message)
        // view?.let { context?.let { it1 -> basicAlert(it, it1, title, message) } }
    }

}



