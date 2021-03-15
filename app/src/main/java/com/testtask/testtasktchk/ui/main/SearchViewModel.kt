package com.testtask.testtasktchk.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.testtask.testtasktchk.data.entities.User
import com.testtask.testtasktchk.data.repository.UsersRepository
import com.testtask.testtasktchk.ui.BaseViewModel
import io.reactivex.Observable
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 * @autor d.snytko
 */
class SearchViewModel(private val repository: UsersRepository) : BaseViewModel() {

    private val _concLiveData = MutableLiveData<SearchState>()
    val concLiveData: LiveData<SearchState> get() = _concLiveData

    var recyclerViewPageLoading = false

    fun searchFromQuery(search: Observable<Pair<String, Int>>) {
        search
            .debounce(600, TimeUnit.MILLISECONDS)
            .filter { s -> s.first.isNotEmpty() }
            .distinctUntilChanged()
            .subscribeOn(Schedulers.io())
            .doOnNext { (_, page) ->
                if (page == 1) {
                    SearchState.Result(emptyList())
                    _concLiveData.postValue(SearchState.Result(emptyList()))
                }
            }
            .switchMap { (query, page) ->
                recyclerViewPageLoading = true
                repository.getUsers(query, page).toObservable()
                    .onErrorResumeNext(Function {
                        _concLiveData.postValue(SearchState.Error(it.message, it))
                        Observable.just(emptyList())
                    })
            }
            .map { concatUserList(it) }
            .map { items -> if (items.isNotEmpty()) SearchState.Result(items) else SearchState.Empty }
            .subscribe(
                { state ->
                    _concLiveData.postValue(state)
                    recyclerViewPageLoading = false
                },
                { error ->
                    _concLiveData.postValue(SearchState.Error(error.message, IllegalStateException()))
                }
            ).disposeOnFinish()
    }

    private fun concatUserList(list: SearchState.Result): List<User> {
        val currentList = _concLiveData.value as SearchState.Result
        return currentList.data.toMutableList().apply { addAll(list.data) }
    }

    sealed class SearchState {
        data class Result(val data: List<User>) : SearchState()
        class Error(val message: String?, val throwable: Throwable) : SearchState()
        object Empty : SearchState()
        object EndOfList : SearchState()
    }
}
