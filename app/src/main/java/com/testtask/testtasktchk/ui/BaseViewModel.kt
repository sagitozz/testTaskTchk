package com.testtask.testtasktchk.ui

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * @autor d.snytko
 */
abstract class BaseViewModel : ViewModel() {

    protected val viewModelDisposable: CompositeDisposable = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        viewModelDisposable.clear()
    }

    protected fun Disposable.disposeOnFinish() : Disposable {
        viewModelDisposable.add(this)
        return this
    }

}
