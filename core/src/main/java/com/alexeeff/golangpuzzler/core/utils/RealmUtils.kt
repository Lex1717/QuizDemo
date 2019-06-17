package com.alexeeff.golangpuzzler.core.utils

import com.alexeeff.golangpuzzler.core.interfaces.CascadeDeletable
import io.realm.RealmResults
import java.util.*

fun <T : CascadeDeletable> RealmResults<T>?.cascadeDeleteAllFromRealm() {
    if (this == null) return

    for (item in this) {
        item.cascadeDelete()
    }
}

fun <T> List<T>?.applyLimitOffset(limit: Int, offset: Int): List<T>? {
    if (this == null || offset > this.size) return Collections.emptyList()

    return if (offset + limit > this.size) {
        this.subList(offset, this.size)
    } else {
        this.subList(offset, offset + limit)
    }
}