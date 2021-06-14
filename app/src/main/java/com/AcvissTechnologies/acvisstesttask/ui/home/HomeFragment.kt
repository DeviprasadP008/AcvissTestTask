package com.AcvissTechnologies.acvisstesttask.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.AcvissTechnologies.acvisstesttask.R
import com.AcvissTechnologies.acvisstesttask.database.ViewModel.ScanViewModel
import com.AcvissTechnologies.acvisstesttask.ui.scan.ScanFragment
import com.AcvissTechnologies.acvisstesttask.ui.scan.ScanListner
import com.AcvissTechnologies.acvisstesttask.ui.scanhistory.ScanHistoryFragment
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment(R.layout.fragment_home){

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val firstFragment= ScanFragment()
        val secondFragment= ScanHistoryFragment()
        setCurrentFragment(firstFragment)

        bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.scan_capture->setCurrentFragment(firstFragment)
                R.id.scan_history->setCurrentFragment(secondFragment)
            }
            true
        }
    }

    private fun setCurrentFragment(fragment: Fragment)= activity?.supportFragmentManager?.beginTransaction()
        ?.apply {
            replace(R.id.frame_home,fragment)
            commit()
        }


}

