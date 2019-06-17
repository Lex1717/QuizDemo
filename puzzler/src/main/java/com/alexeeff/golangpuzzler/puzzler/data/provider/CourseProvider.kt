package com.alexeeff.golangpuzzler.puzzler.data.provider

import com.alexeeff.golangpuzzler.puzzler.domain.entity.CourseContainer
import com.alexeeff.golangpuzzler.puzzler.domain.entity.CourseListContainer
import io.reactivex.Observable

/**
 * @author Yaroslav Alexeev
 */
interface CourseProvider {
    fun getCourseData(courseId: Int): Observable<CourseContainer>

    fun getCourseList(page: Int, size: Int): Observable<CourseListContainer>
}