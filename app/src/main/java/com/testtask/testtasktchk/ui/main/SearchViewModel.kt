package com.testtask.testtasktchk.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.testtask.testtasktchk.data.entities.User
import com.testtask.testtasktchk.data.entities.UserList
import com.testtask.testtasktchk.data.repository.UsersRepository
import com.testtask.testtasktchk.ui.BaseViewModel
import io.reactivex.schedulers.Schedulers

/**
 * @autor d.snytko
 */
class SearchViewModel (private val repository: UsersRepository) : BaseViewModel() {

    private val _userLiveData = MutableLiveData<UserList>()
    val userLiveData: LiveData<UserList> get() = _userLiveData

    private val _errorLiveData = MutableLiveData<Throwable>()
    val errorLiveData: LiveData<Throwable> get() = _errorLiveData

    fun search(input: String) {
        repository.getUsers(input, 1)
            .subscribeOn(Schedulers.io())
            .subscribe(
                {users -> _userLiveData.postValue(users)},
                {error -> _errorLiveData.postValue(error)}
            )
            .disposeOnFinish()
    }
}
