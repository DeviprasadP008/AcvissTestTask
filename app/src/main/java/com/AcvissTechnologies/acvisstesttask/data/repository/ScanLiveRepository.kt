package com.AcvissTechnologies.acvisstesttask.data.repository

import com.AcvissTechnologies.acvisstesttask.data.network.ScanApi

class ScanLiveRepository(
    private val api: ScanApi,
) : BaseRepository(){

    suspend fun saveScanLiveData(
        scanresult: String,
        scantimestamp: String
    ) = safeApiCall {
        api.saveScan(scanresult, scantimestamp)
    }

}
