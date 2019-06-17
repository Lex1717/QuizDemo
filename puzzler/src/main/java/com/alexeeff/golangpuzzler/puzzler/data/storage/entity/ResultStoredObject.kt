package com.alexeeff.golangpuzzler.puzzler.data.storage.entity

import io.realm.RealmObject

@Suppress("unused")
open class ResultStoredObject(
        var cardId: Int = 0,
        var courseId: Int = 0,
        var text: String? = null,
        var isCorrect: Boolean? = null,
        var answerCode: Int? = null
) : RealmObject()