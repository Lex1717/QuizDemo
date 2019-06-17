package com.alexeeff.golangpuzzler.puzzler.data.storage

import com.alexeeff.golangpuzzler.puzzler.domain.entity.CourseContainer
import com.alexeeff.golangpuzzler.puzzler.domain.entity.CourseListContainer

/**
 * @author Yaroslav Alexeev
 */
interface CourseStorage {
    fun storeCourse(courseContainer: CourseContainer): Boolean

    fun getCourse(courseId: Int): CourseContainer

    fun storeCourseList(courseContainer: CourseListContainer): Boolean

    fun clearCourseList()

    fun getCourseList(page: Int, size: Int): CourseListContainer
}