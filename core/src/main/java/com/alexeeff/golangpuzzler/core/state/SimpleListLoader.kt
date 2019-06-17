package com.alexeeff.golangpuzzler.core.state

import io.reactivex.Single
import io.reactivex.disposables.Disposable

/**
 * Encapsulates loading list logic for simple UI requirements.
 * For more complex logic with additional requirements use {@link Paginator}
 *
 * @author Yaroslav Alexeev
 */

class SimpleListLoader<T>(
        private val loader: () -> Single<List<T>>,
        private val viewController: ViewController<T>
) {
    private var currentState: State<T> = Empty()
    private var disposable: Disposable? = null
    private val data = mutableListOf<T>()

    var onListLoadedListener: (List<T>) -> Unit = {}

    fun getData(): List<T> {
        return data
    }
    fun loadData() {
        currentState.loadData()
    }

    fun cancel() {
        currentState.exit()
    }

    private fun loadList() {
        disposable?.dispose()
        disposable = loader()
                .subscribe(
                        {currentState.onListLoaded(it)},
                        {currentState.onError(it)}
                )
    }

    interface ViewController<T> {
        fun showLoading(isShown: Boolean)
        fun showData(data: List<T>)
        fun showDataView(isShown: Boolean)
        fun showError(isShown: Boolean, error: Throwable?)
    }

    private interface State<T> {
        fun loadData() {}
        fun exit() {}
        fun onListLoaded(list: List<T>) {}
        fun onError(error: Throwable) {}
    }

    private inner class Empty: State<T> {

        override fun loadData() {
            currentState = Loading()
            viewController.showDataView(false)
            viewController.showLoading(true)
            loadList()
        }

        override fun exit() {
            currentState = Exit()
            disposable?.dispose()
        }
    }

    private inner class Loading: State<T> {

        override fun onListLoaded(list: List<T>) {
            currentState = LoadedData()
            viewController.showLoading(false)
            viewController.showDataView(true)
            data.clear()
            data.addAll(list)
            onListLoadedListener(data)
            viewController.showData(data)
        }

        override fun onError(error: Throwable) {
            currentState = Error()
            viewController.showLoading(false)
            viewController.showError(true, error)
        }

        override fun exit() {
            currentState = Exit()
            disposable?.dispose()
        }
    }

    private inner class LoadedData: State<T> {

        override fun loadData() {
            currentState = Loading()
            viewController.showDataView(false)
            viewController.showLoading(true)
            loadList()
        }

        override fun exit() {
            currentState = Exit()
            disposable?.dispose()
        }
    }

    private inner class Error: State<T> {

        override fun loadData() {
            currentState = Loading()
            viewController.showError(false, null)
            viewController.showLoading(true)
            loadList()
        }

        override fun exit() {
            currentState = Exit()
            disposable?.dispose()
        }
    }

    private inner class Exit: State<T>
}