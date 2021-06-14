package com.AcvissTechnologies.acvisstesttask.ui.scanhistory

interface ScanDataListner {
    fun onDataSyncSuccess()
    fun onDataSyncFailure(message: String)
}