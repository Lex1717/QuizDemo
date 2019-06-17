package com.alexeeff.golangpuzzler.puzzler.domain.interactor

import com.alexeeff.golangpuzzler.puzzler.domain.CourseRepository
import com.alexeeff.golangpuzzler.puzzler.domain.entity.CourseListContainer
import io.reactivex.Scheduler
import io.reactivex.Single

/**
 * @author Yaroslav Alekseev
 */
class GetCourseListInteractor(
        private val jobScheduler: Scheduler,
        private val uiScheduler: Scheduler,
        private val repository: CourseRepository
) {

    fun loadList(
            page: Int,
            size: Int,
            isForcedUpdate: Boolean
    ): Single<CourseListContainer> {
        return repository.getList(page, size, isForcedUpdate)
                .subscribeOn(jobScheduler)
                .observeOn(uiScheduler)
    }
}