package com.alexeeff.golangpuzzler.puzzler.data.response

class CourseResponse(
        val courseId: Int,
        val cardsList: List<CardResponseObject>
)

class CardResponseObject(
        val id: Int,
        val title: String,
        val results: List<ResultItemResponseObject>?,
        val optionsList: List<CardElementResponseObject>?
)

class CardElementResponseObject(
        val id: Int,
        val text: String
)

class ResultItemResponseObject(
        val id: Int,
        val text: String,
        val isCorrect: Boolean
)