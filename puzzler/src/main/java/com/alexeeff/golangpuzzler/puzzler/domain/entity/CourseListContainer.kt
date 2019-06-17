package com.alexeeff.golangpuzzler.puzzler.domain.entity

import com.alexeeff.golangpuzzler.puzzler.domain.entity.course.Course

/**
 * @author Yaroslav Alexeev
 */
class CourseListContainer(
        val courseList: List<Course>,
        val timestamp: Long
) {

    fun isExpired(): Boolean {
        return System.currentTimeMillis() - timestamp > EXPIRATION_TIMEOUT
    }

    companion object {
        const val EXPIRATION_TIMEOUT = 60 * 60 * 1000
    }
}