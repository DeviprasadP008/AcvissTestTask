package com.AcvissTechnologies.acvisstesttask.ui.base

import androidx.lifecycle.ViewModel
import com.AcvissTechnologies.acvisstesttask.data.repository.BaseRepository

abstract class BaseViewModel(
    private val repository: BaseRepository
): ViewModel() {

}