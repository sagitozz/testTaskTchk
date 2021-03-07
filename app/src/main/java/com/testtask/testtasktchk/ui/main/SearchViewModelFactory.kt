package com.testtask.testtasktchk.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

/**
 * @autor d.snytko
 */
class SearchViewModelFactory @Inject constructor() : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when (modelClass) {
        SearchViewModel::class.java -> SearchViewModel()
        else -> throw IllegalArgumentException("$modelClass is not registered ViewModel")
    } as T
}
