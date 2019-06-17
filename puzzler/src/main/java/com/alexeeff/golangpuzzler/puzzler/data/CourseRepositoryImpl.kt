package com.alexeeff.golangpuzzler.puzzler.data

import com.alexeeff.golangpuzzler.core.exception.NoInternetConnectionException
import com.alexeeff.golangpuzzler.core.interfaces.NetworkManager
import com.alexeeff.golangpuzzler.puzzler.data.provider.CourseProvider
import com.alexeeff.golangpuzzler.puzzler.data.storage.CourseStorage
import com.alexeeff.golangpuzzler.puzzler.domain.CourseRepository
import com.alexeeff.golangpuzzler.puzzler.domain.entity.CourseContainer
import com.alexeeff.golangpuzzler.puzzler.domain.entity.CourseListContainer
import io.reactivex.Observable
import io.reactivex.Single

/**
 * @author Yaroslav Alexeev
 */
@Suppress("RedundantLambdaArrow")
class CourseRepositoryImpl(
        private val provider: CourseProvider,
        private val storage: CourseStorage,
        private val networkManager: NetworkManager
) : CourseRepository {

    override fun getCourse(
            courseId: Int
    ): Single<CourseContainer> {
        return Observable
                .concat(
                        getDetailsFromDb(courseId),
                        getDetailsFromServer(courseId)
                )
                .firstOrError()
    }

    private fun getDetailsFromDb(
            courseId: Int
    ): Observable<CourseContainer> {
        return Observable
                .fromCallable { storage.getCourse(courseId) }
                .filter { container -> container.cardsList.isNotEmpty() }
                .onErrorResumeNext { _: Throwable ->
                    Observable.empty()
                }
    }

    private fun getDetailsFromServer(
            courseId: Int
    ): Observable<CourseContainer> {
        return Observable.just(courseId)
                .flatMap { id ->
                    if (networkManager.isOnline()) {
                        provider.getCourseData(id)
                    } else {
                        Observable.error(NoInternetConnectionException())
                    }
                }
                .doOnNext { details -> storage.storeCourse(details) }
    }

    override fun getList(
            page: Int,
            size: Int,
            isForcedUpdate: Boolean
    ): Single<CourseListContainer> {
        return Observable
                .concat(
                        getListFromDb(page, size, isForcedUpdate),
                        getListFromServer(page, size)
                )
                .firstOrError()
    }

    private fun getListFromDb(
            page: Int,
            size: Int,
            isForcedUpdate: Boolean
    ): Observable<CourseListContainer> {
        return Observable
                .just(isForcedUpdate)
                .filter { forced -> !forced }
                .map { storage.getCourseList(page, size) }
                .filter { container ->
                    container.courseList.isNotEmpty()
                            && !container.isExpired()
                }
                .onErrorResumeNext { _: Throwable ->
                    Observable.empty()
                }
    }

    private fun getListFromServer(
            page: Int,
            size: Int
    ): Observable<CourseListContainer> {
        return Observable
                .just(Pair(page, size))
                .flatMap { pair ->
                    if (networkManager.isOnline())
                        provider.getCourseList(pair.first, pair.second)
                    else
                        Observable.error(NoInternetConnectionException())
                }
                .doOnNext { container ->
                    if (page == 1) {
                        storage.clearCourseList()
                    }
                    storage.storeCourseList(container)
                }
    }
}