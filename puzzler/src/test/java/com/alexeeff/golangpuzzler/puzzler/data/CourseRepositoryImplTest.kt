package com.alexeeff.golangpuzzler.puzzler.data

import com.alexeeff.golangpuzzler.core.exception.NoInternetConnectionException
import com.alexeeff.golangpuzzler.core.interfaces.NetworkManager
import com.alexeeff.golangpuzzler.puzzler.data.provider.CourseProvider
import com.alexeeff.golangpuzzler.puzzler.data.storage.CourseStorage
import com.alexeeff.golangpuzzler.puzzler.domain.entity.CourseContainer
import com.alexeeff.golangpuzzler.puzzler.domain.entity.CourseListContainer
import com.alexeeff.golangpuzzler.puzzler.domain.entity.card.Card
import com.alexeeff.golangpuzzler.puzzler.domain.entity.course.Course
import com.nhaarman.mockitokotlin2.*
import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test
import java.util.*

const val TEST_ID = 1
const val FIRST_PAGE = 1
const val SECOND_PAGE = 2
const val SIZE = 20

class CourseRepositoryImplTest{
    private lateinit var repository: CourseRepositoryImpl
    private val courseProvider: CourseProvider = mock()
    private val storage: CourseStorage = mock()
    private val networkManager: NetworkManager = mock()

    @Before
    fun setUp() {
        repository = CourseRepositoryImpl(courseProvider, storage, networkManager)
    }

    @Test
    fun get_course_should_load_and_store_if_empty_bd() {
        /* Given */
        val container = CourseContainer(
                TEST_ID,
                Collections.emptyList()
        )

        given(courseProvider.getCourseData(TEST_ID))
                .willReturn(Observable.just(container))

        given(storage.getCourse(TEST_ID))
                .willReturn(container)

        given(networkManager.isOnline()).willReturn(true)

        /* When */
        val courseObservable = repository.getCourse(TEST_ID)
        val testObserver = TestObserver<CourseContainer>()

        courseObservable.subscribe(testObserver)
        testObserver.awaitTerminalEvent()

        /* Then */
        verify(storage, times(1)).getCourse(TEST_ID)
        verify(courseProvider, times(1)).getCourseData(TEST_ID)
        verify(storage, times(1)).storeCourse(container)

        testObserver
                .assertValueCount(1)
                .assertNoErrors()
                .assertValue(container)
    }

    @Test
    fun get_course_should_throw_exception_if_bd_is_empty_and_offline() {
        /* Given */
        val container = CourseContainer(
                TEST_ID,
                Collections.emptyList()
        )

        given(storage.getCourse(TEST_ID))
                .willReturn(container)

        given(networkManager.isOnline()).willReturn(false)

        /* When */
        val courseObservable = repository.getCourse(TEST_ID)
        val testObserver = TestObserver<CourseContainer>()

        courseObservable.subscribe(testObserver)
        testObserver.awaitTerminalEvent()

        /* Then */
        verify(storage, times(1)).getCourse(TEST_ID)
        verifyZeroInteractions(courseProvider)
        verify(storage, times(0)).storeCourse(container)

        testObserver
                .assertNoValues()
                .assertError(NoInternetConnectionException::class.java)
    }

    @Test
    fun get_course_should_get_data_from_bd_if_exists() {
        /* Given */
        val container = CourseContainer(
                TEST_ID,
                listOf(Card(1, "", Collections.emptyList(), Collections.emptyMap()))
        )

        given(storage.getCourse(TEST_ID))
                .willReturn(container)

        given(networkManager.isOnline()).willReturn(true)

        /* When */
        val courseObservable = repository.getCourse(TEST_ID)
        val testObserver = TestObserver<CourseContainer>()

        courseObservable.subscribe(testObserver)
        testObserver.awaitTerminalEvent()

        /* Then */
        verify(storage).getCourse(TEST_ID)
        verifyZeroInteractions(courseProvider)
        verify(storage, times(0)).storeCourse(container)

        testObserver
                .assertValueCount(1)
                .assertNoErrors()
                .assertValue(container)
    }

    @Test
    fun get_list_should_load_and_store_data_if_bd_is_empty() {
        /* Given */
        val container = CourseListContainer(
                Collections.emptyList(),
                0
        )

        given(courseProvider.getCourseList(FIRST_PAGE, SIZE))
                .willReturn(Observable.just(container))

        given(storage.getCourseList(FIRST_PAGE, SIZE))
                .willReturn(container)

        given(networkManager.isOnline()).willReturn(true)

        /* When */
        val courseObservable = repository.getList(FIRST_PAGE, SIZE, false)
        val testObserver = TestObserver<CourseListContainer>()

        courseObservable.subscribe(testObserver)
        testObserver.awaitTerminalEvent()

        /* Then */
        verify(storage, times(1)).getCourseList(FIRST_PAGE, SIZE)
        verify(courseProvider, times(1)).getCourseList(FIRST_PAGE, SIZE)
        verify(storage, times(1)).storeCourseList(container)

        testObserver
                .assertValueCount(1)
                .assertNoErrors()
                .assertValue(container)
    }

    @Test
    fun get_list_should_throw_error_if_bd_is_empty_and_offline() {
        /* Given */
        val container = CourseListContainer(
                Collections.emptyList(),
                0
        )

        given(storage.getCourseList(FIRST_PAGE, SIZE))
                .willReturn(container)

        given(networkManager.isOnline()).willReturn(false)

        /* When */
        val courseObservable = repository.getList(FIRST_PAGE, SIZE, false)
        val testObserver = TestObserver<CourseListContainer>()

        courseObservable.subscribe(testObserver)
        testObserver.awaitTerminalEvent()

        /* Then */
        verify(storage, times(1)).getCourseList(FIRST_PAGE, SIZE)
        verifyZeroInteractions(courseProvider)
        verify(storage, times(0)).storeCourseList(container)

        testObserver
                .assertNoValues()
                .assertError(NoInternetConnectionException::class.java)
    }

    @Test
    fun get_list_should_clear_bd_if_first_page_load_from_server() {
        /* Given */
        val container = CourseListContainer(
                Collections.emptyList(),
                0
        )

        given(courseProvider.getCourseList(FIRST_PAGE, SIZE))
                .willReturn(Observable.just(container))

        given(storage.getCourseList(FIRST_PAGE, SIZE))
                .willReturn(container)

        given(networkManager.isOnline()).willReturn(true)

        /* When */
        val courseObservable = repository.getList(FIRST_PAGE, SIZE, false)
        val testObserver = TestObserver<CourseListContainer>()

        courseObservable.subscribe(testObserver)
        testObserver.awaitTerminalEvent()

        /* Then */
        verify(storage, times(1)).clearCourseList()
    }

    @Test
    fun get_list_should_not_clear_bd_if_other_than_first_page_load_from_server() {
        /* Given */
        val container = CourseListContainer(
                Collections.emptyList(),
                0
        )

        given(courseProvider.getCourseList(SECOND_PAGE, SIZE))
                .willReturn(Observable.just(container))

        given(storage.getCourseList(SECOND_PAGE, SIZE))
                .willReturn(container)

        given(networkManager.isOnline()).willReturn(true)

        /* When */
        val courseObservable = repository.getList(SECOND_PAGE, SIZE, false)
        val testObserver = TestObserver<CourseListContainer>()

        courseObservable.subscribe(testObserver)
        testObserver.awaitTerminalEvent()

        /* Then */
        verify(storage, times(0)).clearCourseList()
    }

    @Test
    fun get_list_should_load_list_from_bd_if_valid_data_exists_and_isnt_force_update() {
        /* Given */
        val container = CourseListContainer(
                listOf(Course(0, "", "")),
                System.currentTimeMillis()
        )

        given(storage.getCourseList(FIRST_PAGE, SIZE))
                .willReturn(container)

        /* When */
        val courseObservable = repository.getList(FIRST_PAGE, SIZE, false)
        val testObserver = TestObserver<CourseListContainer>()

        courseObservable.subscribe(testObserver)
        testObserver.awaitTerminalEvent()

        /* Then */
        verify(storage, times(1)).getCourseList(FIRST_PAGE, SIZE)
        verifyZeroInteractions(courseProvider)
        verify(storage, times(0)).storeCourseList(container)

        testObserver
                .assertValueCount(1)
                .assertNoErrors()
                .assertValue(container)
    }

    @Test
    fun get_list_should_load_list_from_server_if_expired_data_exists_and_isnt_force_update() {
        /* Given */
        val container = CourseListContainer(
                listOf(Course(0, "", "")),
                0
        )

        given(courseProvider.getCourseList(FIRST_PAGE, SIZE))
                .willReturn(Observable.just(container))

        given(storage.getCourseList(FIRST_PAGE, SIZE))
                .willReturn(container)

        given(networkManager.isOnline()).willReturn(true)

        /* When */
        val courseObservable = repository.getList(FIRST_PAGE, SIZE, false)
        val testObserver = TestObserver<CourseListContainer>()

        courseObservable.subscribe(testObserver)
        testObserver.awaitTerminalEvent()

        /* Then */
        verify(storage, times(1)).getCourseList(FIRST_PAGE, SIZE)
        verify(courseProvider, times(1)).getCourseList(FIRST_PAGE, SIZE)

        testObserver
                .assertValueCount(1)
                .assertNoErrors()
                .assertValue(container)
    }

    @Test
    fun get_list_should_load_list_from_server_if_force_update() {
        /* Given */
        val container = CourseListContainer(
                listOf(Course(0, "", "")),
                0
        )

        given(courseProvider.getCourseList(FIRST_PAGE, SIZE))
                .willReturn(Observable.just(container))

        given(networkManager.isOnline()).willReturn(true)

        /* When */
        val courseObservable = repository.getList(FIRST_PAGE, SIZE, true)
        val testObserver = TestObserver<CourseListContainer>()

        courseObservable.subscribe(testObserver)
        testObserver.awaitTerminalEvent()

        /* Then */
        verify(storage, times(0)).getCourseList(FIRST_PAGE, SIZE)
        verify(courseProvider, times(1)).getCourseList(FIRST_PAGE, SIZE)

        testObserver
                .assertValueCount(1)
                .assertNoErrors()
                .assertValue(container)
    }
}