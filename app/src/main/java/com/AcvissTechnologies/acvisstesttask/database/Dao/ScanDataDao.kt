package com.AcvissTechnologies.acvisstesttask.database.Dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.AcvissTechnologies.acvisstesttask.database.Model.ScanDataModel


@Dao
interface ScanDataDao {

    @Query("SELECT * FROM qrscandata")
    fun getAll(): LiveData<List<ScanDataModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveScanData(scandata: ScanDataModel?)

    @Query("DELETE FROM qrscandata")
    fun deleteAllScanData()


}
