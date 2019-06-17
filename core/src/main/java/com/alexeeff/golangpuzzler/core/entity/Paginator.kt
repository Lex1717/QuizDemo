package com.alexeeff.golangpuzzler.core.entity

import io.reactivex.Single
import io.reactivex.disposables.Disposable

const val FIRST_PAGE = 1
const val LIST_SIZE = 20

/**
 * Implements logic for loading lists, managing pagination.
 */
@Suppress("ClassName")
class Paginator<T>(
        private val loadList: (Int, Int, Boolean) -> Single<List<T>>,
        private val viewController: ViewController<T>,
        private val listFilter: Filter<T>
) {

    interface ViewController<T> {
        fun showLoading(isShown: Boolean)
        fun showData(data: List<T> = emptyList())
        fun showError(isShown: Boolean, error: Throwable? = null)
        fun showEmptyView(isShown: Boolean)
        fun showRefreshProgress(isShown: Boolean)
        fun showPageProgress(isShown: Boolean)
        fun showErrorMessage(error: Throwable)
    }

    interface Filter<T> {
        fun filter(item: T): Boolean
        fun clear()
    }

    private val currentData = mutableListOf<T>()
    private var currentState: State<T> = EMPTY()
    private var currentPage = 0
    private var disposable: Disposable? = null

    fun refresh(isForcedUpdate: Boolean) {
        listFilter.clear()
        currentState.refresh(isForcedUpdate)
    }

    fun loadNextPage() {
        currentState.loadNewPage()
    }

    fun cancel() {
        currentState.cancel()
    }

    private fun loadPage(page: Int, isForcedUpdate: Boolean) {
        disposable?.dispose()
        disposable = loadList(page, LIST_SIZE, isForcedUpdate)
                .toObservable()
                .flatMapIterable { list -> list }
                .filter { listFilter.filter(it) }
                .toList()
                .subscribe(
                        { currentState.newData(it) },
                        { currentState.fail(it) }
                )
    }

    private interface State<T> {
        fun refresh(isForcedUpdate: Boolean) {}
        fun loadNewPage() {}
        fun cancel() {}
        fun newData(data: List<T>) {}
        fun fail(error: Throwable) {}
    }

    private inner class EMPTY : State<T> {

        override fun refresh(isForcedUpdate: Boolean) {
            currentState = LOADING()
            viewController.showLoading(true)
            loadPage(FIRST_PAGE, isForcedUpdate)
        }

        override fun cancel() {
            currentState = CANCELED()
            disposable?.dispose()
        }
    }

    private inner class LOADING : State<T> {

        override fun newData(data: List<T>) {
            if (data.isNotEmpty()) {
                currentState = DATA()
                currentData.clear()
                currentData.addAll(data)
                currentPage = FIRST_PAGE
                viewController.showData(currentData)
                viewController.showLoading(false)
            } else {
                currentState = EMPTY_DATA()
                viewController.showLoading(false)
                viewController.showEmptyView(true)
            }
        }

        override fun fail(error: Throwable) {
            currentState = ERROR()
            viewController.showLoading(false)
            viewController.showError(true, error)
        }

        override fun cancel() {
            currentState = CANCELED()
            disposable?.dispose()
        }
    }

    private inner class ERROR : State<T> {

        override fun refresh(isForcedUpdate: Boolean) {
            currentState = LOADING()
            viewController.showError(false)
            viewController.showLoading(true)
            loadPage(FIRST_PAGE, isForcedUpdate)
        }

        override fun cancel() {
            currentState = CANCELED()
            disposable?.dispose()
        }
    }

    private inner class EMPTY_DATA : State<T> {

        override fun refresh(isForcedUpdate: Boolean) {
            currentState = LOADING()
            viewController.showEmptyView(false)
            viewController.showLoading(true)
            loadPage(FIRST_PAGE, isForcedUpdate)
        }

        override fun cancel() {
            currentState = CANCELED()
            disposable?.dispose()
        }
    }

    private inner class DATA : State<T> {

        override fun refresh(isForcedUpdate: Boolean) {
            currentState = REFRESH()
            viewController.showRefreshProgress(true)
            loadPage(FIRST_PAGE, isForcedUpdate)
        }

        override fun loadNewPage() {
            currentState = PAGE_PROGRESS()
            viewController.showPageProgress(true)
            loadPage(currentPage + 1, false)
        }

        override fun cancel() {
            currentState = CANCELED()
            disposable?.dispose()
        }
    }

    private inner class REFRESH : State<T> {

        override fun newData(data: List<T>) {
            if (data.isNotEmpty()) {
                currentState = DATA()
                currentData.clear()
                currentData.addAll(data)
                currentPage = FIRST_PAGE
                viewController.showRefreshProgress(false)
                viewController.showData(currentData)
            } else {
                currentState = EMPTY_DATA()
                currentData.clear()
                viewController.showRefreshProgress(false)
                viewController.showEmptyView(true)
            }
        }

        override fun fail(error: Throwable) {
            currentState = DATA()
            viewController.showRefreshProgress(false)
            viewController.showErrorMessage(error)
        }

        override fun cancel() {
            currentState = CANCELED()
            disposable?.dispose()
        }
    }

    private inner class PAGE_PROGRESS : State<T> {

        override fun newData(data: List<T>) {
            if (data.isNotEmpty()) {
                currentState = DATA()
                currentData.addAll(data)
                currentPage++
                viewController.showPageProgress(false)
                viewController.showData(currentData)
            } else {
                currentState = ALL_DATA()
                viewController.showPageProgress(false)
            }
        }

        override fun refresh(isForcedUpdate: Boolean) {
            currentState = REFRESH()
            viewController.showPageProgress(false)
            viewController.showRefreshProgress(true)
            loadPage(FIRST_PAGE, isForcedUpdate)
        }

        override fun fail(error: Throwable) {
            currentState = DATA()
            viewController.showPageProgress(false)
            viewController.showErrorMessage(error)
        }

        override fun cancel() {
            currentState = CANCELED()
            disposable?.dispose()
        }
    }

    private inner class ALL_DATA : State<T> {

        override fun refresh(isForcedUpdate: Boolean) {
            currentState = REFRESH()
            viewController.showRefreshProgress(true)
            loadPage(FIRST_PAGE, isForcedUpdate)
        }

        override fun cancel() {
            currentState = CANCELED()
            disposable?.dispose()
        }
    }

    private inner class CANCELED : State<T>
}