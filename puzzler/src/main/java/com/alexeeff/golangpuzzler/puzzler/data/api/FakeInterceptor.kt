package com.alexeeff.golangpuzzler.puzzler.data.api

import android.content.Context
import com.alexeeff.golangpuzzler.puzzler.R
import okhttp3.*
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

/**
 * Mocks server's responses
 *
 * @author Yaroslav Alexeev
 */
class FakeInterceptor(val context: Context) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response? {
        val host = chain.request().url().uri().path
        //imitating network load
        Thread.sleep(1500)
        return when {
            host.contains(GET_COURSE) -> getCourseResponse(chain)
            host.contains(GET_LIST) -> getListResponse(chain)
            else -> chain.proceed(chain.request())
        }
    }

    private fun getCourseResponse(chain: Interceptor.Chain): Response? {
        val courseId = chain.request().url().queryParameter("id")?.toInt() ?: 0
        val serverResponse: ByteArray = readMock(getCourseId(courseId))

        return Response.Builder()
                .code(200)
                .message("SUCCESS")
                .request(chain.request())
                .protocol(Protocol.HTTP_1_0)
                .body(ResponseBody.create(MediaType.parse("application/json"), serverResponse))
                .addHeader("content-type", "application/json")
                .build()
    }

    private fun getCourseId(courseId: Int): Int {
        return when (courseId) {
            110 -> R.raw.initial_course
            else -> R.raw.second_course
        }
    }

    private fun getListResponse(chain: Interceptor.Chain): Response? {
        val page = chain.request().url().queryParameter("page")?.toInt() ?: 1
        val serverResponse: ByteArray = readMock(getListId(page))

        return Response.Builder()
                .code(200)
                .message("SUCCESS")
                .request(chain.request())
                .protocol(Protocol.HTTP_1_0)
                .body(ResponseBody.create(MediaType.parse("application/json"), serverResponse))
                .addHeader("content-type", "application/json")
                .build()
    }

    private fun getListId(page: Int): Int {
        return when (page) {
            1 -> R.raw.course_list_first_page
            2 -> R.raw.course_list_second_page
            else -> R.raw.course_list_third_page
        }
    }

    private fun readMock(resource: Int): ByteArray {
        var mockInputStream: InputStream? = null
        var byteOutputStream: OutputStream? = null
        try {
            mockInputStream = context.resources.openRawResource(resource)

            byteOutputStream = ByteArrayOutputStream()
            val bufferArray = ByteArray(8192)
            var threshold: Int

            while (true) {
                threshold = mockInputStream.read(bufferArray)
                if (threshold == -1) {
                    break
                }
                byteOutputStream.write(bufferArray, 0, threshold)
            }

            return byteOutputStream.toByteArray()
        } catch (e: IOException) {
            return ByteArray(0)
        } finally {
            mockInputStream?.close()
            byteOutputStream?.close()
        }
    }

}