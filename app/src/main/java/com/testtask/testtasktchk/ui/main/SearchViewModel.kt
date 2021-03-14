package com.testtask.testtasktchk.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.testtask.testtasktchk.data.entities.User
import com.testtask.testtasktchk.data.repository.UsersRepository
import com.testtask.testtasktchk.ui.BaseViewModel
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.lang.IllegalStateException

/**
 * @autor d.snytko
 */
class SearchViewModel(private val repository: UsersRepository) : BaseViewModel() {

    private val _concLiveData = MutableLiveData<SearchState>()
    val concLiveData: LiveData<SearchState> get() = _concLiveData


//    private val _userLiveData = MutableLiveData<List<User>>()
//    val userLiveData: LiveData<List<User>> get() = _userLiveData
//
//    private val _errorLiveData = MutableLiveData<Throwable>()
//    val errorLiveData: LiveData<Throwable> get() = _errorLiveData

    private var page: Int = 1

    var recyclerViewPageLoading: Boolean = false

    fun searchFromQuery(flowableQuery: Flowable<String>) {
//        recyclerViewPageLoading = false
     flowableQuery
            .flatMapSingle { query ->

                     repository.getUsers(query, page)
            }
//            .doOnSubscribe { _concLiveData.postValue(SearchUsers.Loading) }
            .subscribeOn(Schedulers.io())
            .doOnSubscribe {
                _concLiveData.value = null }
         .doOnNext { recyclerViewPageLoading = false; page = 1 }
            .subscribe(
                { users ->
                    Log.d("XXX", "request")
                    if (users.isNotEmpty()) {
                        _concLiveData.postValue(SearchState.Result(users))
//                        _userLiveData.postValue(users)
                    } else {
                        _concLiveData.postValue(SearchState.Empty)
//                        _errorLiveData.postValue(Throwable("Пользователь с таким именем не найден"))
                    }
                },
                { error ->
                    _concLiveData.postValue(SearchState.Error(error.message, IllegalStateException()))
//                    _errorLiveData.postValue(error)
                }
            )
            .disposeOnFinish()
    }

    fun searchFromQueryNextPage(query: String) {
        recyclerViewPageLoading = true
        page += 1

        repository.getUsers(query, page)
            .subscribeOn(Schedulers.io())
            .doOnSuccess { recyclerViewPageLoading = false }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { users ->
                    Log.d("XXX", "request")
                    if (users.isNotEmpty()) {
                        _concLiveData.postValue(SearchState.Result(concatUserList(SearchState.Result(users))))
//                    _userLiveData.postValue(concatUserList(users))
                    }
//                    else {
//                        _concLiveData.postValue(SearchState.Error("Достингут конец списка", IllegalStateException()))}
                },
                { error ->
//                    _errorLiveData.postValue(error)
                    _concLiveData.postValue(SearchState.Error(error.message, IllegalStateException()))
                    _concLiveData.value = null
                }
            )
            .disposeOnFinish()
    }

    private fun concatUserList(list: SearchState.Result): List<User> {
        val currentList = _concLiveData.value as SearchState.Result
        return currentList.data.toMutableList().apply { addAll(list.data) }
    }

    sealed class SearchState {
        data class Result(val data: List<User>) : SearchState()
        class Error(val message: String?, val throwable: Throwable) : SearchState()
        object Empty : SearchState()
    }
}
