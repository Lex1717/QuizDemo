package com.alexeeff.golangpuzzler.config.data.storage.objects

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * @author Yaroslav Alexeev
 */

open class UserInfoStoredObject(
        @PrimaryKey
        var id: Int = 0,
        var questionShowed: Int = 0,
        var questionAnswered: Int = 0,
        var correctAnswerCount: Int = 0
) : RealmObject()