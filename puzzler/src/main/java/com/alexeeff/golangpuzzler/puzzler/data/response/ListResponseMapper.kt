package com.alexeeff.golangpuzzler.puzzler.data.response

import com.alexeeff.golangpuzzler.puzzler.domain.entity.CourseListContainer
import com.alexeeff.golangpuzzler.puzzler.domain.entity.course.Course
import java.util.*

fun ListResponse.parse(): CourseListContainer {
    val list = ArrayList<Course>()
    courseList.mapTo(list) {
        createCourse(it)
    }
    return CourseListContainer(list, System.currentTimeMillis())
}

fun createCourse(response: CourseResponseObject): Course {
    return Course(
            response.courseId,
            response.header,
            response.subheader
    )
}
