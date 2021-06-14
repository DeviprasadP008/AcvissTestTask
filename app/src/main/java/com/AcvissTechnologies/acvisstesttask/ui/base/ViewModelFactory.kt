package com.AcvissTechnologies.acvisstesttask.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.AcvissTechnologies.acvisstesttask.data.repository.BaseRepository
import com.AcvissTechnologies.acvisstesttask.ui.home.HomeViewModel
import java.lang.IllegalArgumentException

class ViewModelFactory(
    private val repository: BaseRepository
): ViewModelProvider.NewInstanceFactory() {

    /*override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when{
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> HomeViewModel(repository as ScanRepository) as T
            else -> throw IllegalArgumentException("ViewModelClass Not Found")
        }
    }*/

}