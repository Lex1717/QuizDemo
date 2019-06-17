package com.alexeeff.golangpuzzler.puzzler.data.storage.entity

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class CourseStoredObject(
        @PrimaryKey
        var courseId: Int = 0,
        var header: String? = null,
        var subheader: String? = null
) : RealmObject()