package com.alexeeff.golangpuzzler.puzzler.data.response

class ListResponse(
        val courseList: List<CourseResponseObject>
)

class CourseResponseObject(
        val courseId: Int,
        val name: String,
        val header: String,
        val subheader: String
)
