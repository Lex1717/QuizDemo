package com.alexeeff.golangpuzzler.puzzler.data.storage

import android.annotation.SuppressLint
import com.alexeeff.golangpuzzler.puzzler.data.storage.entity.CardStoredObject
import com.alexeeff.golangpuzzler.puzzler.data.storage.entity.CourseStoredObject
import com.alexeeff.golangpuzzler.puzzler.data.storage.entity.OptionStoredObject
import com.alexeeff.golangpuzzler.puzzler.data.storage.entity.ResultStoredObject
import com.alexeeff.golangpuzzler.puzzler.domain.entity.card.Card
import com.alexeeff.golangpuzzler.puzzler.domain.entity.card.Option
import com.alexeeff.golangpuzzler.puzzler.domain.entity.card.Result
import com.alexeeff.golangpuzzler.puzzler.domain.entity.course.Course
import io.realm.RealmList
import io.realm.RealmResults
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

/*CARD*/

fun createCardsList(
        storedCardsList: RealmResults<CardStoredObject>?
): List<Card> {
    if (storedCardsList == null || storedCardsList.isEmpty()) return Collections.emptyList()

    val cardsList = ArrayList<Card>()
    for (card in storedCardsList) {
        cardsList.add(createCard(card))
    }

    return cardsList
}

fun createCardStoredObject(
        item: Card,
        courseId: Int
): CardStoredObject {
    return CardStoredObject(
            item.id,
            courseId,
            item.title,
            createOptionStoredList(
                    item.id,
                    courseId,
                    item.optionsList
            ),
            createResultStoredList(
                    item.id,
                    courseId,
                    item.resultMap
            )
    )
}

private fun createOptionStoredList(
        cardId: Int,
        courseId: Int,
        optionsList: List<Option>
): RealmList<OptionStoredObject>? {
    if (optionsList.isEmpty()) return null

    val optionStoredList = RealmList<OptionStoredObject>()
    for (optionItem in optionsList) {
        optionStoredList.add(
                OptionStoredObject(
                        cardId,
                        courseId,
                        optionItem.text,
                        optionItem.id
                )
        )
    }

    return optionStoredList
}

private fun createResultStoredList(
        cardId: Int,
        courseId: Int,
        resultMap: Map<Int, Result>
): RealmList<ResultStoredObject>? {
    if (resultMap.isEmpty()) return null

    val resultStoredList = RealmList<ResultStoredObject>()
    for (entry in resultMap) {
        val result = entry.value
        resultStoredList.add(
                ResultStoredObject(
                        cardId,
                        courseId,
                        result.text,
                        result.isCorrect,
                        result.answerCode
                )
        )
    }

    return resultStoredList
}

private fun createCard(card: CardStoredObject): Card {
    return Card(card.id,
            card.title ?: "",
            createOptionList(card.optionList),
            createResultMap(card.results))
}

private fun createOptionList(
        optionStoredList: RealmList<OptionStoredObject>?
): List<Option> {
    if (optionStoredList == null || optionStoredList.isEmpty()) return Collections.emptyList()

    val optionsList = ArrayList<Option>()
    for (optionItem in optionStoredList) {
        optionsList.add(
                Option(
                        optionItem.text ?: "",
                        optionItem.id ?: 0
                )
        )
    }

    return optionsList
}

@SuppressLint("UseSparseArrays")
private fun createResultMap(
        results: RealmList<ResultStoredObject>?
): Map<Int, Result> {
    if (results == null || results.isEmpty()) return Collections.emptyMap()

    val resultMap = HashMap<Int, Result>()
    for (item in results) {
        val resultItem = Result(
                item.text ?: "",
                item.isCorrect ?: false,
                item.answerCode ?: 0
        )

        resultMap[item.answerCode ?: 0] = resultItem
    }

    return resultMap
}

/*COURSE*/

fun createCourseStoredObject(
        course: Course
): CourseStoredObject {
    return CourseStoredObject(
            course.courseId,
            course.header,
            course.subheader
    )
}

fun createCourseList(
        storedCourseList: List<CourseStoredObject>?
): List<Course> {
    if (storedCourseList == null || storedCourseList.isEmpty()) return Collections.emptyList()

    val courseList = ArrayList<Course>()
    for (course in storedCourseList) {
        courseList.add(createCourse(course))
    }

    return courseList
}

fun createCourse(course: CourseStoredObject): Course {
    return Course(
            course.courseId,
            course.header ?: "",
            course.subheader ?: ""
    )
}
