package com.alexeeff.golangpuzzler.puzzler.data.storage.entity

import com.alexeeff.golangpuzzler.core.interfaces.CascadeDeletable
import io.realm.RealmList
import io.realm.RealmObject

open class CardStoredObject(
        var id: Int = 0,
        var courseId: Int = 0,
        var title: String? = null,
        var optionList: RealmList<OptionStoredObject>? = null,
        var results: RealmList<ResultStoredObject>? = null
) : RealmObject(), CascadeDeletable {

    override fun cascadeDelete() {
        optionList?.deleteAllFromRealm()
        results?.deleteAllFromRealm()
        deleteFromRealm()
    }
}

const val COURSE_ID = "courseId"
const val ID = "id"