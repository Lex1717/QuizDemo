package com.alexeeff.golangpuzzler.puzzler.data.response

import com.alexeeff.golangpuzzler.puzzler.domain.entity.CourseContainer
import com.alexeeff.golangpuzzler.puzzler.domain.entity.card.Card
import com.alexeeff.golangpuzzler.puzzler.domain.entity.card.Option
import com.alexeeff.golangpuzzler.puzzler.domain.entity.card.Result
import java.util.*

fun CourseResponse.parse(): CourseContainer {
    val cardList = ArrayList<Card>()
    cardsList.mapTo(cardList) {
        createLearningCard(it)
    }
    return CourseContainer(courseId, cardList)
}

fun createLearningCard(response: CardResponseObject): Card {
    return Card(response.id,
            response.title,
            createOptionsList(response.optionsList),
            createResultMap(response.results))
}

private fun createResultMap(
        results: List<ResultItemResponseObject>?
): Map<Int, Result> {
    if (results == null || results.isEmpty()) return Collections.emptyMap()

    return results.associate { it.id to Result(it.text, it.isCorrect, it.id) }
}

private fun createOptionsList(
        optionsList: List<CardElementResponseObject>?
): List<Option> {
    if (optionsList == null || optionsList.isEmpty()) return Collections.emptyList()

    return optionsList.mapTo(ArrayList()) { Option(it.text, it.id) }
}
