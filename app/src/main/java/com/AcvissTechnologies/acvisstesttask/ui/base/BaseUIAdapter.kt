package com.AcvissTechnologies.acvisstesttask.ui.base

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.AcvissTechnologies.acvisstesttask.R
import com.AcvissTechnologies.acvisstesttask.database.Model.ScanDataModel

class BaseUIAdapter(private var scanlist: List<ScanDataModel>, private val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>()  {

    private inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var tv_scanneddetails: TextView
        lateinit var tv_scannedtimestamp: TextView
        init {
            tv_scanneddetails=itemView.findViewById(R.id.tv_scanneddetails)
            tv_scannedtimestamp=itemView.findViewById(R.id.tv_scannedtimestamp)
        }

        fun bind(position: Int) {
            val recyclerViewModel = scanlist[position]
            tv_scanneddetails.text = recyclerViewModel.scanresult
            tv_scannedtimestamp.text = "Scanned on : " + recyclerViewModel.scantimestamp
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_scannedtask, parent, false))
    }

    override fun getItemCount(): Int {
        return scanlist.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind(position)
    }

}