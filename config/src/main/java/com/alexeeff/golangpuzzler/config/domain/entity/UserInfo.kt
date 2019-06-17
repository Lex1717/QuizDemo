package com.alexeeff.golangpuzzler.config.domain.entity

/**
 * @author Yaroslav Alexeev
 */

data class UserInfo(
        val questionShowed: Int = 0,
        val questionAnswered: Int = 0,
        val correctAnswerCount: Int = 0
)