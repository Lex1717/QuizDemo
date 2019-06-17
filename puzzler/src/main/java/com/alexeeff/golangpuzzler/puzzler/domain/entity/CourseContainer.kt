package com.alexeeff.golangpuzzler.puzzler.domain.entity

import com.alexeeff.golangpuzzler.puzzler.domain.entity.card.Card

/**
 * @author Yaroslav Alexeev
 */
class CourseContainer(
        val courseId: Int,
        val cardsList: List<Card>
)