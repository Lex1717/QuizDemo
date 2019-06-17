package com.alexeeff.golangpuzzler.puzzler.data.storage.entity

import io.realm.RealmObject

@Suppress("unused")
open class OptionStoredObject(
        var cardId: Int = 0,
        var courseId: Int = 0,
        var text: String? = null,
        var id: Int? = null
) : RealmObject()