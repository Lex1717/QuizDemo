package com.alexeeff.golangpuzzler.config.data.storage

import com.alexeeff.golangpuzzler.config.data.storage.objects.UserInfoStoredObject
import com.alexeeff.golangpuzzler.config.domain.entity.UserInfo

/**
 * Methods for mapping RealmObject to entity and vice versa
 *
 * @author Yaroslav Alexeev
 */
fun UserInfo.mapToStoredObject(id: Int): UserInfoStoredObject {
    return UserInfoStoredObject(
            id,
            this.questionShowed,
            this.questionAnswered,
            this.correctAnswerCount
    )
}

fun UserInfoStoredObject?.mapToEntity(): UserInfo {
    return if (this == null) {
        UserInfo()
    } else {
        UserInfo(
                this.questionShowed,
                this.questionAnswered,
                this.correctAnswerCount
        )
    }
}