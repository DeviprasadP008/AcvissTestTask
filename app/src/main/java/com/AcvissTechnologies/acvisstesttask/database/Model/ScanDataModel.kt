package com.AcvissTechnologies.acvisstesttask.database.Model
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "qrscandata", indices = [Index(value = ["scanresult"], unique = true)])
data class ScanDataModel(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "scanresult") val scanresult: String?,
    @ColumnInfo(name = "scantimestamp") val scantimestamp: String?,
)