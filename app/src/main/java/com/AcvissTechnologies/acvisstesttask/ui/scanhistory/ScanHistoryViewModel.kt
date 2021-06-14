package com.AcvissTechnologies.acvisstesttask.ui.scanhistory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.AcvissTechnologies.acvisstesttask.data.network.RemoteDataSource
import com.AcvissTechnologies.acvisstesttask.data.network.Resource
import com.AcvissTechnologies.acvisstesttask.data.network.ScanApi
import com.AcvissTechnologies.acvisstesttask.data.repository.ScanLiveRepository
import com.AcvissTechnologies.acvisstesttask.data.responses.SampleResponse
import com.AcvissTechnologies.acvisstesttask.data.responses.ScanLiveData
import com.AcvissTechnologies.acvisstesttask.ui.base.BaseViewModel
import com.AcvissTechnologies.acvisstesttask.ui.scan.ScanListner
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory



class ScanHistoryViewModel : ViewModel() {

     var scanDataListner: ScanDataListner? = null

     fun insertScanDataServer(scanresult: String, scantimestamp: String) {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(RemoteDataSource.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val api: ScanApi = retrofit.create(ScanApi::class.java)
        val call: Call<SampleResponse> = api.saveScan(scanresult,scantimestamp)
        call.enqueue(object : Callback<SampleResponse> {
            override fun onResponse(call: Call<SampleResponse>, response: Response<SampleResponse>) {
                scanDataListner?.onDataSyncSuccess()
            }
            override fun onFailure(call: Call<SampleResponse>, t: Throwable) {
                t.message?.let { scanDataListner?.onDataSyncFailure(it) }
            }
        })
    }
}


/*class ScanHistoryViewModel(
    private val repository: ScanLiveRepository
) : BaseViewModel(repository) {

    private val _scanResponse: MutableLiveData<Resource<ScanLiveData>> = MutableLiveData()
    val loginResponse: LiveData<Resource<ScanLiveData>>
    get() = _scanResponse

    fun insertScanDataServer(scandata: String, scantimestamp: String) = viewModelScope.launch {
        _scanResponse.value = Resource.Loading
        _scanResponse.value = repository.saveScanLiveData(scandata, scantimestamp)
    }

}*/


