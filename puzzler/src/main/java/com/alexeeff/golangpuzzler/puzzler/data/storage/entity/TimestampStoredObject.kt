package com.alexeeff.golangpuzzler.puzzler.data.storage.entity

import io.realm.RealmObject

open class TimestampStoredObject(
        var timestamp: Long = 0
) : RealmObject()