package com.alexeeff.golangpuzzler.puzzler.domain

import com.alexeeff.golangpuzzler.puzzler.domain.entity.CourseContainer
import com.alexeeff.golangpuzzler.puzzler.domain.entity.CourseListContainer
import io.reactivex.Single

interface CourseRepository {
    fun getCourse(
            courseId: Int
    ): Single<CourseContainer>

    fun getList(
            page: Int,
            size: Int,
            isForcedUpdate: Boolean
    ): Single<CourseListContainer>
}