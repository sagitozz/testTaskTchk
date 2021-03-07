package com.testtask.testtasktchk.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.testtask.testtasktchk.data.repository.UsersRepository
import javax.inject.Inject

/**
 * @autor d.snytko
 */
class SearchViewModelFactory @Inject constructor(private val repository: UsersRepository) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when (modelClass) {
        SearchViewModel::class.java -> SearchViewModel(repository)
        else -> throw IllegalArgumentException("$modelClass is not registered ViewModel")
    } as T
}
