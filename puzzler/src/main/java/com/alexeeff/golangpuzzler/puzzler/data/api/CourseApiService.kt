package com.alexeeff.golangpuzzler.puzzler.data.api

import com.alexeeff.golangpuzzler.puzzler.data.response.CourseResponse
import com.alexeeff.golangpuzzler.puzzler.data.response.ListResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author Yaroslav Alexeev
 */
interface CourseApiService {
    @GET(GET_COURSE)
    fun getCourseData(
            @Query("id") id: Int
    ): Observable<CourseResponse>

    @GET(GET_LIST)
    fun getCourseList(
            @Query("page") page: Int,
            @Query("size") size: Int
    ): Observable<ListResponse>
}