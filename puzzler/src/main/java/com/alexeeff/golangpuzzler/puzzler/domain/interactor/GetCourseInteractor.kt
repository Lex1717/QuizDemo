package com.alexeeff.golangpuzzler.puzzler.domain.interactor

import com.alexeeff.golangpuzzler.puzzler.domain.CourseRepository
import com.alexeeff.golangpuzzler.puzzler.domain.entity.CourseContainer
import io.reactivex.Scheduler
import io.reactivex.Single

/**
 * @author Yaroslav Alexeev
 */
class GetCourseInteractor(
        private val jobScheduler: Scheduler,
        private val uiScheduler: Scheduler,
        private val courseRepository: CourseRepository
)  {

    fun getCourseList(courseId: Int): Single<CourseContainer> {
        return courseRepository.getCourse(courseId)
                .subscribeOn(jobScheduler)
                .observeOn(uiScheduler)
    }
}