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

    private val _searchLiveData = MutableLiveData<SearchState>()
    val searchLiveData: LiveData<SearchState> get() = _searchLiveData

    var recyclerViewPageLoading = false

    fun searchFromQuery(search: Observable<Pair<String, Int>>) {
        search
            .debounce(600, TimeUnit.MILLISECONDS)
            .filter { s -> s.first.isNotEmpty() }
            .distinctUntilChanged()
            .subscribeOn(Schedulers.io())
            .doOnNext { (_, page) -> checkIfFirstPage(page) }
            .switchMap { (query, page) -> sendRequest(query, page) }
            .map { concatUserList(it) }
            .map { items ->
                if (items.isNotEmpty()) {
                    SearchState.Result(items)
                } else {
                    SearchState.Empty
                }
            }
            .subscribe(
                { state ->
                    _searchLiveData.postValue(state)
                    recyclerViewPageLoading = false
                },
                { error ->
                    _searchLiveData.postValue(SearchState.Error(error.message, error))
                }
            ).disposeOnFinish()
    }

    private fun checkIfFirstPage(page: Int) {
        if (page == 1) {
            SearchState.Result(emptyList())
            _searchLiveData.postValue(SearchState.Result(emptyList()))
        }
    }

    private fun sendRequest(
        query: String,
        page: Int
    ): Observable<List<User>>? {
        recyclerViewPageLoading = true
        return repository.getUsers(query, page).toObservable()
            .onErrorResumeNext(Function {
                _searchLiveData.postValue(SearchState.Error(it.message, it))
                Observable.just(emptyList())
            })
    }

    private fun concatUserList(list: List<User>): List<User> {
        val result = _searchLiveData.value
        val state: SearchState.Result = if (result is SearchState.Result) result else return emptyList()

        return state.data.toMutableList()
            .apply { addAll(list) }
    }

    sealed class SearchState {
        data class Result(val data: List<User>) : SearchState()
        class Error(val message: String?, val throwable: Throwable) : SearchState()
        object Empty : SearchState()
    }
}
