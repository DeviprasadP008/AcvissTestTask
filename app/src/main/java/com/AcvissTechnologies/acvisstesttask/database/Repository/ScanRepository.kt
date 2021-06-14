package com.AcvissTechnologies.acvisstesttask.database.Repository

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import com.AcvissTechnologies.acvisstesttask.database.AppDatabase.AppDatabase
import com.AcvissTechnologies.acvisstesttask.database.Dao.ScanDataDao
import com.AcvissTechnologies.acvisstesttask.database.Model.ScanDataModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch


class ScanRepository {

    companion object {

        var appDatabase: AppDatabase? = null
        var scandataModel: LiveData<List<ScanDataModel>>? = null

        fun initializeDB(context: Context) : AppDatabase {
            return AppDatabase.getDataseClient(context)
        }

        fun insertScanData(context: Context, scanresult: String, scantimestamp: String) {
            appDatabase = initializeDB(context)
            CoroutineScope(IO).launch {
                val loginDetails = ScanDataModel(0, scanresult, scantimestamp)
                appDatabase!!.scandataDao().saveScanData(loginDetails)
            }
        }

        fun getAllScanDetails(context: Context) : LiveData<List<ScanDataModel>>? {
            appDatabase = initializeDB(context)
            scandataModel = appDatabase!!.scandataDao().getAll()
            return scandataModel
        }

        fun deleteAllScanDetails(context: Context){
            appDatabase = initializeDB(context)
            CoroutineScope(IO).launch {
                appDatabase!!.scandataDao().deleteAllScanData()
            }
        }


    }
}




/*class ScanRepository(application: Application?) {
   private val scandatadao: ScanDataDao
   private val allScanData: LiveData<List<ScanDataModel>>

   fun insert(scandata: ScanDataModel?) {
       InsertScanDataAsyncTask(scandatadao).execute(scandata)
   }

   fun getAllScanData(): LiveData<List<ScanDataModel>> {
       return allScanData
   }

   private class InsertScanDataAsyncTask(scanDao: ScanDataDao) :
       AsyncTask<ScanDataModel?, Void?, Void?>() {
       private val scandataDao: ScanDataDao
       override fun doInBackground(vararg scandata: ScanDataModel?): Void? {
           scandataDao.saveScanData(scandata[0])
           return null
       }
       init {
           this.scandataDao = scanDao
       }
   }

   init {
       val database: AppDatabase = application?.let { AppDatabase.getDataseClient(it) }!!
       scandatadao = database.scandataDao()
       allScanData = scandatadao.getAll()
   }
}*/


