package com.testtask.testtasktchk.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.testtask.testtasktchk.data.entities.User
import com.testtask.testtasktchk.data.repository.UsersRepository
import com.testtask.testtasktchk.ui.BaseViewModel
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers

/**
 * @autor d.snytko
 */
class SearchViewModel(private val repository: UsersRepository) : BaseViewModel() {

    private val _userLiveData = MutableLiveData<List<User>>()
    val userLiveData: LiveData<List<User>> get() = _userLiveData

    private val _errorLiveData = MutableLiveData<Throwable>()
    val errorLiveData: LiveData<Throwable> get() = _errorLiveData

    private var page: Int = 1
    var recyclerViewPageLoading: Boolean = false

//    fun search(input: String) {
//        repository.getUsers(input, 1)
//            .subscribeOn(Schedulers.io())
//            .subscribe(
//                { users -> _userLiveData.postValue(users) },
//                { error -> _errorLiveData.postValue(error) }
//            )
//            .disposeOnFinish()
//    }

    fun searchFromQuery(flowableQuery: Flowable<String>) {
        recyclerViewPageLoading = true
        flowableQuery
            .flatMapSingle { query -> repository.getUsers(query, 1) }
            .subscribeOn(Schedulers.io())
            .doOnNext { recyclerViewPageLoading = false; page = 1 }
            .subscribe(
                { users -> _userLiveData.postValue(users) },
                { error -> _errorLiveData.postValue(error) }
            )
            .disposeOnFinish()
    }

    fun searchFromQueryNextPage(query: String) {
        page += 1
        repository.getUsers(query, page)
            .subscribeOn(Schedulers.io())
            .doFinally { recyclerViewPageLoading = false }
            .subscribe(
                { users -> _userLiveData.postValue(concatUserList(users)) },
                { error -> _errorLiveData.postValue(error) }
            )
            .disposeOnFinish()
    }

    private fun concatUserList(list: List<User>): List<User> {
        return _userLiveData.value?.toMutableList()?.apply { addAll(list) } ?: emptyList()
    }
}
