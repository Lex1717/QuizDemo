package com.alexeeff.golangpuzzler.puzzler.data.provider

import com.alexeeff.golangpuzzler.puzzler.data.api.CourseApiService
import com.alexeeff.golangpuzzler.puzzler.data.response.parse
import com.alexeeff.golangpuzzler.puzzler.domain.entity.CourseContainer
import com.alexeeff.golangpuzzler.puzzler.domain.entity.CourseListContainer
import io.reactivex.Observable

/**
 * @author Yaroslav Alexeev
 */
class CourseProviderImpl(private val courseApiService: CourseApiService): CourseProvider {
    override fun getCourseData(
            courseId: Int
    ): Observable<CourseContainer> {
        return courseApiService.getCourseData(courseId)
                .map { response -> response.parse() }

    }

    override fun getCourseList(
            page: Int,
            size: Int
    ): Observable<CourseListContainer> {
        return courseApiService.getCourseList(page, size)
                .map { response -> response.parse() }
    }
}